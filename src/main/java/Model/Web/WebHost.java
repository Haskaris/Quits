package Model.Web;

import Model.Move;

import java.io.IOException;

public class WebHost extends WebManager {
    public String[] players = new String[3];
    private HostWaiter hostWaiter;

    public WebHost(String roomname, String pseudo) throws IOException {
        super(roomname, pseudo);

        queue_out= roomname;
        queue_in = roomname+"0";
        WebTools.addQueue(channel,queue_in);
        WebTools.addExchange(channel,queue_out);

        createGame();
    }

    void createGame() throws IOException {
        hostWaiter = new HostWaiter(channel, queue_in, queue_out, players);
        new Thread(hostWaiter).start();
    }
    public void launchGame(){
        if(players[0] == null || players[1] == null || players[2] == null) {
            System.out.println("Room incomplète");
            return;
        }
        hostWaiter.running = false;

        //Ici il faudra récuperer les parametre choisis
        String metadata = "5 4 " + players[0] + " " +  players[1] + " " +  players[2];
        send(metadata);

        //Ici il  instancier le jeu avec des joueurs distants ou des ia selon les choix de l'utilisateurs


    }

    public void sendMove(Move move, boolean ismymove) {
        if(ismymove)
            super.sendMove(move,ismymove);
    }
}
