package Controler;

import Model.AI;
import Model.Coin;
import Model.Container;
import Model.Joueur;

import java.util.Scanner;

public class PartieConsoleAI
{//
    public static Joueur[] joueurs;

    private static final String[] COULEURS = new String[] { "Rouge;\033[31m", "Vert;\033[32m", "Bleu;\033[34m", "Orange;\033[33m" };

    private Container[][] tabContainer;
    private Coin[][] tabCoin;

    private int nbLig; //Random
    private int nbCol; //Random

    private Joueur joueurActif;
    private int nbJoueurs;

    public PartieConsoleAI(int nbLig, int nbCol, int nbJoueurs)
    {
        this.nbLig = nbLig;
        this.nbCol = nbCol;

        if (nbJoueurs < 2) nbJoueurs = 2;
        if (nbJoueurs > 4) nbJoueurs = 4;

        this.nbJoueurs = nbJoueurs;
        PartieConsoleAI.joueurs = new Joueur[] {new Joueur("Rouge", "\033[31m", true), new AI("Vert", "\033[32m")};

        /*for (int i = 0 ; i < nbJoueurs ; i++)
            PartieConsoleAI.joueurs[i] = new Joueur(PartieConsoleAI.COULEURS[i].split(";")[0], PartieConsoleAI.COULEURS[i].split(";")[1]);
        */

        this.joueurActif = PartieConsoleAI.joueurs[0];

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
    }

    public void lancerPartie()
    {
        System.out.println("==============================================");
        System.out.println("             Début de partie !");
        System.out.println("==============================================");

        Scanner sc = new Scanner(System.in);

        while (!this.estFinDePartie())
        {
            System.out.println("C'est le tour du joueur " + joueurActif.getCodeCouleur() + joueurActif.getCouleur() + getBase() + " !");
            System.out.println("\tTwistLock(s) restant(s) : " + this.joueurActif.getNbTwistLock());
            System.out.println("\tScore : " + joueurActif.getScore() + "\n");

            if (this.joueurActif.getNbTwistLock() != 0)
            {
                if (this.joueurActif.isHumain())
                {
                    System.out.println(afficherTableauContainer(nbCol, nbLig));

                    String choix;
                    do
                    {
                        System.out.println("Choisissez ligne + colonne (genre 9B)");
                        choix = sc.nextLine();
                    } while (!(choix.length() >= 2 && choix.length() <= 3) || !estSaisieValide(choix));

                    String coin;
                    do
                    {
                        System.out.println("Choisissez l'un des quatres coins (1 à 4)");
                        coin = sc.nextLine();
                    } while (coin.length() != 1 || !estSaisieValide(coin));

                    int lig = Integer.parseInt(choix.substring(0, choix.length() - 1));

                    if (!tabContainer[lig - 1][(int) Character.toUpperCase(choix.charAt(choix.length() - 1)) - 65].getCoins()[Integer.parseInt(coin) - 1].isOccupe())
                    {
                        this.tabContainer[lig - 1][(int) Character.toUpperCase(choix.charAt(choix.length() - 1)) - 65].getCoins()[Integer.parseInt(coin) - 1].setOccupant(joueurActif);
                        // -64 pour les lettres et -1 pour le tableau

                        // Parcours des joueurs pour remettres leurs points à 0 avant de fair eune nouvelle affectation
                        for (int i = 0; i < PartieConsoleAI.joueurs.length; i++)
                            PartieConsoleAI.joueurs[i].setScore(0);

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
                else
                {
                    String AIChoix = this.joueurActif.jouerTour(this.tabContainer);

                    this.tabContainer[Integer.parseInt(AIChoix.split(" ")[0])][Integer.parseInt(AIChoix.split(" ")[1])]
                            .getCoins()[Integer.parseInt(AIChoix.split(" ")[2])].setOccupant(this.joueurActif);
                    // Parcours des joueurs pour remettres leurs points à 0 avant de fair eune nouvelle affectation
                    for (int i = 0; i < PartieConsoleAI.joueurs.length; i++)
                        PartieConsoleAI.joueurs[i].setScore(0);

                    // Parcours des containers pour mettre les points aux joueurs
                    for (int i = 0; i < nbLig; i++)
                        for (int j = 0; j < nbCol; j++)
                            this.tabContainer[i][j].setScoreJoueur();
                }

                //Gestion de tours
                this.joueurActif.setNbTwistLock(joueurActif.getNbTwistLock() - 1);

            }
            else
                System.out.println("Plus de TwistLocks ! Joueur suivant.");

            for (int i = 0 ; i < this.nbJoueurs ; i++)
                if (this.joueurActif == PartieConsoleAI.joueurs[i])
                {
                    if (i == this.nbJoueurs-1) this.joueurActif = PartieConsoleAI.joueurs[0];
                    else                       this.joueurActif = PartieConsoleAI.joueurs[i+1];

                    break;
                }
        }

        System.out.println(afficherFinPartie());
    }

    private boolean estSaisieValide(String saisie)
    {
        try {
            if (saisie.length() >= 2)
            {
                int lig = Integer.parseInt(saisie.substring(0,saisie.length()-1));
                int col = Character.toUpperCase(saisie.charAt(saisie.length()-1))-65;

                if (lig <= nbLig && lig >= 0 && col <= nbCol && col >= 0) return true;
            }
            else
            if (Integer.parseInt(saisie) <= 4 && Integer.parseInt(saisie) > 0) return true;
        } catch (Exception e){}

        return false;
    }

    public boolean estSaisieValideServeur(String saisie)
    {
        try {
            if (saisie.length() == 3)
            {
                int lig  = Integer.parseInt(saisie.substring(0,1));
                int col  = Character.toUpperCase(saisie.charAt(1))-65;
                int coin = Integer.parseInt(saisie.substring(2,saisie.length()-1));

                if (lig <= nbLig && lig >= 0 && col <= nbCol && col >= 0) return true;
                if (coin <=0 || coin >= 5 ) return true;
            }

            //if (Integer.parseInt(saisie) < 4 && Integer.parseInt(saisie) > 0) return true;
        } catch (Exception e) {}

        return false;
    }



    private boolean estFinDePartie()
    {
        for (int i = 0 ; i < PartieConsoleAI.joueurs.length ; i++)
            if (PartieConsoleAI.joueurs[i].getNbTwistLock() != 0) return false;

        return true;
    }

    public String afficherFinPartie()
    {
        String sRet = "";

        sRet += "\n\nFIN DE PARTIE\n\n";

        Joueur gagnant = PartieConsoleAI.joueurs[0];

        for (int i = 0 ; i < PartieConsoleAI.joueurs.length ; i++)
        {
            Joueur jTmp = PartieConsoleAI.joueurs[i];

            sRet += "\tJoueur " + jTmp + " : " + jTmp.getScore() + "\n";

            if (jTmp.getScore() > gagnant.getScore()) gagnant = jTmp;
        }

        sRet += "\nLE GAGNANT EST : joueur " + gagnant + " avec " + gagnant.getScore() + " points !!!";

        return sRet;
    }

    public String afficherTableauContainer(int nbCol, int nbLig)
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


    public static String getBase()
    {
        return "\033[30m";
    }

    public Container getContainer(int i, int j){ return this.tabContainer[i][j];}

    public Joueur getJoueurActif () { return this.joueurActif;}

    public int getNbJoueurs () { return this.nbJoueurs;}

    public static void main(String[] args)
    {
        new PartieConsoleAI(10, 15, 2).lancerPartie();
    }

}
