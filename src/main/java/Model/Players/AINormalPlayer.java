package Model.Players;

import Model.Move;
import java.awt.Color;
import java.io.IOException;
import java.io.OutputStream;

import java.util.List;

public class AINormalPlayer extends Player {

    public AINormalPlayer(String name, Color color) {
        super(name, color);
    }

    @Override
    public Move Jouer( List<Move> coups_possibles) {
        return coups_possibles.get(0);
    }
    
    /**
     * S'imprime dans la sortie stream
     * @param stream
     * @throws IOException 
     */
    @Override
    public void print(OutputStream stream) throws IOException {
        stream.write("AINormalPlayer".getBytes());
        stream.write('\n');
        super.print(stream);
    }
}
