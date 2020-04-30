import Modele.LecteurRedacteur;
import Modele.Support.Plateau;

import java.io.IOException;

public class Quits {

    public static void main(String[] args) throws IOException, IllegalArgumentException, IllegalAccessException {
        Plateau plateau = new Plateau(4, 5);
        LecteurRedacteur.AffichePartie(plateau);

        //Properties.Load();
        //GameManager.InstanceGame();
    }
}
