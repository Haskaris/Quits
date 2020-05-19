package Model.Players;

import Model.Move;
import Model.Web.WebManager;
import org.apache.commons.lang3.SerializationUtils;

import java.awt.Color;
import java.io.IOException;
import java.io.OutputStream;

import java.util.List;

public class DistantPlayer extends Player {
    public DistantPlayer(String name, Color color, WebManager webManager) {
        super(name, color);
        this.webManager = webManager;
    }

    private WebManager webManager;

    @Override
    public Move Jouer( List<Move> coups_possibles) {
        Move move = SerializationUtils.deserialize(webManager.Receive());

        return move;
    }




    /**
     * S'imprime dans la sortie stream
     * @param stream
     * @throws IOException 
     */
    @Override
    public void print(OutputStream stream) throws IOException {
        stream.write("DistantPlayer".getBytes());
        stream.write(' ');
        super.print(stream);
    }
}
