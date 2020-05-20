package Model.Web;

import Model.Move;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static Model.Web.WebTools.getMessageAsString;

public class WebClient extends WebManager {
    public WebClient(String roomname, String pseudo) throws IOException {
        super(roomname, pseudo);

        queue_out = roomname+"0";
        queue_in = WebTools.addQueueBound(channel,roomname);
        WebTools.addQueue(channel,queue_out);

        joinGame(pseudo);
    }

    void joinGame(String pseudo) {
        send(pseudo);

        if (WebTools.receiveMessageWaited(channel,queue_in) == null) {
            System.out.println("Room inexistante ");
            return;
        }


        if(getMessageAsString().equals("Room pleine")){
            System.out.println("Room pleine ");
            return;
        }

        if(!getMessageAsString().equals("OK")){
            System.out.println("recu " + WebTools.getMessageAsString());
            return;
        }


        System.out.println("Partie rejointe, en attente");

        WebTools.receiveMessage(channel,queue_in);

        while (true){
            if(WebTools.message != null){
                if(getMessageAsString().equals("launch"))
                    break;
                else
                    WebTools.receiveMessage(channel,queue_in);
            }
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        launchGame();
    }

    void launchGame(){
        WebTools.receiveMessageWaited(channel,queue_in);

        int boardlength = Integer.parseInt(WebTools.getMessageAsString().split(" ")[0]);
        int playernumber = Integer.parseInt(WebTools.getMessageAsString().split(" ")[1]);

        String[] players = new String[playernumber-1];
        players[0] = WebTools.getMessageAsString().split(" ")[2];
        if(playernumber >2){
            players[1] = WebTools.getMessageAsString().split(" ")[3];
            players[2] = WebTools.getMessageAsString().split(" ")[4];
        }

        //Ici il faudra instancier la partie avec les paramètres récupérés et que des joueurs distants
    }

    @Override
    public void sendMove(Move move, boolean ismymove) {
        super.sendMove(move, ismymove);
    }
}
