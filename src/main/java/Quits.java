import Global.Properties;
import Model.Support.Board;
import View.MainGraphicInterface;

import java.io.IOException;

public class Quits {
    static Board plateau;

    /**
     * Permet d'initialiser ka partie.Les parametres de la partie sont definies dans Configuration
     * @param args
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        Properties.load();
        MainGraphicInterface mainGraphicInterface = new MainGraphicInterface();
    }
}
