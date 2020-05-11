/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controleur.Mediator;
import Model.Players.Player;
import Model.Support.Board;
import Paterns.Observateur;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    Board board;
    Mediator mediator;
    
    JFrame frame;
    BoardGraphic boardGraphic;
    
    boolean maximized;
    
    JToggleButton menu;
    JButton undo, redo;
    InGameMenu inGameMenu;

    
    GraphicInterface(Board plateau, Mediator m) {
        this.board = plateau;
        this.mediator = m;
    }

    public static void start(Mediator m) {
        GraphicInterface vue = new GraphicInterface(m.getPlateau(), m);
        m.addGraphicInterface(vue);
        SwingUtilities.invokeLater(vue);
    }


    @Override
    public void run() {
        // Eléments de l'interface
        frame = new JFrame("Quits");
        boardGraphic = new ViewBoard(this.board);
        inGameMenu = new InGameMenu();
        inGameMenu.setVisible(false);

        // Texte et contrôles à droite de la fenêtre
        Box boxPlayer = Box.createVerticalBox();

        for(Player player : this.board.getPlayers()) {
            try {
                JLabel titre = new JLabel(player.name);
                titre.setAlignmentX(Component.CENTER_ALIGNMENT);
                boxPlayer.add(titre);
            } catch (Exception e) {
                System.out.println("Un joueur est vide");
            }
        }
        
        Box totalMenu = Box.createHorizontalBox();
        
        Box boxMenu = Box.createVerticalBox();
        menu = new JToggleButton("Menu");
        menu.setAlignmentX(Component.LEFT_ALIGNMENT);
        menu.addActionListener((ActionEvent e) -> {
            inGameMenu.setVisible(!inGameMenu.isVisible());
        });
        
        undo = new JButton("Défaire");
        undo.setAlignmentX(Component.LEFT_ALIGNMENT);
        undo.setFocusable(false);
        redo = new JButton("Refaire");
        redo.setAlignmentX(Component.LEFT_ALIGNMENT);
        redo.setFocusable(false);
        boxMenu.add(menu);
        boxMenu.add(undo);
        boxMenu.add(redo);
        totalMenu.add(boxMenu);
        totalMenu.add(inGameMenu);
        
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
        boardGraphic.addMouseListener(new MouseAction(boardGraphic, mediator));
        //frame.addKeyListener(new AdaptateurClavier(control));
        //Timer chrono = new Timer(16, new AdaptateurTemps(control));
        //IA.addActionListener(new AdaptateurIA(control));
        //animation.addActionListener(new AdaptateurAnimations(control));
        //prochain.addActionListener(new AdaptateurProchain(control));
        //annuler.addActionListener(new AdaptateurAnnuler(control));
        //refaire.addActionListener(new AdaptateurRefaire(control));

        // Mise en place de l'interface
        frame.add(totalMenu, BorderLayout.WEST);
        frame.add(boxPlayer, BorderLayout.EAST);
        frame.add(boardGraphic);
        //j.ajouteObservateur(this);
        //chrono.start();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 300);
        frame.setVisible(true);
    }
    
    public void update() {
        boardGraphic.repaint();
    }

    @Override
    public void miseAJour() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
