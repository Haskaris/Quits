package Model;

import Model.Players.Player;
import Global.Configuration;
import Model.Support.Marble;
import Model.Support.Board;

import java.io.*;

public class ReaderWriter {
    private String filepath;


    public ReaderWriter(String filepath) {
        this.filepath = filepath;
    }

    /**
     * Lis le contenu d'un board de jeu d'un fichier externe
     * À vérifier le bon fonctionnement
     */
    public Board readGame()throws IOException{
        InputStream in_stream = new FileInputStream("Sauvegardes/" + filepath);

        //On lit la boardSize et le nombre de players
        String[] firstline = readLine(in_stream).split(" ");
        int boardSize = Integer.parseInt(firstline[0]);
        
        //Enregistrer le mode de jeu serait peut être mieux ?
        int playerNumber = Integer.parseInt(firstline[1]);

        Board board = new Board(/*boardSize*/);

        //On lit les infos sur les players
        board.players = new Player[playerNumber];
        for (int k = 0; k < playerNumber; k++) {
            //Format :
            //TYPE NOM COULEUR
            //Bille1X-Bille1Y/Bille2X-Bille2Y
            Player tmp = Player.load(in_stream);
            String[] metadonees = readLine(in_stream).split("/");
            for(String coord : metadonees) {
                String[] xy = coord.split("-");
                Marble btmp = new Marble(tmp.color);
                tmp.addMarble(btmp);
                board.placeMarbleOn(btmp, Integer.parseInt(xy[0]), Integer.parseInt(xy[1]));
            }
            board.players[k] = tmp;
        }
        
        board.load(in_stream);
        
        in_stream.close();
        return board;
    }

    //Il faut sauvegarder l'historique
    /**
     * Ecris le contenu d'un board de jeu dans un fichier externe
     * @param board board à sauvegarder
     */
    public void writeGame(Board board) throws IOException{
        String path = "Sauvegardes/" + filepath;
        File out = new File(path);
        OutputStream stream;
        try {
            out.getParentFile().mkdirs();
            out.createNewFile();
            stream = new FileOutputStream(out);
        }
        catch(IOException e){
            Configuration.logger().severe("Erreur de creation d'un fichier de sortie : " + path);
            return;
        }

        //On écrit la boardSize du tableau (Pour quand elle sera dynamique)
        //J'ai un doute là dessus, on ne peut pas simplement écrire l'entier ?
        stream.write((byte)IntToChar(board.getGrid().length));
        stream.write(' ');
        //On écrit le nombre de joueur
        //Enregistrer le mode de jeu serait peut être mieux ?
        stream.write((byte)IntToChar(board.players.length));
        stream.write('\n');
        
        //Info sur les players
        for (Player p : board.players) {
            p.print(stream);
        }

        board.print(stream);

        stream.write('\n');
        stream.flush();
        stream.close();
    }

    /**
     * Permet de lire une ligne
     * @param stream
     * @return
     * @throws IOException 
     */
    public static String readLine(InputStream stream) throws IOException {
        String S = "";
        byte[] data = new byte [1];
        stream.read(data);
        while(data[0] != '\n' && data[0] != '\r' && data[0] != 0){
            S += (char)data[0];
            stream.read(data);
        }
        return S;
    }

    //J'ai un doute là dessus, on ne peut pas simplement écrire l'entier ?
    private char IntToChar(int a){
        return (char)(a+48);
    }

    /**
     * Permet d'afficher l'etat du jeu dans la sortie standard.
     * Les 0 sont des cases vides, les chiffres sont les billes des joueurs
     */
    /*public static void AffichePartie(Board p){
        Tile[][] tab = p.GetGrille();
        for(int i=0;i<tab.length;i++){
            for(int j=0;j<tab[0].length;j++){
                if(tab[i][j].getCouleurBille() != 9)
                    System.out.print(tab[i][j].CouleurBille());
                else
                    System.out.print(".");
            }
            System.out.print('\n');
        }
        System.out.println("\n");
    }*/

}