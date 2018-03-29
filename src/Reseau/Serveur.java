package Reseau;

import Model.Coin;
import Model.Container;
import Model.Joueur;

import java.io.IOException;
import java.net.*;

public class Serveur
{

	private static final String[] COULEURS = new String[] { "Rouge;\033[31m", "Vert;\033[32m" };

	private Container[][] tabContainer;
	private Coin[][]      tabCoin;

	public static Joueur[] joueurs;
	private DatagramPacket [] tabClient;
	private boolean partieCommencerBok;
	private int nombreJoueur;
	private String [] tabNomJ;
	private DatagramSocket ds;
	private int nbCol, nbLigne;
	private String signal;
	private Joueur joueurActif;

	public Serveur ()
	{
		nombreJoueur = 0;
		partieCommencerBok = false;

		tabNomJ = new String[2];
		tabClient = new DatagramPacket[2];

		lancerServeur();
	}

	public void lancerServeur()
	{
		try {
			//crée une Socket au port 2009
			ds = new DatagramSocket(2009);

			while ( true )
			{
				if ( !partieCommencerBok )
				{
					DatagramPacket msg = new DatagramPacket(new byte[512], 512);
					ds.receive(msg);

					String msgRecu = new String(msg.getData());
					System.out.println("message Recu : " + msgRecu);
					signal = "";

					if (msgRecu.contains("Name :") && nombreJoueur < 2) {
						String couleurJ = COULEURS[nombreJoueur].split(";")[0];

						String msgInfoJoueur = couleurJ + ";" + nombreJoueur + ";";

						envoyerMsg(msgInfoJoueur, msg);

						tabNomJ[nombreJoueur] = msgRecu.substring(6);
						tabClient[nombreJoueur] = msg;
						nombreJoueur++;

					}

				}
				if ( nombreJoueur == 2 )
				{
					if ( !partieCommencerBok )
					{
						System.out.println("debut partie");
						partieCommencerBok = true;

						initPartie();
						System.out.println("init");
						String map = preparerMap();
						System.out.println("creation map");
						System.out.println(map);
						initGrille(map);

						signal01(map);
						System.out.println("envoie map");
					}

					if ( partieCommencerBok )
					{
						System.out.println("debut jeu");
						boolean joueurPeutJouer = true;

						//test si fin du jeu
						if ( signal88() ) break;

						// test si le joueur courant a des TwistLock, sinon passe son tour
						joueurPeutJouer = signal50();

						while ( joueurPeutJouer )
						{
							//fait jouer le joueur courant
							signal10();

							String coordonnees = recevoirMsg(); System.out.println(coordonnees);
							//test erreur coord
							if (verifCoord(coordonnees) == 1  )
							{
								signal20(coordonnees);
								majGrille(coordonnees);
								joueurActif.setNbTwistLock( joueurActif.getNbTwistLock() -1);
								break;
							}
							if (verifCoord(coordonnees) == 0  )
							{
								signalErreur();
								joueurActif.setNbTwistLock( joueurActif.getNbTwistLock() -1);
							}
							if (verifCoord(coordonnees) == -1 )
							{
								continue;
							}
						}
						System.out.println("Joueur TwistLock :" + joueurActif.getNom() + "   " + joueurActif.getNbTwistLock());
						changeJoueurActif();

					}

				}
				System.out.println("new tour");

			}
			System.out.println("Fin Jeu");
			//envoyerScore();
			System.out.println("Score " + joueurs[0].getNom() + "\t" + joueurs[0].getScore());
			System.out.println("Score " + joueurs[1].getNom() + "\t" + joueurs[1].getScore());
		} catch (IOException ioe) { ioe.printStackTrace(); }
	}

	private void majGrille(String valPos)
	{
		int coin = Integer.parseInt(valPos.substring(valPos.length()-1, valPos.length()));

		String coord = valPos.substring(0,valPos.length()-1);

		int lig = Integer.parseInt(valPos.substring(0, coord.length()-1));
		int col = Character.toUpperCase(coord.charAt(coord.length()-1))-65;

		if (!tabContainer[lig-1][col].getCoins()[coin-1].isOccupe())
		{
			this.tabContainer[lig-1][col].getCoins()[coin-1].setOccupant(joueurActif);
			// -64 pour les lettres et -1 pour le tableau

			// Parcours des joueurs pour remettres leurs points à 0 avant de fair eune nouvelle affectation
			for (int i = 0; i < joueurs.length ; i++)
				joueurs[i].setScore(0);

			// Parcours des containers pour mettre les points aux joueurs
			for (int i = 0; i < nbLigne ; i++)
				for (int j = 0; j < nbCol ; j++)
					this.tabContainer[i][j].setScoreJoueurServeur();
		}
		else if (this.joueurActif.getNbTwistLock() != 0) this.joueurActif.setNbTwistLock(this.joueurActif.getNbTwistLock() - 1);
	}

