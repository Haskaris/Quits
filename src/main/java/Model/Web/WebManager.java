package Model.Web;
import Model.Move;
import com.rabbitmq.client.*;
import org.apache.commons.lang3.SerializationUtils;

import java.io.IOException;

import static Model.Web.WebTools.*;


public abstract class WebManager  {

    protected Channel channel;
    public String queue_in = "Qin";
    public String queue_out = "Qout";


    public WebManager(String roomname, String pseudo) throws IOException {
        channel = channelCreatorLocal();

        if (channel == null) {
            System.out.println("Impossible de se connecter au serveur ");
            return;
        }

    }


    public void send(String s){
        try {
            channel.basicPublish("", queue_out, null, s.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMove(Move move, boolean ismymove)  {
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