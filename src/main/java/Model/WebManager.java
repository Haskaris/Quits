package Model;
import com.rabbitmq.client.*;
import org.apache.commons.lang3.SerializationUtils;

import java.io.IOException;

import static Model.WebTools.channelCreatorLocal;
import static Model.WebTools.message;


public class WebManager implements Runnable {

    public String queuename = "QQ";
    public boolean isMaster;
    public String[] players = new String[3];
    private Channel channel;
    private boolean waitingthread;

    public void Send(Move message) throws Exception {
        if (channel == null)
            return;

        channel.basicPublish("", queuename, null, SerializationUtils.serialize(message));
        System.out.println(" [x] Sent '" + message + "'");
    }

    public void joinGame(String roomname) {
        Channel channel = channelCreatorLocal(roomname);
        if (channel == null) {
            System.out.println("Impossible de se connecter au serveur ");
            return;
        }
        WebTools.receiveMessage(channel,roomname);
        if (channel == null) {
            System.out.println("Impossible de se connecter au serveur ");
            return;
        }

        queuename = roomname;
        System.out.println("Partie rejointe : " + roomname);
        isMaster = false;

    }

    public void createGame(String roomname) throws IOException {
        queuename = roomname;
        Channel channel = channelCreatorLocal(queuename);
        if (channel == null) {
            System.out.println("Erreur de creation de la partie");
            return;
        }
        channel.basicPublish("", queuename, null, "0".getBytes());

        waitingthread = true;
        new Thread(this).start();
    }

    @Override
    public void run() {
        WebTools.receiveMessage(channel, queuename);
        while (waitingthread) {
            if (WebTools.message != null) {
                if (message.toString().split(" ")[0] == "disconnect") {
                    removePlayer(message.toString().substring("disconnect ".length()));
                }
                else{
                    addPlayer(message.toString());
                }
            }

        }
    }

    public void stopThread() {
        waitingthread = false;
    }


    boolean addPlayer(String name) {
        for (String player : players) {
            if (player == null) {
                player = name;
                return true;
            }
        }
        return false;
    }


    void removePlayer(String name) {
        for (String player : players) {
            if (player == name) {    //pas de vérification des noms identique ici
                player = null;
                return;
            }
        }
    }

}