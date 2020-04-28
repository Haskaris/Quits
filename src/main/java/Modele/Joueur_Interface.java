package Modele;

public interface Joueur_Interface {
    /*
    * La classe joueur devra implémenter cette interface.
    * Au démarrage, chaque joueur devra appeler GameManager.EnregistrerJoueur(this)
    * Pour l'instant, après 4 joueurs enregistrés, il faudra détruire un joueur d'abord (a voir si on met un remplacement automatique)
    * Si le joueur à besoin de connaitre ses billes, on peut le rajouter dans le retour d'EnregistrerJoueur
    * */


    /**
     * Le joueur devra à ce moment jouer (lancer l'ia ou récupérer l'entrée utilisateur)
     * @param coups_possibles la liste des coups jouable par le joueur
     * @return le coup choisi par le joueur
     */
    Coup Jouer(Coup[] coups_possibles);
}
