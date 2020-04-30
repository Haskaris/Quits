package Modele.Joueurs;

import Modele.Coup;

import java.util.List;

public class JoueurDistant extends Joueur {
    public JoueurDistant(String _nom, int _couleur) {
        super(_nom, _couleur);
    }

    @Override
    public Coup Jouer( List<Coup> coups_possibles) {
        return coups_possibles.get(0);
    }
}
