import java.util.*;

class Essai_Scanner {
    public static void main(String [] args) {
        Scanner my_scanner;
        int chiffre;
		boolean erreur;
		do {
		my_scanner = new Scanner(System.in);
	    erreur = false;
	    try {
    	System.out.println("Saisissez un chiffre");
    	chiffre = my_scanner.nextInt();
    	System.out.println("Vous avez saisi le : " + chiffre);
    	} catch (InputMismatchException e) {
      	erreur = true;
    	System.out.println("Pas un chiffre");
	    } catch (NoSuchElementException e) {
				System.out.println("Aucune ligne saisie");
			}
		} while (erreur);
		my_scanner.close();
    }
}
