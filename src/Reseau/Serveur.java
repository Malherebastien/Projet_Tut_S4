package Reseau;

import Controler.PartieConsole;
import Model.Joueur;

import java.io.IOException;
import java.net.*;

public class Serveur
{

	private DatagramPacket [] tabClient;
	private boolean partieCommencerBok;
	private int nombreJoueur;
	private String [] tabNomJ;
	private String [] tabCouleur;
	private String [] tabCodeCouleur;
	private Joueur [] tabJoueur;
	private DatagramSocket ds;
	private int nbCol, nbLigne;
	private PartieConsole partie;

	public Serveur ()
	{
		nombreJoueur = 0;

		partieCommencerBok = false;

		tabNomJ = new String[2];
		tabClient = new DatagramPacket[2];
		tabJoueur = new Joueur[2];

		tabCouleur = new String[4];
		tabCouleur[0] = "ROUGE";
		tabCouleur[1] = "VERT";
		tabCouleur[2] = "BLEU";
		tabCouleur[3] = "JAUNE";

		tabCodeCouleur = new String[4];
		tabCodeCouleur[0] = "#f00";
		tabCodeCouleur[1] = "0f0";
		tabCodeCouleur[2] = "00f";
		tabCodeCouleur[3] = "ff0";

		lancerServeur();
	}

	public void lancerServeur()
	{
		try {
			//crée une Socket au port 2009
			ds = new DatagramSocket(2009);

			while ( true)
			{
				DatagramPacket msg = new DatagramPacket(new byte[512], 512);
				ds.receive(msg);

				String msgRecu = new String(msg.getData());
				System.out.println("message Recu : " + msgRecu);
				String texteReponse;

				if ( msgRecu.contains("Name :") && nombreJoueur < 2)
				{
					String couleurJ = tabCouleur[nombreJoueur];
					String codeCouleurJ = tabCodeCouleur[nombreJoueur];
					texteReponse = "Bonjour " + msgRecu.substring(6);
					String numJ = "" + (nombreJoueur+1);

					envoyerMsg(texteReponse, msg);
					envoyerMsg(numJ, msg);
					envoyerMsg(couleurJ, msg);

					tabNomJ[nombreJoueur] = msgRecu.substring(6);
					tabClient[nombreJoueur] = msg;
					nombreJoueur ++;

				}
				if ( nombreJoueur == 2 )
				{
					if ( !partieCommencerBok )
					{
						partieCommencerBok = true;
						texteReponse = "La partie va commencer";

						for (int i = 0; i < nombreJoueur; i++)
						{
							envoyerMsg(texteReponse, tabClient[i] );
						}

						nbLigne = (int) (Math.random() * 5) + 4;
						nbCol   = (int) (Math.random() * 5) + 4;

						partie = new PartieConsole(nbLigne, nbCol, nombreJoueur);
						PartieConsole.joueurs[0].setNom(tabNomJ[0]);
						PartieConsole.joueurs[1].setNom(tabNomJ[1]);
						tabJoueur[0] = PartieConsole.joueurs[0];
						tabJoueur[1] = PartieConsole.joueurs[1];

						for (int i = 0; i < nombreJoueur; i++)
						{
							envoyerMsg(partie.afficherTableauContainer(nbCol, nbLigne), tabClient[i]);//affiche tab
						}

						// recois le joueur actif, et le joueur actif recoi le message suivant
						for (int i = 0; i < tabJoueur.length; i++)
						{
							if ( partie.getJoueurActif()== tabJoueur[i] )
							{
								texteReponse = "A vous de Jouer " + tabJoueur[i].getCouleur();
								envoyerMsg(texteReponse, tabClient[i]);
							}
						}

					}

				}

			}
		} catch (IOException ioe) { ioe.printStackTrace(); }
	}



	private void envoyerMsg(String msg, DatagramPacket dpReceveurMessage) throws IOException
	{
		DatagramPacket reponse = new DatagramPacket(msg.getBytes(), msg.length(), dpReceveurMessage.getAddress(), dpReceveurMessage.getPort());
		ds.send(reponse);
	}


	public static void main (String args[]) throws Exception
	{
		new Serveur();
	}

}