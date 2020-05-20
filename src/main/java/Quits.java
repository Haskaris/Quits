import Global.Properties;
import View.StartScreen;
import java.awt.EventQueue;

import java.io.IOException;

public class Quits {

    /**
     * Permet d'initialiser ka partie.Les parametres de la partie sont definies dans Configuration
     * @param args
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        Properties.load();
        EventQueue.invokeLater(() -> {
            StartScreen startScreen = new StartScreen();
            startScreen.setVisible(true);
        });
    }
}
