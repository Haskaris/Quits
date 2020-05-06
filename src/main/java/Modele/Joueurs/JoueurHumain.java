package Modele.Joueurs;

import Modele.Coup;
import java.awt.Color;

import java.util.List;

public class JoueurHumain extends Joueur {

    public JoueurHumain(String _nom, Color _couleur) {
        super(_nom, _couleur);
    }

    @Override
    public Coup Jouer(List<Coup> coups_possibles) {
        return coups_possibles.get(0);
    }
}
