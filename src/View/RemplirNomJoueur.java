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

		String message = "";
		for ( int i = 0; i < nb; i ++)
		{
			String nom;
			do{
				//Boite de dialogue qui demande a changer le nom du joueur
				nom = (String) JOptionPane.showInputDialog( null, "Modifier nom du joueur " + i, "Nom du Joueur" );
				if( nom != null )
				{
					nom = nom.toUpperCase();
					break;
				}
			}
			while(true);
			Joueur j = new Joueur(tabCouleur[i],tabCouleur[i]);
			j.setNom(nom);
			tabJoueur[i] = j;
			message += tabJoueur[i].getNom() + ",\n";
		}
		JOptionPane.showMessageDialog( null, message + "ont été créés avec succès!");

		new Tablier(tabJoueur);
	}

}
