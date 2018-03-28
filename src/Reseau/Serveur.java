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
				String signal;

				if ( msgRecu.contains("Name :") && nombreJoueur < 2)
				{
					String couleurJ = tabCouleur[nombreJoueur];
					String codeCouleurJ = tabCodeCouleur[nombreJoueur];

					signal = "Bonjour " + msgRecu.substring(6);
					String numJ = "" + (nombreJoueur+1);
					envoyerMsg(signal, msg);

					String msgInfoJoueur = couleurJ + ";" + numJ;

					envoyerMsg(msgInfoJoueur, msg);
					//envoyerMsg(couleurJ, msg);

					tabNomJ[nombreJoueur] = msgRecu.substring(6);
					tabClient[nombreJoueur] = msg;
					nombreJoueur ++;

				}
				if ( nombreJoueur == 2 )
				{
					if ( !partieCommencerBok )
					{
						partieCommencerBok = true;

						nbLigne = (int) (Math.random() * 5) + 4;
						nbCol   = (int) (Math.random() * 5) + 4;

						partie = new PartieConsole(nbLigne, nbCol, nombreJoueur);
						PartieConsole.joueurs[0].setNom(tabNomJ[0]);
						PartieConsole.joueurs[1].setNom(tabNomJ[1]);
						tabJoueur[0] = PartieConsole.joueurs[0];
						tabJoueur[1] = PartieConsole.joueurs[1];

						/*for (int i = 0; i < nombreJoueur; i++)
						{
							envoyerMsg(partie.afficherTableauContainer(nbCol, nbLigne), tabClient[i]); //affiche tab
						}*/

						String map = "";
						for ( int i = 0; i < nbLigne; i ++)
						{
							for ( int j = 0; j < nbCol; j++)
							{
								map += partie.getContainer(i,j);
								map += ":";
							}
							map += "|";
						}
						signal = "01";
						for (int i = 0; i < nombreJoueur; i++)
						{
							envoyerMsg(signal, tabClient[i] );
							envoyerMsg(map, tabClient[i]);
						}

					}
					else
					{

						//fin du jeu
						signal = "88";
						if ( tabJoueur[0].getNbTwistLock() == 0 && tabJoueur[1].getNbTwistLock() == 0)
						{
							envoyerMsg(signal, tabClient[0]);
							envoyerMsg(signal, tabClient[1]);
						}

						// si le joueur courant n'a plus de TwistLock alors on passe son tour
						signal = "50";
						if ( partie.getJoueurActif() == tabJoueur[0] && tabJoueur[0].getNbTwistLock() == 0)
						{
							envoyerMsg(signal, tabClient[0] );
							continue;
						}
						if ( partie.getJoueurActif() == tabJoueur[1] && tabJoueur[1].getNbTwistLock() == 0)
						{
							envoyerMsg(signal, tabClient[1] );
							continue;
						}

						while ( true )
						{
							signal = "20"; //attente
							// recois le joueur actif, et le joueur actif recoi le message suivant
							if (partie.getJoueurActif() != tabJoueur[0])
							{
								envoyerMsg(signal, tabClient[0]);
							} else
							{
								envoyerMsg(signal, tabClient[1]);
							}


							signal = "10"; //a vous de jouer
							if (partie.getJoueurActif() == tabJoueur[0])
							{
								envoyerMsg(signal, tabClient[0]);
							}
							else
							{
								envoyerMsg(signal, tabClient[1]);
							}

							String coordonnees = recevoirMsg();
							if (!verifCoord(coordonnees)) //test erreur coord
							{
								if (partie.getJoueurActif() == tabJoueur[0])
								{
									signal = "21";
									envoyerMsg(signal, tabClient[0]);
								} else
								{
									signal = "22";
									envoyerMsg(signal, tabClient[1]);
								}

							}
							else
							{
								//envoie resultat du joueur courant
								signal = coordonnees;
								if (partie.getJoueurActif() != tabJoueur[0])
								{
									envoyerMsg(signal, tabClient[0]);
								} else
								{
									envoyerMsg(signal, tabClient[1]);
								}
								break;
							}

						}

					}

				}

			}
		} catch (IOException ioe) { ioe.printStackTrace(); }
	}



	private boolean verifCoord(String coord)
	{
		try
		{
			if (coord.length() == 3) {
				int lig = Integer.parseInt(coord.substring(0, 1));
				int col = Character.toUpperCase(coord.charAt(1)) - 65;
				int coin = Integer.parseInt(coord.substring(2, coord.length() - 1));

				if (lig <= nbLigne && lig >= 0 && col <= nbCol && col >= 0) return true;
				if (coin <= 0 || coin >= 5) return true;
			}
		}catch (Exception e){}

		return false;
	}

	private void envoyerMsg(String msg, DatagramPacket dpReceveurMessage) throws IOException
	{
		DatagramPacket reponse = new DatagramPacket(msg.getBytes(), msg.length(), dpReceveurMessage.getAddress(), dpReceveurMessage.getPort());
		ds.send(reponse);
	}

	private String recevoirMsg() throws IOException
	{
		//crée un receveur qui va recevoir la taille et la taille du msg
		DatagramPacket dpMsg = new DatagramPacket(new byte[512], 512);
		//recois le msg
		ds.receive(dpMsg);
		return new String(dpMsg.getData());
	}


	public static void main (String args[]) throws Exception
	{
		new Serveur();
	}

}