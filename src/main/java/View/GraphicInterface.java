/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import View.Action.MouseAction;
import Controleur.FileGestion;
import Controleur.Mediator;

import Model.Players.Player;
import Model.Support.Board;

import Paterns.Observateur;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
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
    Mediator mediator;
    
    JFrame frame;
    BoardGraphic boardGraphic;
    
    boolean maximized;
    
    JToggleButton menu;
    JButton undo, redo;
    InGameMenu inGameMenu;
    
    ArrayList<JLabel> names;
    
    Box totalMenu, boxPlayer;
    
    
    GraphicInterface(Mediator m) {
        this.mediator = m;
        this.names = new ArrayList<>();
    }

    public static void start(Mediator m) {
        GraphicInterface view = new GraphicInterface(m);
        m.setGraphicInterface(view);
        SwingUtilities.invokeLater(view);
    }
    
    /**
     * Néttoie la fenêtre et recrée une partie avec les nouveaux paramètres
     * du mediateur
     */
    public void reset() {
        //Je supprime la fenêtre
        this.frame.dispose();
        
        //Je refais la fenêtre
        this.run();
    }

    @Override
    public void run() {
        this.frame = new JFrame("Quits");
        this.inGameMenu = new InGameMenu(this.mediator);

        this.createPlayers();
        this.createMenu();
        this.createBoard();
        
        // Mise en place de l'interface
        //j.ajouteObservateur(this);
        //chrono.start();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 300);
        frame.setVisible(true);
        this.update();
    }
    
    private void createMenu() {
        totalMenu = Box.createHorizontalBox();
        
        Box boxMenu = Box.createVerticalBox();
        this.menu = new JToggleButton("Menu");
        this.menu.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.menu.addActionListener((ActionEvent e) -> {
            this.inGameMenu.setVisible(!this.inGameMenu.isVisible());
        });
        
        this.undo = new JButton("Défaire");
        this.undo.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.undo.setFocusable(false);
        this.undo.setEnabled(this.mediator.canUndo());
        this.undo.addActionListener((ActionEvent e) -> {
            this.mediator.undo();
        });
        
        this.redo = new JButton("Refaire");
        this.redo.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.redo.setFocusable(false);
        this.redo.setEnabled(this.mediator.canRedo());
        this.redo.addActionListener((ActionEvent e) -> {
            this.mediator.redo();
        });
        
        boxMenu.add(this.menu);
        boxMenu.add(this.undo);
        boxMenu.add(this.redo);
        totalMenu.add(boxMenu);
        totalMenu.add(this.inGameMenu);
        
        this.frame.add(totalMenu, BorderLayout.WEST);
    }
    
    private void createPlayers() {
        boxPlayer = Box.createVerticalBox();

        this.mediator.getBoard().getPlayers().forEach((player) -> {
            JLabel titre = new JLabel(player.name);
            titre.setAlignmentX(Component.CENTER_ALIGNMENT);
            boxPlayer.add(titre);
            this.names.add(titre);
        });
        
        this.frame.add(boxPlayer, BorderLayout.EAST);
    }
    
    private void createBoard() {
        this.boardGraphic = new ViewBoard(this.mediator.getBoard());
        this.boardGraphic.addMouseListener(new MouseAction(this.boardGraphic, this.mediator));
        this.frame.add(this.boardGraphic);
    }
    
    public void update() {
        boardGraphic.repaint();
        this.undo.setEnabled(this.mediator.canUndo());
        this.redo.setEnabled(this.mediator.canRedo());
        this.names.forEach((name) -> {
            name.setForeground(Color.black);
        });
        this.names.get(this.mediator.getBoard().currentPlayer).setForeground(Color.red);
    }

    @Override
    public void miseAJour() {
        //Oui
    }
    
}
