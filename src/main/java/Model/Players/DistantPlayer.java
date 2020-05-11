package Model.Players;

import Model.Move;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.apache.commons.lang3.SerializationUtils;

import java.awt.Color;
import java.io.IOException;
import java.io.OutputStream;

import java.util.List;

public class DistantPlayer extends Player {
    public DistantPlayer(String name, Color color) {
        super(name, color);
    }

    private final static String QUEUE_NAME = "QQ";
    private static Move message;

    @Override
    public Move Jouer( List<Move> coups_possibles) {
        message = null;
        webReceiver();

        while (message == null){
            try {
                wait(1000);
            }catch (Exception e){
                System.out.println("Erreur d'attente du réseau");
            }
        }

        return message;
    }

    private void webReceiver(){
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try {
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();

            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                message = SerializationUtils.deserialize(delivery.getBody());
                System.out.println(" [x] Received '" + message + "'");
            };
            channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> {
            });

        }catch (Exception e){
            System.out.println("Erreur de reception des données");
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
