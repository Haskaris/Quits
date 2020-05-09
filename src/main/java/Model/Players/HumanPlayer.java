package Model.Players;

import Global.Tools.PlayerStatus;
import Model.Move;
import java.awt.Color;
import java.io.IOException;
import java.io.OutputStream;

import java.util.List;

public class HumanPlayer extends Player {

    private PlayerStatus status;

    public HumanPlayer(String name, Color color) {
        super(name, color);
        status = PlayerStatus.MarbleSelection;
    }

    public void setStatus(PlayerStatus status) {
        this.status = status;
    }

    public PlayerStatus getStatus() {
        return status;
    }

    @Override
    public Move Jouer(List<Move> coups_possibles) {
        return coups_possibles.get(0);
    }

    /**
     * S'imprime dans la sortie stream
     *
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
