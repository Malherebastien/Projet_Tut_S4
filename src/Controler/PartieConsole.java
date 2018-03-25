package Controler;

import Model.Coin;
import Model.Container;
import Model.Joueur;

import java.util.Scanner;

public class PartieConsole
{
    private Container[][] tabContainer;
    private Coin[][] tabCoin;

    private int nbLig; //Random
    private int nbCol; //Random

    private Joueur joueur1;
    private Joueur joueur2; //2-4 joueurs max

    private Joueur joueurActif;

    public PartieConsole(int nbLig, int nbCol, Joueur joueur1, Joueur joueur2)
    {
        this.nbLig = nbLig;
        this.nbCol = nbCol;

        this.joueur1 = joueur1;
        this.joueur2 = joueur2;

        this.joueurActif = joueur1; // Oui, arbitrairement, parce que je fais qu'est-ce que je veux.

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
                this.tabContainer[i][j].addCoin(this.tabCoin[i+1][j]); //Bas gauche
                this.tabContainer[i][j].addCoin(this.tabCoin[i+1][j+1]); //Bas droite
            }
        }
        //Remplissage du tableau de Model.Coin de chaque Model.Container (moins compliqué que ce que je pensais)

        System.out.println("==============================================");
        System.out.println("             Début de partie !");
        System.out.println("==============================================");

        Scanner sc = new Scanner(System.in);
        while (joueur1.getNbTwistLock() != 0 && joueur2.getNbTwistLock() != 0)
        {
            System.out.println("C'est le tour du joueur " + joueurActif.getCodeCouleur() + joueurActif.getCouleur() +
                    getBase() + "!\nIl lui reste " +
                    this.joueurActif.getNbTwistLock() + " TwistLocks !");

            System.out.println(afficherTableauContainer());

            String choix;
            do
            {
                System.out.println("Choisissez ligne + colonne (genre 9B)");
                choix = sc.nextLine();
            } while (choix.length() != 2); //TODO Faire meilleur vérif.

            String coin;
            do
            {
                System.out.println("Choisissez l'un des quatres coins (1 à 4)");
                coin = sc.nextLine();
            } while(coin.length() != 1); //TODO verifs + retour arrière.

            int lig = choix.charAt(0) - '0'; //Ok, ça marche pour transformer un char en int :o
            System.out.println(lig);
            this.tabContainer[lig - 1][(int)(choix.charAt(1)) - 65]
                    .getCoins()[Integer.parseInt(coin) - 1].setOccupant(joueurActif);
            System.out.println(this.tabContainer[lig - 1][(int)(choix.charAt(1)) - 65]
                    .getCoins()[Integer.parseInt(coin) - 1].isOccupe());
            // -64 pour les lettres et -1 pour le tableau
            //TODO Comment compter points

            //Gestion de tours, à changer pour permettre + que 2 joueurs.
            this.joueurActif.setNbTwistLock(joueurActif.getNbTwistLock() - 1);
            if (this.joueurActif == joueur1)
                this.joueurActif = joueur2;
            else
                this.joueurActif = joueur1;
        }
    }

    private String afficherTableauContainer()
    {
        String sRet = "";

        sRet += "  " + "  1   " + "  2   " + "  3   " + "  4   " + "  5   " + "  6   " + "  7   \n";

        for (int i = 0; i < nbLig; i++)
        {

            sRet += "  " + this.tabContainer[i][0].toString1();
            sRet += this.tabContainer[i][1].toString1();
            sRet += this.tabContainer[i][2].toString1();
            sRet += this.tabContainer[i][3].toString1();
            sRet += this.tabContainer[i][4].toString1();
            sRet += this.tabContainer[i][5].toString1();
            sRet += this.tabContainer[i][6].toString1() + "\n";

            sRet += String.valueOf((char) ('A' + i));
            sRet += " " + this.tabContainer[i][0].toString2();
            sRet += this.tabContainer[i][1].toString2();
            sRet += this.tabContainer[i][2].toString2();
            sRet += this.tabContainer[i][3].toString2();
            sRet += this.tabContainer[i][4].toString2();
            sRet += this.tabContainer[i][5].toString2();
            sRet += this.tabContainer[i][6].toString2() + "\n";

            sRet += "  " + this.tabContainer[i][0].toString3();
            sRet += this.tabContainer[i][1].toString3();
            sRet += this.tabContainer[i][2].toString3();
            sRet += this.tabContainer[i][3].toString3();
            sRet += this.tabContainer[i][4].toString3();
            sRet += this.tabContainer[i][5].toString3();
            sRet += this.tabContainer[i][6].toString3() + "\n";
        }

        return sRet;
    }

    public Container[][] getTabContainer()
    {
        return tabContainer;
    }

    public void setTabContainer(Container[][] tabContainer)
    {
        this.tabContainer = tabContainer;
    }

    public int getNbCol()
    {
        return nbCol;
    }

    public void setNbCol(int nbCol)
    {
        this.nbCol = nbCol;
    }

    public int getNbLig()
    {
        return nbLig;
    }

    public void setNbLig(int nbLig)
    {
        this.nbLig = nbLig;
    }

    public Joueur getJoueur1()
    {
        return joueur1;
    }

    public void setJoueur1(Joueur joueur1)
    {
        this.joueur1 = joueur1;
    }

    public Joueur getJoueur2()
    {
        return joueur2;
    }

    public void setJoueur2(Joueur joueur2)
    {
        this.joueur2 = joueur2;
    }

    public static String getRouge() {
        return "\033[31m";
    }

    public static String getVert() {
        return "\033[32m";
    }

    public static String getBase()
    {
        return "\033[30m";
    }

    public static void main(String[] args)
    {
        new PartieConsole(10, 7, new Joueur("Rouge", "\033[31m"), new Joueur("Vert", "\033[32m"));
        System.out.println(getRouge() + "Hello World !" + getBase() + " Coucou le monde !");
    }
}
