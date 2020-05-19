package Model.Web;
import Model.Move;
import com.rabbitmq.client.*;
import org.apache.commons.lang3.SerializationUtils;

import java.io.IOException;

import static Model.Web.WebTools.*;


public class WebManager  {

    private Channel channel;
    public String queue_in = "Qin";
    public String queue_out = "Qout";

    public boolean isMaster;
    public String[] players = new String[3];
    private HostWaiter hostWaiter;

    public WebManager(String mode, String roomname, String pseudo) throws IOException {
        channel = channelCreatorLocal(roomname,roomname+"0");
        if (channel == null) {
            System.out.println("Impossible de se connecter au serveur ");
            return;
        }

        if(mode == "Create"){
            queue_out= roomname;
            queue_in = roomname+"0";
            createGame();
        }
        if(mode == "Join"){
            queue_in = roomname;
            queue_out = roomname+"0";
            joinGame(pseudo);
        }
    }

    void joinGame(String pseudo) {
        if (WebTools.receiveMessageWaited(channel,queue_in) == null) {
            System.out.println("Room inexistante ");
            return;
        }
        if(WebTools.getMessageAsString() == "Room pleine"){
            System.out.println("Room pleine ");
            return;
        }

        Send(pseudo);

        System.out.println("Partie rejointe : " + queue_in);
        isMaster = false;

    }

    void createGame() throws IOException {
        channel.basicPublish("", queue_out, null, "0".getBytes());

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