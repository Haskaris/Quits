package Modele.Joueurs;

import Modele.Coup;

import java.util.List;

public class JoueurIANormale extends Joueur {

    public JoueurIANormale(String _nom, int _couleur) {
        super(_nom, _couleur);
    }

    @Override
    public Coup Jouer( List<Coup> coups_possibles) {
        return coups_possibles.get(0);
    }
}