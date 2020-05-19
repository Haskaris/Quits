package View;
import java.awt.*;

// Classe servant uniquement à casser la dépendance à Swing
public class ImageQuits {
    Image img;

    public ImageQuits(Image i) {
        img = i;
    }

    public Image image() {
        return img;
    }
}
