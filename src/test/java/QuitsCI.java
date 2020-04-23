import Global.Configuration;
import Global.Properties;
import Modele.GameManager;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class QuitsCI {

    @Test
    public void testConfiguration() {
        try{
            Properties.Load();
            assertNotNull(Configuration.Lis("Maximized"));
        }
        catch (Exception e){
            fail();
        }
    }

    @Test
    public void testInstanciationModele() {
        GameManager.InstanceGame();
    }

}