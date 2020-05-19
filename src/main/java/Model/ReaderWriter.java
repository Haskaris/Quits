package Model;

import Model.Players.Player;
import Global.Configuration;
import Model.Support.Marble;
import Model.Support.Board;

import java.io.*;

public class ReaderWriter {
    private final String filepath;

    /**
     * Constructeur
     * @param filepath 
     */
    public ReaderWriter(String filepath) {
        this.filepath = "Sauvegardes/" + filepath;
    }
    
    /**
     * Lis le contenu d'un board de jeu d'un fichier externe
     * @return 
     * @throws java.io.IOException 
     */
    public Board readGame()throws IOException{
        Board board = new Board();
        try (InputStream in_stream = new FileInputStream(this.filepath)) {
            //On lit la boardSize et le nombre de players
            String[] firstline = readLine(in_stream).split(" ");
            int boardSize = Integer.parseInt(firstline[0]);
            
            //Enregistrer le mode de jeu serait peut être mieux ?
            int playerNumber = Integer.parseInt(firstline[1]);
            
            //board = new Board(/*boardSize*/);
            //On lit les infos sur les players
            for (int k = 0; k < playerNumber; k++) {
                //Format :
                //TYPE NOM COULEUR POSITION
                //Bille1X-Bille1Y/Bille2X-Bille2Y
                Player tmp = Player.load(in_stream, board);
                String[] metadonees = readLine(in_stream).split("/");
                for(String coord : metadonees) {
                    String[] xy = coord.split(",");
                    Marble btmp = tmp.addMarble();
                    board.placeMarbleOn(btmp, Integer.parseInt(xy[0]), Integer.parseInt(xy[1]));
                }
                board.addPlayer(tmp);
            }
            
            board.load(in_stream);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return board;
    }

    //Il faut sauvegarder l'historique
    /**
     * Ecris le contenu d'un board de jeu dans un fichier externe
     * @param board board à sauvegarder
     * @throws java.io.IOException
     */
    public void writeGame(Board board) throws IOException{
        File out = new File(this.filepath);
        OutputStream stream;
        try {
            out.getParentFile().mkdirs();
            out.createNewFile();
            stream = new FileOutputStream(out);
        }
        catch(IOException e){
            Configuration.logger().severe("Erreur de creation d'un fichier de sortie : " + this.filepath);
            return;
        }

        //On écrit la boardSize du tableau (Pour quand elle sera dynamique)
        stream.write(String.valueOf(board.getGrid().length).getBytes());
        stream.write(' ');
        //On écrit le nombre de joueur
        //Enregistrer le mode de jeu serait peut être mieux ?
        stream.write(String.valueOf(board.getPlayers().size()).getBytes());
        stream.write('\n');
        
        //Info sur les players
        for (Player p : board.getPlayers()) {
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