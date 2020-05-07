import Global.Configuration;
import Global.Properties;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class BaseTest {

    @Test
    public void TestConfiguration() {
        try{
            Properties.load();
            assertNotNull(Configuration.read("Maximized"));
        }
        catch (Exception e){
            fail();
        }
        System.out.println("Configuration OK");
    }

}