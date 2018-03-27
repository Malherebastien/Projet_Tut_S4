package View;

import Model.Joueur;

import javax.swing.*;

public class RemplirNomJoueur
{
	public RemplirNomJoueur(int nb)
	{
		Joueur [] tabJoueur = new Joueur[nb];
		String [] tabCouleur = new String[4];
		tabCouleur[0] = "ROUGE";
		tabCouleur[1] = "VERT";
		tabCouleur[2] = "BLEU";
		tabCouleur[3] = "JAUNE";

		String [] tabCodeCouleur = new String[4];
		tabCodeCouleur[0] = "#f00";
		tabCodeCouleur[1] = "0f0";
		tabCodeCouleur[2] = "00f";
		tabCodeCouleur[3] = "ff0";

		String message = "";
		for ( int i = 0; i < nb; i ++)
		{
			String nom;

			//Boite de dialogue qui demande a changer le nom du joueur
			nom = (String) JOptionPane.showInputDialog( null, "Modifier nom du joueur " + i, "Nom du Joueur" );
			if( nom == null )
			{
				System.exit(0);
			}
			nom = nom.toUpperCase();

			Joueur j = new Joueur(tabCouleur[i],tabCodeCouleur[i]);
			j.setNom(nom);
			tabJoueur[i] = j;
			message += tabJoueur[i].getNom() + ",\n";
		}

		new Tablier(tabJoueur);
	}

}
