/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vue;

import Controleur.Mediateur;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 *
 * @author Mathis
 */
public class MouseAction extends MouseAdapter {
    PlateauGraphique plateau;
    Mediateur mediateur;
    
    MouseAction(PlateauGraphique plateau, Mediateur m) {
        this.plateau = plateau;
        this.mediateur = m;
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
        int l = e.getY() / plateau.hauteurCase();
        int c = e.getX() / plateau.largeurCase();
        
        mediateur.mouseClick(l, c);
        //plateau.repaint();
        plateau.tracerNiveau();
    }
}
