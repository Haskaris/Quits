package Model.Players;

import Model.Move;
import Model.WebManager;
import Model.WebTools;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.apache.commons.lang3.SerializationUtils;

import java.awt.Color;
import java.io.IOException;
import java.io.OutputStream;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class DistantPlayer extends Player {
    public DistantPlayer(String name, Color color, String roomname) {
        super(name, color);
        queuename = roomname;
        channel = WebTools.channelCreatorLocal(queuename);
    }

    private String queuename = "default";
    private Channel channel;
    private static Move message;

    @Override
    public Move Jouer( List<Move> coups_possibles) {
        message = null;
        webReceiver();

        while (message == null){
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            }catch (Exception e){
                System.out.println("Erreur d'attente du réseau : " + e.getMessage());
            }
        }

        return message;
    }

    private void webReceiver() {

        System.out.println(" [*] Waiting for messages.");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                message = SerializationUtils.deserialize(delivery.getBody());
                System.out.println(" [x] Received '" + message + "'");
        };
        try {
            channel.basicConsume(queuename, true, deliverCallback, consumerTag -> {
            });
        }catch (Exception e){
            System.out.println("Erreur de consommation");
        }

    }




    /**
     * S'imprime dans la sortie stream
     * @param stream
     * @throws IOException 
     */
    @Override
    public void print(OutputStream stream) throws IOException {
        stream.write("DistantPlayer".getBytes());
        stream.write(' ');
        super.print(stream);
    }
}
