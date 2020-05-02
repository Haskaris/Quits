package Modele;

import Modele.Support.Plateau;

public class Historique {
    Plateau plateau;
    Coup passe;
    Coup futur;

    public Historique(Plateau _plateau){
        plateau = _plateau;
    }

    public void Faire(Coup c){
        if(c == null) return ; // Correspond a une impossibilite de se deplacer pour le player. Rien n'est enregistre
        c.Execute(plateau);
        c.next = passe;
        passe = c;
        futur = null;
    }

    public void Annuler(){
        if(PasseEstVide())return;
        Coup c = passe.next;
        passe.next = null;
        futur = passe;
        passe = c;
        futur.Dexecute(plateau);
    }

    public void Refaire(){
        if(FuturEstVide())return;
        Coup c = futur.next;
        futur.next = null;
        passe = futur;
        futur = c;
        passe.Execute(plateau);
    }

    public boolean PasseEstVide(){
        return passe == null;
    }
    public boolean FuturEstVide(){
        return futur == null;
    }
    public Coup DernierCoup(){return passe;}
}
