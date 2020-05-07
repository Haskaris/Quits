package Modele.Joueurs;

import Global.Tools.Dir;
import Modele.Coup;
import Modele.Support.Bille;
import java.awt.Color;

import java.util.ArrayList;
import java.util.List;

public abstract class Joueur {

    public String nom;
    public Color couleur;
    public ArrayList<Bille> billes;
    public int etat;//0 : On attend la sélection d'une bille | 1 : une bille ou une tuile a été sélectionnée, on affiche les choix possibles
    private Dir pointDeDepart;

    Joueur(String _nom, Color _couleur) {
        etat = 0;
        nom = _nom;
        couleur = _couleur;
        billes = new ArrayList<>();
    }

    Joueur(String _nom, Color _couleur, Dir p) {
        etat = 0;
        nom = _nom;
        couleur = _couleur;
        billes = new ArrayList<>();
        this.pointDeDepart = p;
    }

    public void setPointDeDepart(Dir p) {
        this.pointDeDepart = p;
    }

    public Dir getPointDeDepart() {
        return this.pointDeDepart;
    }

    public Bille addBille() {
        this.billes.add(new Bille(this.couleur));
        return this.billes.get(this.billes.size() - 1);
    }

    /**
     * Enlève la bille de la liste des billes du joueur Retourne vrai si elle a
     * été supprimée, faux sinon
     *
     * @param b
     * @return
     */
    public boolean removeBille(Bille b) {
        return this.billes.remove(b);
    }

    /**
     * Le joueur devra à ce moment jouer (lancer l'ia ou récupérer l'entrée
     * utilisateur)
     *
     * @param coups_possibles la liste des coups jouable par le joueur
     * @return le coup choisi par le joueur
     */
    abstract public Coup Jouer(List<Coup> coups_possibles);

}
