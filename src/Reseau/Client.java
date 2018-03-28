package Reseau;

import Model.Container;
import Model.Joueur;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class Client
{
	private DatagramSocket ds;

	private Container[][] tabContainer;

	public Client() throws IOException
	{
		ds = new DatagramSocket(); // connecte le client

		Scanner scNom = new Scanner(System.in);
		String msgNom = "Name :" + scNom.nextLine();

		envoyerMsg(msgNom);
		System.out.println(recevoirMsg());

		String cpt = recevoirMsg();

		String couleur = recevoirMsg();

		System.out.println( "Vous etes le Joueur " + cpt + " (" + couleur + ") " + " attente suite ..." );

		System.out.println( recevoirMsg() ); // partie commencer
		System.out.println( recevoirMsg() ); // map

		lancerClient();

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

	public static void main (String args[]) throws Exception
	{
		new Client();
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

	public void initGrille(String map)
	{
		tabContainer = new Container[map.split("|").length][map.split("|")[0].split(":").length];

		for (String ligne : map.split("|"))
		{
			for (String col : ligne.split(":"))
			{

			}
		}
	}

	public void majGrille()
	{

	}

	public String afficherGrille()
	{
		String sRet = "";

		sRet += "    ";

		char col = 'A';
		while(col <= 64+nbCol) sRet += "  " + col++ + "  ";

		sRet +="\n";

		for (int i = 0; i < nbLig; i++)
		{
			sRet += "   " + getContainer(i,0).getCoins()[0];

			for (int j = 0 ; j < nbCol ; j++)
				sRet += getContainer(i,j).toString1();

			sRet += "\n";


			sRet += String.format("%2s",(1 + i)) + " |";

			for (int j = 0 ; j < nbCol ; j++)
				sRet += getContainer(i,j).toString2();

			sRet += "\n";

			if (i == nbLig-1)
			{
				sRet += "   " + getContainer(i,3).getCoins()[2];

				for (int j = 0 ; j < nbCol ; j++)
					sRet += getContainer(i,j).toString3();

				sRet += "\n";
			}
		}

		return sRet;
	}


}