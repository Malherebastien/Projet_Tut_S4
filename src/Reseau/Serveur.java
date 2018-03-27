package Reseau;

import Controler.PartieConsole;

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
	private DatagramSocket ds;

	public Serveur () throws IOException {
		//crée une Socket au port 2009
		ds = new DatagramSocket(2009);

		nombreJoueur = 0;

		partieCommencerBok = false;

		tabNomJ = new String[2];

		tabClient = new DatagramPacket[2];

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

	}

	public void lancerServeur() throws IOException
	{
		PartieConsole partie;
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

				//Joueur j = new Joueur( couleurJ, codeCouleurJ);
				tabNomJ[nombreJoueur] = msgRecu.substring(6);
				tabClient[nombreJoueur] = msg;
				nombreJoueur ++;

			}
			if ( nombreJoueur == 2 )
			{
				if ( !partieCommencerBok )
				{
					partieCommencerBok = true;
					String partieCommence = "La partie va commencer";

					for (int i = 0; i < nombreJoueur; i++)
					{
						envoyerMsg(partieCommence, tabClient[i] );
					}

					int nbLigne = (int) (Math.random() * 9) + 4;
					int nbCol   = (int) (Math.random() * 9) + 4;

					partie = new PartieConsole(nbLigne, nbCol, nombreJoueur);
					PartieConsole.joueurs[0].setNom(tabNomJ[0]);
					PartieConsole.joueurs[1].setNom(tabNomJ[1]);

					String map = "MAP=\n";
					for (int i = 0; i < nbLigne; i++)
					{
						for (int j = 0; j < nbCol; j++)
						{
							map += partie.getContainer(i, j).getValeur();
							map += ":";
						}
						map += "|\n";
					}

					for (int i = 0; i < nombreJoueur; i++)
					{
						envoyerMsg(map, tabClient[i] );
					}

				}

			}

		}
	}

	public void envoyerMsg(String msg, DatagramPacket dpReceveurMessage) throws IOException
	{
		DatagramPacket reponse = new DatagramPacket(msg.getBytes(), msg.length(), dpReceveurMessage.getAddress(), dpReceveurMessage.getPort());
		ds.send(reponse);
	}

	public static void main (String args[]) throws Exception
	{
		new Serveur().lancerServeur();
	}

}