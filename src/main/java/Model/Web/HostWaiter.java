package Model.Web;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class HostWaiter implements Runnable {
    public boolean running;

    private Channel channel;
    private String queue_in;
    private String queue_out;
    private String[] players;
    private Object message;

    public HostWaiter(Channel channel, String queue_in, String queue_out, String[] players){
        running = true;
        this.channel = channel;
        this.queue_in = queue_in;
        this.queue_out = queue_out;
        this.players = players;
    }


    @Override
    public void run() {
        receiveMessage();

        while (running) {
            if (this.message != null) {
                String message = new String((byte[])this.message);
                if (message.split(" ")[0].equals("disconnect"))
                    removePlayer(message.substring("disconnect ".length()));
                else
                    addPlayer(message);

                if(emptyPlayer())
                    sendMessage("OK");
                else
                    sendMessage("Room pleine");

                receiveMessage();
            }
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }
    }

    public void receiveMessage(){
        message = null;
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            message = delivery.getBody();
        };
        try {
            channel.basicConsume(queue_in, true, deliverCallback, consumerTag -> {
            });
        }catch (Exception e){
            System.out.println("Erreur de consommation na : " + e.getMessage());
        }
    }
    public void sendMessage(String s){
        try {
            channel.basicPublish(queue_out, "", null, s.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
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
        System.out.println("Joueur arrivé : "+ name);
        for (int i = 0; i < players.length; i++) {
            if (players[i] == null) {
                players[i] = name;
                return;
            }
        }
    }
    void removePlayer(String name) {
        for (int i = 0; i < players.length; i++) {
            if (players[i].equals(name)) {    //pas de vérification des noms identique ici
                players[i] = null;
                return;
            }
        }
    }

}
