import Global.Configuration;
import Global.Properties;
import Model.ReaderWriter;
import Model.Support.Board;
import View.MainGraphicInterface;
//import Vue.InterfaceGraphique;

import java.io.IOException;

public class Quits {
    //static InterfaceGraphique interfacegraphique;
    static Board plateau;

    /**
     * Permet d'initialiser ka partie. Les parametres de la partie sont definies dans Configuration
     */
    public static void main(String[] args) throws IOException, IllegalArgumentException, IllegalAccessException {
        Properties.load();
        new MainGraphicInterface();
    }
}
