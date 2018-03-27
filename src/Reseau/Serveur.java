package Reseau;

import Controler.PartieConsole;

import java.net.*;

public class Serveur
{

	public static void main (String args[]) throws Exception
	{
		DatagramSocket ds = new DatagramSocket(2009);
		int nombreJoueur =0;
		boolean partieCommencerBok = false;
		Integer[] tabPort = new Integer[2];
		InetAddress[] tabAdress = new InetAddress[2];
		String [] tabNomJ   = new String[2];

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

				DatagramPacket reponse = new DatagramPacket(texteReponse.getBytes(), texteReponse.length(), msg.getAddress(), msg.getPort());
				ds.send(reponse);

				DatagramPacket dpNumJoueur = new DatagramPacket(numJ.getBytes(), numJ.length(), msg.getAddress(), msg.getPort());
				ds.send(dpNumJoueur);

				DatagramPacket dpCouleurJoueur = new DatagramPacket(couleurJ.getBytes(), couleurJ.length(), msg.getAddress(), msg.getPort());
				ds.send(dpCouleurJoueur);

				//Joueur j = new Joueur( couleurJ, codeCouleurJ);
				tabNomJ[nombreJoueur] = msgRecu.substring(6);
				tabPort[nombreJoueur] = msg.getPort();
				tabAdress[nombreJoueur] = msg.getAddress();
				nombreJoueur ++;

			}
			if ( nombreJoueur == 2 && !partieCommencerBok)
			{
				partieCommencerBok = true;
				String partieCommence = "La partie va commencer";

				for ( int i = 0; i <nombreJoueur; i++)
				{
					DatagramPacket dpMessPartieCommence = new DatagramPacket(partieCommence.getBytes(), partieCommence.length(), tabAdress[i], tabPort[i]);
					ds.send(dpMessPartieCommence);
				}
				int nbLigne = (int) (Math.random()*9)+4;
				int nbCol   = (int) (Math.random()*9)+4;

				PartieConsole partie = new PartieConsole(nbLigne, nbCol, nombreJoueur);
				PartieConsole.joueurs[0].setNom(tabNomJ[0]);
				PartieConsole.joueurs[1].setNom(tabNomJ[1]);

				String map = "MAP=";
				for ( int i = 0; i < nbLigne; i++)
				{
					for ( int j = 0; j < nbCol; j++)
					{
						map += partie.getContainer(i,j).getValeur();
						map +=":";
					}
					map += "|";
				}

				for ( int i = 0; i <nombreJoueur; i++)
				{
					DatagramPacket dpMap = new DatagramPacket(map.getBytes(), map.length(), tabAdress[i], tabPort[i]);
					ds.send(dpMap);
				}



			}
			//DatagramPacket reponse = new DatagramPacket(texteReponse.getBytes(), texteReponse.length(), msg.getAddress(), msg.getPort());
			//ds.send(reponse);
		}
		//ds.close();
	}
}