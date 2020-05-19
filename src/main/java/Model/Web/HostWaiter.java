package Model.Web;

import com.rabbitmq.client.Channel;

import java.io.IOException;

import static Model.Web.WebTools.*;
import static Model.Web.WebTools.message;

public class HostWaiter implements Runnable {
    public boolean running;

    private Channel channel;
    private String queue_in;
    private String queue_out;
    private String[] players;

    public HostWaiter(Channel channel, String queue_in, String queue_out, String[] players){
        running = true;
        this.channel = channel;
        this.queue_in = queue_in;
        this.queue_out = queue_out;
        this.players = players;
    }


    @Override
    public void run() {
        WebTools.receiveMessage(channel, queue_in);
        while (running) {
            if (WebTools.message != null) {
                if (WebTools.getMessageAsString().split(" ")[0].equals("disconnect"))
                    removePlayer(WebTools.getMessageAsString().substring("disconnect ".length()));
                else
                    addPlayer(WebTools.getMessageAsString());

                if(emptyPlayer())
                    try {
                        channel.basicPublish("", queue_out, null, "OK".getBytes());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                else
                    try {
                        channel.basicPublish("", queue_out, null, "Room pleine".getBytes());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }
            WebTools.receiveMessage(channel, queue_in);
        }
    }

    boolean emptyPlayer(){
        for (String player : players) {
            if (player == null) {
                return true;            }
        }
        return false;
    }

    void addPlayer(String name) {
        for (String player :  players) {
            if (player == null) {
                player = name;
                return;
            }
        }
    }
    void removePlayer(String name) {
        for (String player :  players) {
            if (player.equals(name)) {    //pas de vérification des noms identique ici
                player = null;
                return;
            }
        }
    }

}