	private void initGrille(String map)
	{
		joueurs = new Joueur[] { new Joueur(COULEURS[0].split(";")[0], COULEURS[0].split(";")[1]),
				                 new Joueur(COULEURS[1].split(";")[0], COULEURS[1].split(";")[1]) };

		joueurs[0].setNom(tabNomJ[0]);
		joueurs[1].setNom(tabNomJ[1]);

		joueurActif = joueurs[0];
		System.out.println("Joueur actif:" + joueurActif.getCouleur());
		System.out.println("Joueur actif:" + joueurActif.getCouleur());

		tabCoin = new Coin[nbLigne+1][nbCol+1];

		for (int i = 0; i < nbLigne+1; i++)
			for (int j = 0; j < nbCol+1; j++)
				tabCoin[i][j] = new Coin();

		tabContainer = new Container[nbLigne][nbCol];

		for (int i = 0 ; i < nbLigne ; i++)
		{
			String ligne = map.split("\\|")[i];

			for (int j = 0 ; j < nbCol ; j++)
				tabContainer[i][j] = initCoinsContainer(new Container(new Integer(ligne.split(":")[j])), i, j);
		}
	}

	private Container initCoinsContainer(Container c, int lig, int col)
	{
		c.addCoin(this.tabCoin[lig][col]); //Haut gauche
		c.addCoin(this.tabCoin[lig][col+1]); //Haut droite
		c.addCoin(this.tabCoin[lig+1][col+1]); //Bas droite
		c.addCoin(this.tabCoin[lig+1][col]); // Bas gauche

		return c;
	}

	/*private void envoyerScore()
	{
		try{
			for ( int i = 0; i < joueurs.length; i++)
			{
				String msgScore = "" + joueurs[i].getScore();
				envoyerMsg(msgScore, tabClient[i]);
				if ( i == 0) msgScore = "" + joueurs[1].getScore();
				else         msgScore = "" + joueurs[0].getScore();
				envoyerMsg(msgScore, tabClient[i]);

			}
		}catch (IOException ioe){}
	}*/

	private void signal01(String map)
	{
		try{
			for (int i = 0; i < joueurs.length; i++)
			{
				signal = "01 - La partie va commencer\nMap = " + map;
				envoyerMsg(signal, tabClient[i] );
			}
		}catch (IOException ioe){ System.out.println("Erreur envoi Signal 01"); }
	}

	private boolean signal88()
	{
		try {
			if (joueurs[0].getNbTwistLock() <= 0 && joueurs[1].getNbTwistLock() <= 0)
			{
				if ( joueurs[0].getScore() > joueurs[1].getScore())
				{
					signal = "88 - Partie Terminée, Vous avez gagne " + joueurs[0].getScore() + " - " + joueurs[1].getScore() + "!" ;
					envoyerMsg(signal, tabClient[0]);
					signal = "88 - Partie Terminée, Vous avez perdu " + joueurs[1].getScore() + " - " + joueurs[0].getScore() + "!" ;
					envoyerMsg(signal, tabClient[1]);
				}
				else
				{
					signal = "88 - Partie Terminée, Vous avez perdu " + joueurs[0].getScore() + " - " + joueurs[1].getScore() + "!" ;
					envoyerMsg(signal, tabClient[0]);
					signal = "88 - Partie Terminée, Vous avez gagne " + joueurs[1].getScore() + " - " + joueurs[0].getScore() + "!" ;
					envoyerMsg(signal, tabClient[1]);
				}

				String carte = afficherGrille();
				envoyerMsg(carte , tabClient[0]);
				envoyerMsg(carte , tabClient[1]);

				return true;
			}
		}catch (IOException ioe){ System.out.println("Erreur envoi Signal 88");}
		return false;
	}

	private boolean signal50()
	{
		boolean joueurPeutJouer = true;
		try {
			for ( int i = 0; i < joueurs.length; i++ )
			{
				if (getJoueurActif() == joueurs[i] && joueurs[i].getNbTwistLock() <= 0)
				{
					signal = "50 - Vous ne pouvez plus jouer";
					envoyerMsg(signal, tabClient[i]);
					joueurPeutJouer = false;
				}
			}
		}catch (IOException ioe){ System.out.println("Erreur envoi Signal 50");}
		return joueurPeutJouer;
	}

	private void signal20(String coord)
	{
		try {
			// recois le joueur actif, et le joueur actif recois le message suivant
			for ( int i = 0; i < joueurs.length; i++ )
			{
				if ( getJoueurActif() != joueurs[i]) { signal = "20 - Coup adversaire : " + coord; envoyerMsg(signal, tabClient[i]); }
			}
		}catch (IOException ioe){ System.out.println("Erreur envoi Signal 20"); }

	}

	private void signal10()
	{
		try {
			for ( int i = 0; i < joueurs.length; i++)
			{
				if (getJoueurActif() == joueurs[i])
				{
					// je met un \n en dehors de la chaine car c'est pour eviter de probleme pour client
					signal = "10 - A vous de jouer (" + joueurActif.getCouleur() + ") : \n" + afficherGrille() ; //a vous de jouer
					envoyerMsg(signal, tabClient[i]);
				}
			}

		}catch (IOException ioe){ System.out.println("Erreur envoi Signal 10");}
	}

