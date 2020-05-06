package Model.Players;

import Model.Move;
import java.awt.Color;
import java.io.IOException;
import java.io.OutputStream;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class AIEasyPlayer extends Player {

    public AIEasyPlayer(String name, Color color) {
        super(name, color);
    }

    @Override
    public Move Jouer(List<Move> coups_possibles) {
        try {
            TimeUnit.SECONDS.sleep(1);
        }catch (Exception e){
            System.out.println("Erreur d'attente de l'IA");
        }
        return coups_possibles.get(new Random().nextInt(coups_possibles.size()));
    }
    
    /**
     * S'imprime dans la sortie stream
     * @param stream
     * @throws IOException 
     */
    @Override
    public void print(OutputStream stream) throws IOException {
        stream.write("AIEasyPlayer".getBytes());
        stream.write(' ');
        super.print(stream);
    }
}
