import Global.Properties;
import Modele.GameManager;

import java.io.IOException;

public class Quits {

    public static void main(String[] args) throws IOException, IllegalArgumentException, IllegalAccessException {
        Properties.Load();
        GameManager.InstanceGame();
    }
}
