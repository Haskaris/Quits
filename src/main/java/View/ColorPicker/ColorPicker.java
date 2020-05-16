
package View.ColorPicker;

import View.EditPlayer;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ColorPicker extends JDialog {
    Color currentColor;
    JColorChooser colorChooser;
    JButton valid;
    JButton cancel;
    PreviewColor previewColor;
    EditPlayer parent;
    
    public ColorPicker(Color color) {
        super();
        super.setTitle("Color Picker");
        
        super.setMinimumSize(new Dimension(700,500));
        this.currentColor = color;
        
        this.previewColor = new PreviewColor(this.currentColor);
        
        this.colorChooser = new JColorChooser(this.currentColor);
        this.colorChooser.setPreviewPanel(previewColor);
        this.colorChooser.getSelectionModel().addChangeListener((ChangeEvent e) -> {
            previewColor.updateColor(colorChooser.getColor());
        });
        this.valid = new JButton("Valider");
        this.valid.addActionListener((ActionEvent e) -> {
            this.currentColor = colorChooser.getColor();
            
            parent.updateButtonColor(this.currentColor);
            parent.playerColor = this.currentColor;
            
            setVisible(false);
            dispose();
        });
        this.cancel = new JButton("Annuler");
        this.cancel.addActionListener((ActionEvent e) -> {
            setVisible(false);
            dispose();
        });
        JPanel tmp = new JPanel();
        tmp.add(this.colorChooser);
        tmp.add(this.cancel);
        tmp.add(this.valid);
        super.add(tmp);
        
        super.setLocationRelativeTo(null);
        super.setVisible(false);
    }
    
    public void showDialog(EditPlayer parent) {
        this.parent = parent;
        this.colorChooser.getSelectionModel().setSelectedColor(parent.playerColor);
        this.currentColor = parent.playerColor;
        previewColor.updateColor(parent.playerColor);
        setVisible(true);
    }
    
}
