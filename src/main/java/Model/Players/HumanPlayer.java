package Model.Players;

import Model.Move;
import java.awt.Color;
import java.io.IOException;
import java.io.OutputStream;

import java.util.List;

public class HumanPlayer extends Player {

    public HumanPlayer(String _nom, Color _couleur) {
        super(_nom, _couleur);
    }

    @Override
    public Move Jouer(List<Move> coups_possibles) {
        return coups_possibles.get(0);
    }
    
    /**
     * S'imprime dans la sortie stream
     * @param stream
     * @throws IOException 
     */
    @Override
    public void print(OutputStream stream) throws IOException {
        stream.write("HumanPlayer".getBytes());
        stream.write(' ');
        super.print(stream);
    }
}
