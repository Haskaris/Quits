package Model.Web;
import Model.Move;
import com.rabbitmq.client.*;
import org.apache.commons.lang3.SerializationUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static Model.Web.WebTools.*;


public class WebManager  {

    private Channel channel;
    public String queue_in = "Qin";
    public String queue_out = "Qout";

    public boolean isMaster;
    public String[] players = new String[3];
    private HostWaiter hostWaiter;

    public WebManager(String mode, String roomname, String pseudo) throws IOException {
        channel = channelCreatorLocal();

        if (channel == null) {
            System.out.println("Impossible de se connecter au serveur ");
            return;
        }

        if(mode.equals("Create")){
            queue_out= roomname;
            queue_in = roomname+"0";
            WebTools.addQueue(channel,queue_in);
            WebTools.addExchange(channel,queue_out);

            createGame();
        }
        if(mode.equals("Join")){
            queue_out = roomname+"0";
            queue_in = WebTools.addQueueBound(channel,roomname);
            WebTools.addQueue(channel,queue_out);

            joinGame(pseudo);
        }
    }

    void joinGame(String pseudo) {
        Send(pseudo);

        if (WebTools.receiveMessageWaited(channel,queue_in) == null) {
            System.out.println("Room inexistante ");
            return;
        }


        if(getMessageAsString().equals("Room pleine")){
            System.out.println("Room pleine ");
            return;
        }

        if(!getMessageAsString().equals("OK")){
            System.out.println("recu " + WebTools.getMessageAsString());
            return;
        }


        System.out.println("Partie rejointe, en attente");
        isMaster = false;
    }

    void createGame() throws IOException {
        hostWaiter = new HostWaiter(channel, queue_in, queue_out, players);
        new Thread(hostWaiter).start();
    }



    public void stopThread() {
        hostWaiter.running = false;
    }



    public void Send(String s){
        try {
            channel.basicPublish("", queue_out, null, s.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Send(Move move)  {
        if (channel == null)
            return;
        try {
            channel.basicPublish("", queue_out, null, SerializationUtils.serialize(move));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public byte[] Receive(){
        return WebTools.receiveMessageWaited(channel,queue_in);
    }

}