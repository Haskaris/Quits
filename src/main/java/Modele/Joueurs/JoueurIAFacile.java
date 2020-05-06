package Modele.Joueurs;

import Modele.Coup;
import java.awt.Color;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class JoueurIAFacile extends Joueur {

    public JoueurIAFacile(String _nom, Color _couleur) {
        super(_nom, _couleur);
    }

    @Override
    public Coup Jouer(List<Coup> coups_possibles) {
        try {
            //Thread.sleep( 1000 );
            //TimeUnit.SECONDS.sleep(1);
        }catch (Exception e){
            System.out.println("Erreur d'attente de l'IA");
        }
        return coups_possibles.get(new Random().nextInt(coups_possibles.size()));
    }
}
