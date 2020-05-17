/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import View.Action.MouseAction;
import Controleur.Mediator;


import Paterns.Observateur;
import View.Filters.SaveFilter;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.OverlayLayout;
import javax.swing.SwingUtilities;

/**
 *
 * @author Mathis
 */
public class GraphicInterface implements Runnable, Observateur {
    Mediator mediator;
    
    JFrame frame;
    public BoardGraphic boardGraphic;
    
    boolean maximized;
    
    JToggleButton menu, oneMoveBefore;
    JButton undo, redo;
    JPanel inGameMenu;
    
    ArrayList<JLabel> names;
    
    JLabel nameLabel;
    
    Box boxPlayer, boxPlayerAndBoard;
    
    private final JFileChooser fc;
    
    
    GraphicInterface(Mediator m) {
        this.mediator = m;
        this.names = new ArrayList<>();
        this.fc = new JFileChooser();
        this.fc.setAcceptAllFileFilterUsed(false);
        this.fc.setFileFilter(new SaveFilter());
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

        this.boxPlayerAndBoard = Box.createVerticalBox();
        
        
        this.createMenu();
        this.createPlayers();
        this.createBoard();
        
        // Mise en place de l'interface
        //j.ajouteObservateur(this);
        //chrono.start();
        
        frame.add(boxPlayerAndBoard);
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 600);
        frame.setMinimumSize(new Dimension(500, 600));
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        this.update();
    }
    
    private void createMenu() {
        
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("Menu");
        fileMenu.setMnemonic(KeyEvent.VK_F);
        
        JMenuItem saveItem = new JMenuItem("Sauvegarder");
        saveItem.setToolTipText("Sauvegarder la partie");
        saveItem.addActionListener((event) -> {
            fc.setDialogTitle("Sauvegarder");
            fc.setApproveButtonText("Sauvegarder");
            int returnVal = fc.showOpenDialog(null);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                this.mediator.saveGame(file.getName());
            }
        });
        
        JMenuItem loadItem = new JMenuItem("Charger");
        loadItem.setToolTipText("Charger une partie");
        loadItem.addActionListener((event) -> {
            fc.setDialogTitle("Charger");
            fc.setApproveButtonText("Charger");
            int returnVal = fc.showOpenDialog(null);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                this.mediator.loadGame(file.getName());
            }
        });
        
        JMenuItem startOverItem = new JMenuItem("Recommencer une partie");
        startOverItem.setToolTipText("Recommencer une nouvelle partie avec les mêmes paramètres");
        startOverItem.addActionListener((event) -> {
            this.mediator.resetGame();
        });
        
        JMenuItem rulesItem = new JMenuItem("Règles");
        rulesItem.setToolTipText("Afficher les règles");
        rulesItem.addActionListener((event) -> {
            this.mediator.rules();
        });

        JMenuItem quitItem = new JMenuItem("Quitter");
        //eMenuItem.setMnemonic(KeyEvent.VK_E);
        quitItem.setToolTipText("Quitter le jeu");
        quitItem.addActionListener((event) -> this.mediator.quitGame());

        fileMenu.add(saveItem);
        fileMenu.add(loadItem);
        fileMenu.add(startOverItem);
        fileMenu.add(rulesItem);
        fileMenu.add(quitItem);
        menuBar.add(fileMenu);

        
        Box boxMenu = Box.createHorizontalBox();
        
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
        
        this.oneMoveBefore = new JToggleButton("Revoir");
        this.oneMoveBefore.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.oneMoveBefore.setFocusable(false);
        this.oneMoveBefore.setEnabled(this.mediator.canUndo());
        this.oneMoveBefore.addActionListener((ActionEvent e) -> {
            this.mediator.seeOneMoveBefore(this.oneMoveBefore.isSelected());
        });
        
        menuBar.add(undo);
        menuBar.add(redo);
        menuBar.add(oneMoveBefore);
        
        this.frame.setJMenuBar(menuBar);
      
    }
    
    private void createPlayers() {
        boxPlayer = Box.createHorizontalBox();
        
        nameLabel = new JLabel("");
        
        this.boxPlayer.add(new JLabel("Tour de "));
        this.boxPlayer.add(nameLabel);
        this.boxPlayerAndBoard.add(boxPlayer, BorderLayout.NORTH);
        
    }
    
    private void createBoard() {
        this.boardGraphic = new ViewBoard(this.mediator.getBoard());
        this.boardGraphic.addMouseListener(new MouseAction(this.boardGraphic, this.mediator));
        this.boxPlayerAndBoard.add(this.boardGraphic, BorderLayout.SOUTH);
    }
    
    public void update() {
        boardGraphic.repaint();
        this.undo.setEnabled(this.mediator.canUndo());
        this.oneMoveBefore.setEnabled(this.mediator.canUndo());
        this.redo.setEnabled(this.mediator.canRedo());
        /*this.names.forEach((name) -> {
            name.setForeground(Color.black);
        });
        this.names.get(this.mediator.getBoard().currentPlayer).setForeground(Color.red);*/
        nameLabel.setText(this.mediator.getBoard().getCurrentPlayer().name);
        System.err.println(this.mediator.getBoard().getCurrentPlayer().name);
        nameLabel.setForeground(this.mediator.getBoard().getCurrentPlayer().color);
    }
    
    public void dispose() {
        this.frame.setVisible(false);
        this.frame.dispose();
    }
    
    public void blockUndoRedo() {
        this.undo.setEnabled(false);
        this.redo.setEnabled(false);
        this.oneMoveBefore.setEnabled(true);
    }

    @Override
    public void miseAJour() {
        //Oui
    }
    
}
