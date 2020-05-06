package Model.Players;

import Model.Move;
import java.awt.Color;

import java.util.List;

public class AINormalPlayer extends Player {

    public AINormalPlayer(String _nom, Color _couleur) {
        super(_nom, _couleur);
    }

    @Override
    public Move Jouer( List<Move> coups_possibles) {
        return coups_possibles.get(0);
    }
}
