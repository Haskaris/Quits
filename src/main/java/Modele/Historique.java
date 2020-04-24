package Modele;

public class Historique {
    Commande passe;
    Commande futur;

    public void Faire(Commande c){
        if(c == null) return ; // Correspond a une impossibilite de se deplacer pour le player. Rien n'est enregistre
        c.Execute();
        c.next = passe;
        passe = c;
        futur = null;
    }

    public void Annuler(){
        if(PasseEstVide())return;
        Commande c = passe.next;
        passe.next = null;
        futur = passe;
        passe = c;
        futur.Dexecute();
    }

    public void Refaire(){
        if(FuturEstVide())return;
        Commande c = futur.next;
        futur.next = null;
        passe = futur;
        futur = c;
        passe.Execute();
    }

    public boolean PasseEstVide(){
        return passe == null;
    }
    public boolean FuturEstVide(){
        return futur == null;
    }
}
