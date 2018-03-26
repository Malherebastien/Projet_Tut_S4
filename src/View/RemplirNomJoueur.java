package View;

import Model.Joueur;

import javax.swing.*;
import java.util.ArrayList;

public class RemplirNomJoueur
{
	public RemplirNomJoueur(int nb)
	{
		ArrayList <Joueur> lJoueur = new ArrayList<>();

		for ( int i = 1; i <= nb; i ++)
		{
			String nom;
			do{
				//Boite de dialogue qui demande a changer le nom du joueur
				nom = (String) JOptionPane.showInputDialog( null, "Modifier nom du joueur " + i, "Nom du Joueur" );
				if( nom != null )
				{
					break;
				}
			}
			while(true);
			Joueur j = new Joueur(nom,nom);
			lJoueur.add(j);
			JOptionPane.showMessageDialog( null, j.getCouleur() + " à été créé !");
		}

	}

}
