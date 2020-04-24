package Modele;

public abstract class Commande {
    Commande next;

    abstract public void Execute();
    abstract public void Dexecute();

}
