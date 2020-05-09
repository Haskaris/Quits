package Model.Players;

import Model.Move;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.awt.Color;
import java.io.IOException;
import java.io.OutputStream;

import java.util.List;

public class DistantPlayer extends Player {
    public DistantPlayer(String name, Color color) {
        super(name, color);
    }

    private final static String QUEUE_NAME = "QQ";

    @Override
    public Move Jouer( List<Move> coups_possibles) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try {
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();

            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), "UTF-8");
                System.out.println(" [x] Received '" + message + "'");
            };
            channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> {
            });
        }catch (Exception e){
            System.out.println("Erreur de reception des données");
        }

        return null;
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
