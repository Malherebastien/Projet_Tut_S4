package View;

import javax.swing.*;


public class SelectionJoueur {
    public SelectionJoueur() {

    	String s;

		Object[] possibilities = {"2", "3", "4"};
		s = (String) JOptionPane.showInputDialog(
					null,
					"Choisir le nom de joueurs",
					"Choix du nombre de joueurs",
					JOptionPane.PLAIN_MESSAGE,
					null,
					possibilities,
					"2");
		if( s == null ) {
			System.exit(0);
		}



		//if ( s.equals("2")) new PartieIHM(10, 7, new Joueur("Rouge", "\033[31m"), new Joueur("Vert", "\033[32m"));
		int nbJoueur = Integer.parseInt(s);
		new RemplirNomJoueur(nbJoueur);
    }

    public static void main(String args[]) {
        new SelectionJoueur();
    }

}