package Controler;

import Model.Coin;
import Model.Container;
import Model.Joueur;

import java.util.Scanner;

public class PartieConsole
{
    public static Joueur[] joueurs;

    private static final String[] COULEURS = new String[] { "Rouge;\033[31m", "Vert;\033[32m", "Bleu;\033[34m", "Orange;\033[33m" };

    private Container[][] tabContainer;
    private Coin[][] tabCoin;

    private int nbLig; //Random
    private int nbCol; //Random

    private Joueur joueurActif;
    private int nbJoueurs;

    public PartieConsole(int nbLig, int nbCol, int nbJoueurs)
    {
        this.nbLig = nbLig;
        this.nbCol = nbCol;

        if (nbJoueurs < 2) nbJoueurs = 2;
        if (nbJoueurs > 4) nbJoueurs = 4;

        this.nbJoueurs = nbJoueurs;
        PartieConsole.joueurs = new Joueur[nbJoueurs];

        for (int i = 0 ; i < nbJoueurs ; i++)
            PartieConsole.joueurs[i] = new Joueur(PartieConsole.COULEURS[i].split(";")[0], PartieConsole.COULEURS[i].split(";")[1]);

        this.joueurActif = PartieConsole.joueurs[0];

        tabContainer = new Container[nbLig][nbCol];
        for (int i = 0; i < nbLig; i++)
            for (int j = 0; j < nbCol; j++)
                tabContainer[i][j] = new Container((int) (Math.random()*49 + 5));
        //Remplissage du tableau de containers;

        tabCoin = new Coin[nbLig+1][nbCol+1]; //Il y a 5*5 coins sur un tableau de 4*4 container.
        for (int i = 0; i < nbLig+1; i++)
            for (int j = 0; j < nbCol+1; j++)
                tabCoin[i][j] = new Coin();

        for (int i = 0; i < nbLig; i++)
        {
            for (int j = 0; j < nbCol; j++)
            {
                this.tabContainer[i][j].addCoin(this.tabCoin[i][j]); //Haut gauche
                this.tabContainer[i][j].addCoin(this.tabCoin[i][j+1]); //Haut droite
                this.tabContainer[i][j].addCoin(this.tabCoin[i+1][j+1]); //Bas droite
                this.tabContainer[i][j].addCoin(this.tabCoin[i+1][j]); // Bas gauche
            }
        }
        //Remplissage du tableau de Model.Coin de chaque Model.Container (moins compliqué que ce que je pensais)

        System.out.println("==============================================");
        System.out.println("             Début de partie !");
        System.out.println("==============================================");

        Scanner sc = new Scanner(System.in);

        while (!this.estFinDePartie())
        {
            System.out.println("C'est le tour du joueur " + joueurActif.getCodeCouleur() + joueurActif.getCouleur() +
                    getBase() + "!\nIl lui reste " + this.joueurActif.getNbTwistLock() + " TwistLocks !");
            System.out.println("Son score est de : " + joueurActif.getScore());

            if (this.joueurActif.getNbTwistLock() != 0)
            {
                System.out.println(afficherTableauContainer());

                String choix;
                do
                {
                    System.out.println("Choisissez ligne + colonne (genre 9B)");
                    choix = sc.nextLine();
                } while (choix.length() != 2 || !estSaisieValide(choix));

                String coin;
                do
                {
                    System.out.println("Choisissez l'un des quatres coins (1 à 4)");
                    coin = sc.nextLine();
                } while (coin.length() != 1 || !estSaisieValide(coin));


                int lig = choix.charAt(0) - '0'; //Ok, ça marche pour transformer un char en int :o

                if (!tabContainer[lig - 1][(int) Character.toUpperCase(choix.charAt(1)) - 65].getCoins()[Integer.parseInt(coin) - 1].isOccupe())
                {
                    this.tabContainer[lig - 1][(int) Character.toUpperCase(choix.charAt(1)) - 65].getCoins()[Integer.parseInt(coin) - 1].setOccupant(joueurActif);
                    // -64 pour les lettres et -1 pour le tableau

                    for (int i = 0; i < nbLig; i++)
                        for (int j = 0; j < nbCol; j++)
                            this.tabContainer[i][j].setScoreJoueur();
                } else
                {
                    System.out.println("Mouvement impossible, pénalité d'un TwistLock");
                    if (this.joueurActif.getNbTwistLock() != 0)
                        this.joueurActif.setNbTwistLock(this.joueurActif.getNbTwistLock() - 1);
                }


                //Gestion de tours
                this.joueurActif.setNbTwistLock(joueurActif.getNbTwistLock() - 1);

            }
            else
                System.out.println("Plus de TwistLocks ! Joueur suivant.");

            for (int i = 0 ; i < this.nbJoueurs ; i++)
                if (this.joueurActif == PartieConsole.joueurs[i])
                {
                    if (i == this.nbJoueurs-1) this.joueurActif = PartieConsole.joueurs[0];
                    else                       this.joueurActif = PartieConsole.joueurs[i+1];

                    break;
                }
        }
    }

    private boolean estSaisieValide(String saisie)
    {
        try {
            if (saisie.length() == 2)
            {
                int lig = Integer.parseInt(saisie.charAt(0)+"");
                int col = Character.toUpperCase(saisie.charAt(1))-65;

                System.out.println("lig : " + lig + "\t col : " + col);

                if (lig <= nbLig && lig >= 0 && col <= nbCol && col >= 0) return true;
            }
            else
                if (Integer.parseInt(saisie) < 4 && Integer.parseInt(saisie) > 0) return true;
        } catch (Exception e) {}

        return false;
    }

    private boolean estFinDePartie()
    {
        for (int i = 0 ; i < PartieConsole.joueurs.length ; i++)
            if (PartieConsole.joueurs[i].getNbTwistLock() != 0) return false;

        return true;
    }

    private String afficherTableauContainer()
    {
        String sRet = "";

        sRet += "    ";

        char col = 'A';
        while(col <= 64+nbCol) sRet += "  " + col++ + "  ";

        sRet +="\n";

        for (int i = 0; i < nbLig; i++)
        {
            sRet += "   " + this.tabContainer[i][0].getCoins()[0];

            for (int j = 0 ; j < nbCol ; j++)
                sRet += this.tabContainer[i][j].toString1();

            sRet += "\n";


            sRet += String.format("%2s",(1 + i)) + " |";

            for (int j = 0 ; j < nbCol ; j++)
                sRet += this.tabContainer[i][j].toString2();

            sRet += "\n";

            if (i == nbLig-1)
            {
                sRet += "   " + this.tabContainer[i][0].getCoins()[2];

                for (int j = 0 ; j < nbCol ; j++)
                    sRet += this.tabContainer[i][j].toString3();

                sRet += "\n";
            }
        }

        return sRet;
    }


    public static String getBase()
    {
        return "\033[30m";
    }

    public static void main(String[] args)
    {
        new PartieConsole(10, 15, 4);
    }
}
