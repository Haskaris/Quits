package Modele;

import Global.Tools;
import Model.Move;
import Model.Players.AIEasyPlayer;
import Model.Web.WebTools;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import org.apache.commons.lang3.SerializationUtils;
import org.junit.jupiter.api.Test;

import Model.Support.*;
import Model.Players.Player;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class WebTest {

    @Test
    public void TestSerialization(){
        Player player = new AIEasyPlayer("default", Color.BLUE);
        Marble marble = player.addMarble();
        Board board = new Board();
        board.getGrid()[2][2].addMarble(marble);
        Move m = new Move(new Point(1,1), Tools.Direction.NO,player.name);
        SerializationUtils.serialize(m);
        m = new Move(marble, Tools.Direction.NO,player.name);
        SerializationUtils.serialize(m);

    }


    Object message;
    @Test
    public void TestRabbitMQ() throws Exception {
        Channel channel = Model.Web.WebTools.channelCreatorLocal();
        WebTools.addQueue(channel,"test");
        assertNotNull(channel);
        channel.basicPublish("","test",null,"essai".getBytes());

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            message = delivery.getBody();
        };

        channel.basicConsume("test", true, deliverCallback, consumerTag -> { });

        while(true){
            if(message != null)
                break;
        }
        assertEquals("essai",new String((byte[]) message));

        message = null;
        WebTools.addExchange(channel,"extest");
        String queue2 =  WebTools.addQueueBound(channel,"extest");
        assertNotNull(queue2);

        channel.basicPublish("extest","",null,"essai2".getBytes());
        channel.basicConsume("extest", true, deliverCallback, consumerTag -> { });
        while(true){
            if(message != null)
                break;
        }
        assertEquals("essai2",new String((byte[]) message));
    }

    @Test
    public void TestReseau() throws Exception {
        Channel channel = WebTools.channelCreatorLocal();
        WebTools.addQueue(channel,"test");

        channel.basicPublish("","test",null,"essai1".getBytes());
        byte[] msg= WebTools.receiveMessageWaited(channel,"test");
        assertNotNull(msg);
        assertEquals("essai1",new String(msg));

        channel.basicPublish("","test",null,"essai2".getBytes());
        msg= WebTools.receiveMessageWaited(channel,"test");
        assertNotNull(msg);
        assertEquals("essai2",new String(msg));

    }


    @Test
    public void TestRoom() throws Exception {/*
        String roomname = "testb";
        WebManager webManager = new WebHost(roomname,"player1");
        TimeUnit.MILLISECONDS.sleep(100);
        WebManager webManager1 = new WebClient(roomname,"player2");
        WebManager webManager2 = new WebClient(roomname,"player3");
        WebManager webManager3 = new WebClient(roomname,"player4");
        WebManager webManager4 = new WebClient(roomname,"player5");
        WebManager webManager5 = new WebClient(roomname,"player6");

        TimeUnit.SECONDS.sleep(1);
*/
    }
}