package Model.Web;

import Model.Move;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.apache.commons.lang3.SerializationUtils;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

public class WebTools {

    public static void addQueue(Channel channel, String queuename){
        boolean durable = false;    //durable - RabbitMQ will never lose the queue if a crash occurs
        boolean exclusive = false;  //exclusive - if queue only will be used by one connection
        boolean autoDelete = false; //autodelete - queue is deleted when last consumer unsubscribes

        try {
            channel.queueDeclare(queuename, durable, exclusive, autoDelete, null);
        }
        catch (Exception e){
            System.out.println("Erreur de creation de queue " + queuename + " sur le channel : "+ e.getMessage());
        }
    }

    public static Channel channelCreatorCloud(String queuename1, String queuename2) {
        ConnectionFactory factory = new ConnectionFactory();

        factory.setHost("chinook.rmq.cloudamqp.com");
        factory.setUsername("bosxyftt");
        factory.setPassword("IOZfKOaTzJ9eIfyHXNalUBvvlgNRRP6T");
        factory.setVirtualHost("bosxyftt");

        return channelCreator(factory,queuename1,queuename2);
    }

    public static Channel channelCreatorLocal(String queuename1, String queuename2) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        return channelCreator(factory,queuename1,queuename2);
    }

    private static Channel channelCreator(ConnectionFactory factory,String queuename1, String queuename2){
        //Recommended settings
        factory.setRequestedHeartbeat(30);
        factory.setConnectionTimeout(30000);

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
        }
        catch (Exception e){
            System.out.println("Erreur de creation de channel sur le serveur");
            return null;
        }

        addQueue(channel,queuename1);
        addQueue(channel,queuename2);

        return channel;
    }



    public static Object message;
    public static void receiveMessage(Channel channel, String queuename){
        message = null;
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            message = delivery.getBody();
        };
        try {
            channel.basicConsume(queuename, true, deliverCallback, consumerTag -> {
            });
        }catch (Exception e){
            System.out.println("Erreur de consommation na : " + e.getMessage());
        }
    }

    public static byte[] receiveMessageWaited(Channel channel, String queuename){
        message = null;
        int waittimemax = 100;
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            message = delivery.getBody();
        };
        try {
            channel.basicConsume(queuename, true, deliverCallback, consumerTag -> {
            });
        }catch (Exception e){
            System.out.println("Erreur de consommation a : " +e.getMessage());
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
        return (byte[])message;
    }

    public static String getMessageAsString(){
        return new String((byte[])message);
    }
    public Move getMessageAsMove(){
        return  SerializationUtils.deserialize((byte[])message);
    }
}

