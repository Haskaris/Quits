package Model;
import org.apache.commons.lang3.SerializationUtils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class WebManager {

    private final static String QUEUE_NAME = "QQ";

    public static Channel channelCreatorCloud(String queuename) {
        String uri = "amqp://bosxyftt:5quOiGijmZhtC_J9w6iCat7khaq2jVVb@chinook.rmq.cloudamqp.com/bosxyftt";
        ConnectionFactory factory = new ConnectionFactory();
        try {
            factory.setUri(uri);
        }catch (Exception e){
            System.out.println("Erreur de connection factory");
            return null;
        }

        //Recommended settings
        factory.setRequestedHeartbeat(30);
        factory.setConnectionTimeout(30000);

        boolean durable = false;    //durable - RabbitMQ will never lose the queue if a crash occurs
        boolean exclusive = false;  //exclusive - if queue only will be used by one connection
        boolean autoDelete = false; //autodelete - queue is deleted when last consumer unsubscribes

        Connection connection = null;
        Channel channel = null;

        try {
            connection = factory.newConnection();
        }
        catch (Exception e){
            System.out.println("Erreur de connection au serveur CloudAmq : " + e.getMessage());
            return null;
        }

        try {
            channel = connection.createChannel();
            channel.queueDeclare(queuename, durable, exclusive, autoDelete, null);
        }
        catch (Exception e){
            System.out.println("Erreur de creation de channel sur le serveur");
            return null;
        }


        return channel;
    }

    public static Channel channelCreatorLocal(String queuename) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        Connection connection = null;
        Channel channel = null;

        try {
            connection = factory.newConnection();
        }
        catch (Exception e){
            System.out.println("Erreur de connection au serveur local : " + e.getMessage());
            return null;
        }

        try {
            channel = connection.createChannel();
            channel.queueDeclare(queuename, false, false, false, null);
        }
        catch (Exception e){
            System.out.println("Erreur de creation de channel sur le serveur");
            return null;
        }
        return channel;
    }


        public void Send(Move message) throws Exception {
        Channel channel = channelCreatorLocal(QUEUE_NAME);
        if(channel != null){
            channel.basicPublish("", QUEUE_NAME, null, SerializationUtils.serialize(message));
            System.out.println(" [x] Sent '" + message + "'");
        }

    }
}
