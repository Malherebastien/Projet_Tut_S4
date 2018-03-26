package View;

import Model.Joueur;

import javax.swing.*;
import java.util.ArrayList;

public class RemplirNomJoueur
{
	public RemplirNomJoueur(int nb)
	{
		Joueur [] tabJoueur = new Joueur[nb];
		String message = "";
		for ( int i = 0; i < nb; i ++)
		{
			String nom;
			do{
				//Boite de dialogue qui demande a changer le nom du joueur
				nom = (String) JOptionPane.showInputDialog( null, "Modifier couleur du joueur " + i, "Couleur du Joueur" );
				if( nom != null )
				{
					break;
				}
			}
			while(true);
			Joueur j = new Joueur(nom,nom);
			tabJoueur[i] = j;
			message += tabJoueur[i].getCouleur() + ",\n";
		}
		JOptionPane.showMessageDialog( null, message + "ont été créés avec succès!");

		new Tablier(tabJoueur);
	}

}
