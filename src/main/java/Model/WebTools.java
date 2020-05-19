package Model;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.apache.commons.lang3.SerializationUtils;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

public class WebTools {
    public static Channel channelCreatorCloud(String queuename) {
        ConnectionFactory factory = new ConnectionFactory();

        factory.setHost("chinook.rmq.cloudamqp.com");
        factory.setUsername("bosxyftt");
        factory.setPassword("IOZfKOaTzJ9eIfyHXNalUBvvlgNRRP6T");
        factory.setVirtualHost("bosxyftt");

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

    public static Object message;
    public static void receiveMessage(Channel channel, String queuename){
        message = null;
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            message = SerializationUtils.deserialize(delivery.getBody());
        };
        try {
            channel.basicConsume(queuename, true, deliverCallback, consumerTag -> {
            });
        }catch (Exception e){
            System.out.println("Erreur de consommation");
        }
    }

    public static Object receiveMessageWaited(Channel channel, String queuename){
        message = null;
        int waittimemax = 200;
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            message = SerializationUtils.deserialize(delivery.getBody());
        };
        try {
            channel.basicConsume(queuename, true, deliverCallback, consumerTag -> {
            });
        }catch (Exception e){
            System.out.println("Erreur de consommation");
        }
        while (message == null){
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            }catch (Exception e){
                System.out.println("Erreur d'attente de thread : " + e.getMessage());
            }
            waittimemax--;
            if(waittimemax <= 0){
                System.out.println("Time out d'attente du message");
                return null;
            }
        }
        return message;
    }
}

