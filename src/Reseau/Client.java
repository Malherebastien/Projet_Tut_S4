package Reseau;

import Model.Joueur;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class Client
{
	private DatagramSocket ds;

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

		System.out.println( recevoirMsg() );
		System.out.println( recevoirMsg() );

		lancerClient();

	}

	public void lancerClient() throws IOException
	{
		while ( true )
		{
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
}