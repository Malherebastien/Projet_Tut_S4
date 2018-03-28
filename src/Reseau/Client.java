package Reseau;

import Controler.PartieConsole;
import Model.Coin;
import Model.Container;
import Model.Joueur;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;

public class Client
{
	private DatagramSocket ds;

    private static Joueur[] joueurs;

    private Joueur joueurActif;

    private int indJoueur;

    private static final String[] COULEURS = new String[] { "Rouge;\033[31m", "Vert;\033[32m" };

	private Container[][] tabContainer;
	private Coin[][]      tabCoin;

	private int nbLig;
	private int nbCol;

	public Client() throws IOException
	{
		ds = new DatagramSocket(); // connecte le client

        initClient();
		lancerClient();
	}

	private void initClient()
    {
        try {
            Scanner scNom = new Scanner(System.in);
            String msgNom = "Name :" + scNom.nextLine();

            envoyerMsg(msgNom);
            System.out.println(recevoirMsg());

            String numJ = recevoirMsg();

            indJoueur = Integer.parseInt(numJ)-1;

            String couleur = recevoirMsg();

            System.out.println( "Vous etes le Joueur " + numJ + " (" + couleur + ") " + " attente suite ..." );

            System.out.println( recevoirMsg() ); // partie commencer
            System.out.println( recevoirMsg() ); // map
        } catch (IOException ioe) { ioe.printStackTrace(); }
    }

	public void lancerClient() throws IOException
	{
		while ( true )
		{
			System.out.println( recevoirMsg() ); // map
			System.out.println("envoi au serveur chaine :");
			Scanner sc = new Scanner(System.in);
			String message = sc.nextLine();

			envoyerMsg(message);
		}
	}

	private void envoyerMsg(String msg) throws IOException
	{
		// crée un envoyeur qui envoie les bits, taill du msg à l'adresse internet "localhost" sur le port 2009
		DatagramPacket reponse = new DatagramPacket(msg.getBytes(), msg.length(), InetAddress.getByName("localhost"), 2009);
		//envoie le msg
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

	private void initGrille(String map)
	{
	    joueurs = new Joueur[] { new Joueur(COULEURS[0].split(";")[0], COULEURS[0].split(";")[1]),
	                             new Joueur(COULEURS[1].split(";")[1], COULEURS[1].split(";")[1]) };

	    joueurActif = joueurs[0];

	    nbLig = map.split("|").length;
	    nbCol = map.split("|")[0].split(":").length;

        tabCoin = new Coin[nbLig+1][nbCol+1];

        for (int i = 0; i < nbLig+1; i++)
            for (int j = 0; j < nbCol+1; j++)
                tabCoin[i][j] = new Coin();

		tabContainer = new Container[nbLig][nbCol];

		for (int i = 0 ; i < nbLig ; i++)
        {
            String ligne = map.split("|")[i];

            for (int j = 0 ; j < nbCol ; j++)
                tabContainer[i][j] = initCoinsContainer(new Container(Integer.parseInt(ligne.split(":")[j])), i, j);
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
            for (int i = 0; i < PartieConsole.joueurs.length ; i++)
                PartieConsole.joueurs[i].setScore(0);

            // Parcours des containers pour mettre les points aux joueurs
            for (int i = 0; i < nbLig; i++)
                for (int j = 0; j < nbCol; j++)
                    this.tabContainer[i][j].setScoreJoueur();
        } else
        {
            System.out.println("Mouvement impossible, pénalité d'un TwistLock");
            if (this.joueurActif.getNbTwistLock() != 0)
                this.joueurActif.setNbTwistLock(this.joueurActif.getNbTwistLock() - 1);
        }
	}

	private String afficherGrille()
	{
		String sRet = "";

		sRet += "    ";

		char col = 'A';
		while(col <= 64+nbCol) sRet += "  " + col++ + "  ";

		sRet +="\n";

		for (int i = 0; i < nbLig; i++)
		{
			sRet += "   " + tabContainer[i][0].getCoins()[0];

			for (int j = 0 ; j < nbCol ; j++)
				sRet += tabContainer[i][j].toString1();

			sRet += "\n";


			sRet += String.format("%2s",(1 + i)) + " |";

			for (int j = 0 ; j < nbCol ; j++)
				sRet += tabContainer[i][j].toString2();

			sRet += "\n";

			if (i == nbLig-1)
			{
				sRet += "   " + tabContainer[i][3].getCoins()[2];

				for (int j = 0 ; j < nbCol ; j++)
					sRet += tabContainer[i][j].toString3();

				sRet += "\n";
			}
		}

		return sRet;
	}

    public static void main (String args[]) throws Exception
    {
        new Client();
    }
}