package View;
import java.awt.*;

// Classe servant uniquement à casser la dépendance à Swing
public class ImageQuits {
    Image img;

    ImageQuits(Image i) {
        img = i;
    }

    Image image() {
        return img;
    }
}
