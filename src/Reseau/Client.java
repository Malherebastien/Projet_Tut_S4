package Reseau;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;


public class Client
{
	private DatagramSocket ds;

    private int indJoueur;
    private String nom;

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
            nom = " Name : " + scNom.nextLine();

            envoyerMsg(nom);

            String msgNumCoul = recevoirMsg();
			System.out.println(msgNumCoul);

            indJoueur = Integer.parseInt( msgNumCoul.split(";")[1] );

            System.out.println(indJoueur + "-Bonjour " + nom);
            System.out.println("Vous etes le Joueur " + (indJoueur+1) + " (" + msgNumCoul.split(";")[0] + "), " + " attente suite ..." );
        } catch (IOException ioe) { ioe.printStackTrace(); }
    }

	public void lancerClient() throws IOException
	{
		while (true)
		{
            String signal = recevoirMsg();

			System.out.println(signal);

			if ( signal.substring(0,2).equals("10"))
			{
				Scanner sc = new Scanner(System.in);
				envoyerMsg(sc.nextLine());
			}

			if ( signal.substring(0,2).equals("88") )
			{
				String msg = recevoirMsg();
				System.out.println(msg);
				System.exit(0);
			}
			System.out.println();
		}
	}

	private void envoyerMsg(String msg) throws IOException
	{
		// crée un envoyeur qui envoie les bits, taill du msg à l'adresse internet "localhost" sur le port 2009
		DatagramPacket reponse = new DatagramPacket(msg.getBytes(), msg.length(), InetAddress.getByName("127.0.0.1"), 2009);
		//envoie le msg
		ds.send(reponse);
	}

	private String recevoirMsg() throws IOException
	{
		//crée un receveur qui va recevoir la taille et la taille du msg
		DatagramPacket dpMsg = new DatagramPacket(new byte[1024], 1024);
		//recois le msg
		ds.receive(dpMsg);
		return new String(dpMsg.getData());
	}

    public static void main (String args[]) throws Exception
    {
        new Client();
    }
}