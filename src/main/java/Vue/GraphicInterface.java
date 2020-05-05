/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vue;

import Controleur.Mediateur;
import Modele.Support.Plateau;
import Paterns.Observateur;
import javax.swing.SwingUtilities;

/**
 *
 * @author Mathis
 */
public class GraphicInterface implements Runnable, Observateur {
    Plateau plateau;
    Mediateur mediateur;
    
    GraphicInterface(Plateau plateau, Mediateur m) {
        this.plateau = plateau;
        this.mediateur = m;
    }

    public static void demarrer(Plateau plateau, Mediateur m) {
        GraphicInterface vue = new GraphicInterface(plateau, m);
        m.addGraphicInterface(vue);
        SwingUtilities.invokeLater(vue);
    }



    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void miseAJour() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