	private void signalErreur()
	{
		try {

			if (getJoueurActif() == joueurs[0])
			{
				signal = "21 - Coup illegal";
				envoyerMsg(signal, tabClient[0]);
				signal = "22 - Coup adversaire illegal";
				envoyerMsg(signal, tabClient[1]);
			}
			else
			{
				signal = "21 - Coup illegal";
				envoyerMsg(signal, tabClient[1]);
				signal = "22 - Coup adversaire illegal";
				envoyerMsg(signal, tabClient[0]);
			}


		}catch (IOException ioe) { System.out.println("Erreur Signal Erreur"); }

	}
	private String afficherGrille()
	{
		String sRet = "";

		sRet += "    ";

		char col = 'A';
		while(col <= 64+nbCol) sRet += "  " + col++ + "  ";

		sRet +="\n";

		for (int i = 0; i < nbLigne; i++)
		{
			sRet += "   " + tabContainer[i][0].getCoins()[0];

			for (int j = 0 ; j < nbCol ; j++)
				sRet += tabContainer[i][j].toString1();

			sRet += "\n";


			sRet += String.format("%2s",(1 + i)) + " |";

			for (int j = 0 ; j < nbCol ; j++)
				sRet += tabContainer[i][j].toString2();

			sRet += "\n";

			if (i == nbLigne-1)
			{
				sRet += "   " + tabContainer[i][3].getCoins()[2];

				for (int j = 0 ; j < nbCol ; j++)
					sRet += tabContainer[i][j].toString3();

				sRet += "\n";
			}
		}

		return sRet;
	}

	public Joueur getJoueurActif() { return joueurActif;}

	public void initPartie()
	{
		nbLigne = (int) (Math.random() * 5) + 4;
		nbCol   = (int) (Math.random() * 5) + 4;

	}

	public void changeJoueurActif()
	{
		if      ( joueurActif == joueurs[0] ) joueurActif = joueurs[1];
		else if ( joueurActif == joueurs[1] ) joueurActif = joueurs[0];

	}

	public String preparerMap ()
	{
		String map = "";
		for ( int i = 0; i < nbLigne; i ++)
		{
			for ( int j = 0; j < nbCol; j++)
			{
				map += (int) (Math.random()*54)+4;
				if ( j < nbCol -1 ) map += ":";
			}

			if (i < nbLigne-1) map += "|";
		}
		return map;
	}

	/**
	 * Permet de savoir si les coordonnées sont valides
	 * @param coord
	 * @return -1 si coordonnées non valides, 0 si coordonnées valides mais emplacement déjà occupé, 1 si out est bon
	 */
	private int verifCoord(String coord)
	{
		try
		{
			if (coord.length() == 3) {
				int lig  = Integer.parseInt(coord.charAt(0)+"");
				int col  = Character.toUpperCase(coord.charAt(1)) - 65;
				int coin = Integer.parseInt(coord.charAt(2)+"");

				if (lig <= nbLigne && lig > 0 && col <= nbCol && col >= 0 && coin >= 1 && coin <= 4)
				{
					// coin -1 a cause de l'indice qui commence a 0
					if (!tabContainer[lig-1][col].getCoins()[coin-1].isOccupe())
					{
						//System.out.println (" Valeur : " + tabContainer[lig-1][col].getValeur());
						if ( joueurActif == joueurs[0] ) { joueurs[0].setScore( joueurs[0].getScore() + tabContainer[lig -1][col].getValeur() ); System.out.println("scoooore " + joueurs[0].getScore()); }
						if ( joueurActif == joueurs[1] ) { joueurs[1].setScore( joueurs[1].getScore() + tabContainer[lig -1][col].getValeur() ); System.out.println("scoooore " + joueurs[1].getScore());}
						//System.out.println(joueurActif.getScore());
						return 1;
					}
					else return 0;
				}
			}
		}catch (Exception e){ System.out.println("Erreur parsing"); }

		System.out.println("FAUX");

		return -1;
	}

	private void envoyerMsg(String msg, DatagramPacket dpReceveurMessage) throws IOException
	{
		DatagramPacket reponse = new DatagramPacket(msg.getBytes(), msg.length(), dpReceveurMessage.getAddress(), dpReceveurMessage.getPort());
		System.out.println(new String(reponse.getData()));
		ds.send(reponse);
	}

	private String recevoirMsg() throws IOException
	{
		//crée un receveur qui va recevoir la taille et la taille du msg
		DatagramPacket dpMsg = new DatagramPacket(new byte[512], 512);
		//recois le msg
		ds.receive(dpMsg);
		return new String(dpMsg.getData()).trim();
	}


	public static void main (String args[]) throws Exception
	{
		new Serveur();
	}

}