/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.ColorPicker;

import Global.Configuration;
import View.ImageQuits;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 *
 * @author Mathis
 */
public class PreviewColor extends JPanel {
    Color c;
    ImageQuits defaultMarble;
    
    public PreviewColor(Color c) {
        super();
        this.c = c;
        super.setPreferredSize(new java.awt.Dimension(100, 100));
        
        String ressource = (String) Configuration.read("DefaultMarble");
        InputStream in = Configuration.charge(ressource);
        try {
            defaultMarble = new ImageQuits(ImageIO.read(in));
        } catch (IOException ex) {
            Logger.getLogger(PreviewColor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        
        super.paintComponent(g);
        g.setColor(c);
        //g.fillRect(0, 0, 100, 100);
        g.fillOval(4, 4, 91, 91);
        
        g.drawImage(defaultMarble.image(), -30, -30, 160, 160, null);
    }
    
    public void updateColor(Color c) {
        this.c = c;
    }
}
