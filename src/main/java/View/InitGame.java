/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import View.Filters.SaveFilter;
import Controleur.Mediator;
import Global.Configuration;
import Global.Tools;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;

/**
 *
 * @author Mathis
 */
public class InitGame extends javax.swing.JPanel {

    Mediator mediator;
    Tools.GameMode gameMode;
    MainGraphicInterface frame;
    private final JFileChooser fc;
    private ArrayList<EditPlayer> listOfEditPlayers;
    private ImageQuits StartScreenBackground;
    private ImageQuits PlayButton;
    
    /**
     * Creates new form InitGameAuto
     * @param frame
     * @param mediateur
     */
    InitGame(MainGraphicInterface frame, Mediator mediateur) {
        initComponents();
        this.gameMode = Tools.GameMode.TwoPlayersFiveBalls;
        this.listOfEditPlayers = new ArrayList<>();
        
        //Ajout des editPlayers dans la liste de sauvegarde
        this.listOfEditPlayers.add(new EditPlayer("Joueur A", Color.BLUE));
        this.listOfEditPlayers.add(new EditPlayer("Joueur B", Color.RED));
        this.listOfEditPlayers.add(new EditPlayer("Joueur C", Color.YELLOW));
        this.listOfEditPlayers.add(new EditPlayer("Joueur D", Color.GREEN));
        
        this.editPlayers.add(this.listOfEditPlayers.get(0));
        this.editPlayers.add(this.listOfEditPlayers.get(1));
        
        this.mediator = mediateur;
        this.frame = frame;
        
        this.fc = new JFileChooser();
        this.fc.setAcceptAllFileFilterUsed(false);
        this.fc.setFileFilter(new SaveFilter());
        
        super.updateUI();
        initTile();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonRules = new javax.swing.JButton();
        labelGameMode = new javax.swing.JLabel();
        ButtonPlay = new javax.swing.JButton();
        gameModeList = new javax.swing.JComboBox<>();
        editPlayers = new javax.swing.JPanel();
        loadButton = new javax.swing.JButton();

        setMinimumSize(new java.awt.Dimension(643, 158));

        ImageIcon MyImage5 = new javax.swing.ImageIcon(getClass().getResource("/ChoiceGameMode/rules_button.png"));
        Image img5 = MyImage5.getImage();
        Image newImg5 = img5.getScaledInstance(114, 46, Image.SCALE_SMOOTH);
        ImageIcon image5 = new ImageIcon(newImg5);
        buttonRules.setIcon(image5);
        buttonRules.setBorderPainted(false);
        buttonRules.setPreferredSize(new java.awt.Dimension(114, 46));
        ImageIcon MyImage6 = new javax.swing.ImageIcon(getClass().getResource("/ChoiceGameMode/rules_button_pressed.png"));
        Image img6 = MyImage6.getImage();
        Image newImg6 = img6.getScaledInstance(114, 46, Image.SCALE_SMOOTH);
        ImageIcon image6 = new ImageIcon(newImg6);
        buttonRules.setPressedIcon(image6);
        buttonRules.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonRulesActionPerformed(evt);
            }
        });

        ImageIcon MyImage7 = new javax.swing.ImageIcon(getClass().getResource("/ChoiceGameMode/game_mode.png"));
        Image img7 = MyImage7.getImage();
        Image newImg7 = img7.getScaledInstance(124, 14, Image.SCALE_SMOOTH);
        ImageIcon image7 = new ImageIcon(newImg7);
        labelGameMode.setIcon(image7);
        labelGameMode.setFont(new java.awt.Font("Backtrack Demo", 0, 11)); // NOI18N
        labelGameMode.setPreferredSize(new java.awt.Dimension(124, 14));

        ImageIcon MyImage = new javax.swing.ImageIcon(getClass().getResource("/ChoiceGameMode/play_button.png"));
        Image img = MyImage.getImage();
        Image newImg = img.getScaledInstance(100, 46, Image.SCALE_SMOOTH);
        ImageIcon image = new ImageIcon(newImg);
        ButtonPlay.setIcon(image);
        ButtonPlay.setBorderPainted(false);
        ButtonPlay.setMaximumSize(new java.awt.Dimension(62, 32));
        ButtonPlay.setMinimumSize(new java.awt.Dimension(62, 32));
        ButtonPlay.setPreferredSize(new java.awt.Dimension(100, 46));
        ImageIcon MyImage2 = new javax.swing.ImageIcon(getClass().getResource("/ChoiceGameMode/play_button_pressed.png"));
        Image img2 = MyImage2.getImage();
        Image newImg2 = img2.getScaledInstance(100, 46, Image.SCALE_SMOOTH);
        ImageIcon image2 = new ImageIcon(newImg2);
        ButtonPlay.setPressedIcon(image2);
        ButtonPlay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonPlayActionPerformed(evt);
            }
        });

        gameModeList.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "2 joueurs 5 billes", "2 joueurs 3 billes", "4 joueurs 3 billes" }));
        gameModeList.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                gameModeListItemStateChanged(evt);
            }
        });

        editPlayers.setOpaque(false);
        editPlayers.setLayout(new javax.swing.BoxLayout(editPlayers, javax.swing.BoxLayout.Y_AXIS));

        ImageIcon MyImage3 = new javax.swing.ImageIcon(getClass().getResource("/ChoiceGameMode/load_button.png"));
        Image img3 = MyImage3.getImage();
        Image newImg3 = img3.getScaledInstance(175, 71, Image.SCALE_SMOOTH);
        ImageIcon image3 = new ImageIcon(newImg3);
        loadButton.setIcon(image3);
        loadButton.setBorderPainted(false);
        loadButton.setPreferredSize(new java.awt.Dimension(175, 71));
        ImageIcon MyImage4 = new javax.swing.ImageIcon(getClass().getResource("/ChoiceGameMode/load_button_pressed.png"));
        Image img4 = MyImage4.getImage();
        Image newImg4 = img4.getScaledInstance(175, 71, Image.SCALE_SMOOTH);
        ImageIcon image4 = new ImageIcon(newImg4);
        loadButton.setPressedIcon(image4);
        loadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(buttonRules, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(labelGameMode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(gameModeList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(editPlayers, javax.swing.GroupLayout.DEFAULT_SIZE, 392, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(loadButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(ButtonPlay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(buttonRules, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(gameModeList)
                            .addComponent(labelGameMode, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(editPlayers, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(loadButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ButtonPlay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void gameModeListItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_gameModeListItemStateChanged
        this.editPlayers.removeAll();
            switch(this.gameModeList.getSelectedIndex()) {
                case 0:
                    this.editPlayers.add(this.listOfEditPlayers.get(0));
                    this.editPlayers.add(this.listOfEditPlayers.get(1));
                    this.gameMode = Tools.GameMode.TwoPlayersFiveBalls;
                    this.frame.updateSize(725, 250);
                    break;
                case 1:
                    this.editPlayers.add(this.listOfEditPlayers.get(0));
                    this.editPlayers.add(this.listOfEditPlayers.get(1));
                    this.gameMode = Tools.GameMode.TwoPlayersThreeBalls;
                    this.frame.updateSize(725, 250);
                    break;
                case 2:
                    this.editPlayers.add(this.listOfEditPlayers.get(0));
                    this.editPlayers.add(this.listOfEditPlayers.get(1));
                    this.editPlayers.add(this.listOfEditPlayers.get(2));
                    this.editPlayers.add(this.listOfEditPlayers.get(3));
                    this.gameMode = Tools.GameMode.FourPlayersFiveBalls;
                    this.frame.updateSize(725, 350);
                    break;
            }
        this.updateUI();
    }//GEN-LAST:event_gameModeListItemStateChanged

    private void ButtonPlayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonPlayActionPerformed
        this.mediator.initGame(this.gameMode);
    }//GEN-LAST:event_ButtonPlayActionPerformed

    private void buttonRulesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonRulesActionPerformed
        this.mediator.rules();
    }//GEN-LAST:event_buttonRulesActionPerformed

    private void loadButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadButtonActionPerformed
        fc.setDialogTitle("Charger");
        fc.setApproveButtonText("Charger");
        int returnVal = fc.showOpenDialog(this);
        
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            this.mediator.loadGame(file.getName());
        }
    }//GEN-LAST:event_loadButtonActionPerformed

    /**
     * Permet de récupérer la liste des EditPlayers affiché dans le panel
     * @return Retourne la liste des editPlayers affiché dans le panel
     */
    public ArrayList<EditPlayer> getEditsPlayers() {
        ArrayList<EditPlayer> tmp = new ArrayList<>();
        //Je récupère tous le contenu de mon panel editPlayers
        //Il ne devrait y avoir que des EditPlayers
        Component[] components = this.editPlayers.getComponents();
        for (Component c: components) {
            //Si le component récupéré est un EditPlayer
            if (c.getClass().equals(EditPlayer.class)) {
                //Je l'ajoute à ma liste
                tmp.add((EditPlayer)c);
            }
        }
        return tmp;
    }
    
    private void initTile() {
        StartScreenBackground = readImage("StartScreenBackground");
    }
    
    private ImageQuits readImage(String name) {
        String ressource = (String) Configuration.read(name);
        Configuration.instance().logger().info("Image " + ressource + " read as " + name);
        InputStream in = Configuration.charge(ressource);
        try {
            // Chargement d'une image utilisable dans Swing
            return new ImageQuits(ImageIO.read(in));
        } catch (IOException e) {
            Configuration.instance().logger().severe("Image " + ressource + " impossible to charge.");
            System.exit(1);
        }
        return null;
    }
    
    
    @Override
    protected void paintComponent(Graphics g) {
        //ImageQuits i = new ImageQuits(Configuration.StartScreenBackground);
        super.paintComponent(g);
        g.drawImage(StartScreenBackground.image(), 0, 0, null);
    }
    
    
    public void reset() {
        this.editPlayers.removeAll();
        this.listOfEditPlayers.clear();
        
        //Ajout des editPlayers dans la liste de sauvegarde
        this.listOfEditPlayers.add(new EditPlayer("Joueur A", Color.BLUE));
        this.listOfEditPlayers.add(new EditPlayer("Joueur B", Color.RED));
        this.listOfEditPlayers.add(new EditPlayer("Joueur C", Color.YELLOW));
        this.listOfEditPlayers.add(new EditPlayer("Joueur D", Color.GREEN));
        
        this.editPlayers.add(this.listOfEditPlayers.get(0));
        this.editPlayers.add(this.listOfEditPlayers.get(1));
        
        this.gameModeList.setSelectedIndex(0);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ButtonPlay;
    private javax.swing.JButton buttonRules;
    private javax.swing.JPanel editPlayers;
    private javax.swing.JComboBox<String> gameModeList;
    private javax.swing.JLabel labelGameMode;
    private javax.swing.JButton loadButton;
    // End of variables declaration//GEN-END:variables
}
