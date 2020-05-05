import Global.Configuration;
import Global.Properties;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class BaseTest {

    @Test
    public void TestConfiguration() {
        try{
            Properties.Load();
            assertNotNull(Configuration.Lis("Maximized"));
        }
        catch (Exception e){
            fail();
        }
        System.out.println("Configuration OK");
    }

}