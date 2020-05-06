/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vue;

import Controleur.Mediateur;
import Modele.Joueurs.Joueur;
import Modele.Support.Plateau;
import Paterns.Observateur;
import java.awt.BorderLayout;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;

/**
 *
 * @author Mathis
 */
public class GraphicInterface implements Runnable, Observateur {
    Plateau plateau;
    Mediateur mediateur;
    
    JFrame frame;
    PlateauGraphique niv;
    boolean maximized;
    JLabel nbPas, nbPoussees;
    JToggleButton IA, animation;
    
    JButton menu, undo, redo;

    
    GraphicInterface(Plateau plateau, Mediateur m) {
        this.plateau = plateau;
        this.mediateur = m;
    }

    public static void demarrer(Mediateur m) {
        GraphicInterface vue = new GraphicInterface(m.getPlateau(), m);
        m.addGraphicInterface(vue);
        SwingUtilities.invokeLater(vue);
    }


    @Override
    public void run() {
        // Eléments de l'interface
        frame = new JFrame("Quits");
        niv = new VuePlateau(this.plateau);

        // Texte et contrôles à droite de la fenêtre
        Box boxPlayer = Box.createVerticalBox();

        for(Joueur player : this.plateau.joueurs) {
            try {
                JLabel titre = new JLabel(player.nom);
                titre.setAlignmentX(Component.CENTER_ALIGNMENT);
                boxPlayer.add(titre);
            } catch (Exception e) {
                System.out.println("Un joueur est vide");
            }
        }
        
        Box boxMenu = Box.createVerticalBox();
        
        menu = new JButton("Menu");
        menu.setAlignmentX(Component.LEFT_ALIGNMENT);
        menu.setFocusable(false);
        undo = new JButton("Défaire");
        undo.setAlignmentX(Component.LEFT_ALIGNMENT);
        undo.setFocusable(false);
        redo = new JButton("Refaire");
        redo.setAlignmentX(Component.LEFT_ALIGNMENT);
        redo.setFocusable(false);
        boxMenu.add(menu);
        boxMenu.add(undo);
        boxMenu.add(redo);
        
        // Annuler / refaire
        //BoutonAnnuler annuler = new BoutonAnnuler(j);
        //annuler.setFocusable(false);
        //BoutonRefaire refaire = new BoutonRefaire(j);
        //refaire.setFocusable(false);
        //Box annulerRefaire = Box.createHorizontalBox();
        //annulerRefaire.add(annuler);
        //annulerRefaire.add(refaire);
        //annulerRefaire.setAlignmentX(Component.CENTER_ALIGNMENT);
        //boiteTexte.add(annulerRefaire);

        // Retransmission des évènements au contrôleur
        niv.addMouseListener(new MouseAction(niv, mediateur));
        //frame.addKeyListener(new AdaptateurClavier(control));
        //Timer chrono = new Timer(16, new AdaptateurTemps(control));
        //IA.addActionListener(new AdaptateurIA(control));
        //animation.addActionListener(new AdaptateurAnimations(control));
        //prochain.addActionListener(new AdaptateurProchain(control));
        //annuler.addActionListener(new AdaptateurAnnuler(control));
        //refaire.addActionListener(new AdaptateurRefaire(control));

        // Mise en place de l'interface
        frame.add(boxMenu, BorderLayout.WEST);
        frame.add(boxPlayer, BorderLayout.EAST);
        frame.add(niv);
        //j.ajouteObservateur(this);
        //chrono.start();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 300);
        frame.setVisible(true);
    }
    
    public void update() {
        niv.repaint();
    }

    @Override
    public void miseAJour() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
