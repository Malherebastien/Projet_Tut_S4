package Reseau;

import Model.Joueur;

import java.net.*;
import java.util.Scanner;

public class Client
{

	public static void main (String args[]) throws Exception
	{

		DatagramSocket ds = new DatagramSocket();


		Scanner scNom = new Scanner(System.in);
		String msgNom = "Name :" + scNom.nextLine();

		DatagramPacket envoiNom = new DatagramPacket(msgNom.getBytes(), msgNom.length(), InetAddress.getByName("localhost"), 2009);
		ds.send(envoiNom);
		DatagramPacket dpNom = new DatagramPacket(new byte[512], 512);
		ds.receive(dpNom);
		System.out.println(new String(dpNom.getData()));


		DatagramPacket dpCpt = new DatagramPacket(new byte[512], 512);
		ds.receive(dpCpt);
		DatagramPacket dpCouleur = new DatagramPacket(new byte[512], 512);
		ds.receive(dpCouleur);

		System.out.println( "Vous etes le Joueur " + new String(dpCpt.getData()) + " (" + new String (dpCouleur.getData()) + ") " + " attente suite ..." );

		DatagramPacket dpPartieCommence = new DatagramPacket(new byte[512], 512);
		ds.receive(dpPartieCommence);
		System.out.println(new String(dpPartieCommence.getData()));

		DatagramPacket dpMap = new DatagramPacket(new byte[512], 512);
		ds.receive(dpMap);
		System.out.println(new String(dpMap.getData()));

		while ( true )
		{
			Scanner sc = new Scanner(System.in);
			String message = sc.nextLine();

			DatagramPacket envoi = new DatagramPacket(message.getBytes(), message.length(), InetAddress.getByName("localhost"), 2009);
			ds.send(envoi);

			/*DatagramPacket msg = new DatagramPacket(new byte[512], 512);
			ds.receive(msg);
			System.out.println(new String(msg.getData()));*/

		}
		//ds.close();
	}
}