package Model.Players;

import Model.Move;
import java.awt.Color;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class AIEasyPlayer extends Player {

    public AIEasyPlayer(String _nom, Color _couleur) {
        super(_nom, _couleur);
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
}
