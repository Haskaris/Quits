package Modele.Joueurs;

import Modele.Coup;

import java.util.List;

public class JoueurDistant extends Joueur {
    @Override
    public Coup Jouer( List<Coup> coups_possibles) {
        return coups_possibles.get(0);
    }
}
