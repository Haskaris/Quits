import Modele.GameManager;
import Global.Properties;

import java.io.IOException;

public class Quits {

    public static void main(String[] args) throws IOException, IllegalArgumentException, IllegalAccessException {
        Properties.Load();
        GameManager.InstanceGame();
    }
}
