package Model.Web;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.util.concurrent.TimeUnit;

public class ClientWaiter implements Runnable {
    public boolean running;
    private String queue_in;
    private Channel channel;
    private Object message;

    public ClientWaiter (Channel channel, String queue_in){
        running = true;
        this.channel = channel;
        this.queue_in = queue_in;

    }

    @Override
    public void run() {
        receiveMessage();

        while (running) {
            if (this.message != null) {
                String message = new String((byte[])this.message);
                if(message.equals("launch")){

                }
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
}
