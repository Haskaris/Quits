package Modele.Joueurs;

import Modele.Coup;

import java.util.List;

public abstract class Joueur {
    public String nom;
    public Integer couleur;

    public Joueur(String _nom, int _couleur){
        nom = _nom;
        couleur = _couleur;
    }


    /**
     * Le joueur devra à ce moment jouer (lancer l'ia ou récupérer l'entrée utilisateur)
     * @param coups_possibles la liste des coups jouable par le joueur
     * @return le coup choisi par le joueur
     */
    abstract public Coup Jouer(List<Coup> coups_possibles);
}
