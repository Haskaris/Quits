/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vue;

import Global.Configuration;
import Modele.Support.Plateau;
import Modele.Support.Tuile;
import java.io.InputStream;
import java.util.ArrayList;

/**
 *
 * @author Mathis
 */
public class VuePlateau extends PlateauGraphique {
    ArrayList<ImageQuits> tuiles;
    Plateau plateau;
    int largeurTuile;
    int hauteurTuile;
    
    
    // Décalage des éléments (pour pouvoir les animer)
    Vecteur[][] decalages;
    
    VuePlateau(Plateau plateau) {
        this.plateau = plateau;
        //tuile = lisImage("Tuile");
        initTuile();
    }
    
    private void initTuile() {
        tuiles = new ArrayList<>();
        tuiles.add(lisImage("Tuile1"));
        tuiles.add(lisImage("Tuile2"));
        tuiles.add(lisImage("Tuile3"));
        tuiles.add(lisImage("Tuile4"));
        tuiles.add(lisImage("Tuile5"));
        tuiles.add(lisImage("Tuile6"));
        tuiles.add(lisImage("Tuile7"));
        tuiles.add(lisImage("Tuile8"));
    }
    
    private ImageQuits lisImage(String nom) {
        String ressource = (String)Configuration.instance().lis(nom);
        Configuration.instance().logger().info("Lecture de l'image " + ressource + " comme " + nom);
        InputStream in = Configuration.charge(ressource);
        try {
            // Chargement d'une image utilisable dans Swing
            return super.lisImage(in);
        } catch (Exception e) {
            Configuration.instance().logger().severe("Impossible de charger l'image " + ressource);
            System.exit(1);
        }
        return null;
    }


    @Override
    void tracerNiveau() {
        if (decalages == null) {
            decalages = new Vecteur[5][5];
        }
        
        largeurTuile = largeur() / 5;
        hauteurTuile = hauteur() / 5;
        
        largeurTuile = Math.min(largeurTuile, hauteurTuile);
        hauteurTuile = largeurTuile;
        
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                int x = j * largeurTuile;
                int y = i * hauteurTuile;
                Tuile currentTile = plateau.getGrille()[i][j];
                int index = currentTile.getIndexOfColor();
                tracer(tuiles.get(index), x, y, largeurTuile, hauteurTuile);
            }
        }
    }

    @Override
    int hauteurCase() {
        return hauteurTuile;
    }

    @Override
    int largeurCase() {
        return largeurTuile;
    }

    @Override
    public void decale(int l, int c, double dl, double dc) {
        if ((dl != 0) || (dc != 0)) {
            Vecteur v = decalages[l][c];
            if (v == null) {
                    v = new Vecteur();
                    decalages[l][c] = v;
            }
            v.x = dc;
            v.y = dl;
        } else {
            decalages[l][c] = null;
        }
        miseAJour();
    }

    @Override
    public void metAJourDirection(int dL, int dC) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void changeEtape() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
