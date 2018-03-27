package Model;

import Controler.PartieConsole;

import java.util.HashMap;

public class Container
{
    private int valeur; //Aléatoire entre 5 et 54

    private Joueur proprietaire; //Le joueur qui possède le container. null si non possédé ou inégalité.

    private Coin[] coins;
    private int nbCoins; //Nombre de coins remplis

    public Container(int valeur)
    {
        this.valeur = valeur;
        this.proprietaire = null;

        this.coins = new Coin[4];
        this.nbCoins = 0; //Sert juste à mettre les coins dans l'ordre.
    }

    public void addCoin(Coin coin)
    {
        this.coins[nbCoins] = coin;
        this.nbCoins++;
    }

    public void setScoreJoueur()
    {
        setProprietaire();
        if (proprietaire != null)
            proprietaire.setScore(proprietaire.getScore() + this.valeur);
    }

    public Coin[] getCoins()
    {
        return coins;
    }

    public String toString1()
    {
        return "----" + this.coins[1].toString();
    }
//
    public String toString2()
    {
        if (proprietaire != null)
            return " " + this.proprietaire.getCodeCouleur() + String.format("%2d",this.valeur) + this.proprietaire.getBase() + " |";

        return " " + String.format("%2d",this.valeur) + " |";
    }

    public String toString3()
    {
        return "----" + this.coins[2].toString();
    }

    public int getValeur()
    {
        return valeur;
    }

    public void setValeur(int valeur)
    {
        this.valeur = valeur;
    }

    public Joueur getProprietaire()
    {
        return proprietaire;
    }

    public void setProprietaire()
    {
        this.proprietaire = null;

        HashMap<Joueur, Integer> mapJoueur = new HashMap<>();

        for (int i = 0; i < PartieConsole.joueurs.length ; i++)
            mapJoueur.put(PartieConsole.joueurs[i], 0);

        for (int i = 0; i < coins.length; i++)
        {
            if (coins[i].isOccupe())
                for (Object j : mapJoueur.keySet())
                    if (coins[i].getOccupant() == (Joueur)j) mapJoueur.put((Joueur)j, mapJoueur.get(j)+1);
        }

        boolean egalite = false;
        Joueur premier = null;
        for (Object j : mapJoueur.keySet())
        {
            if (mapJoueur.get((Joueur)j) != 0)
            {
                if (premier == null) premier = (Joueur)j;
                if (mapJoueur.get(premier) < mapJoueur.get(j)) premier = (Joueur)j;
                if (premier != (Joueur)j && mapJoueur.get(premier) == mapJoueur.get((Joueur)j)) egalite = true;
            }
        }

        if (!egalite) this.proprietaire = premier;
    }
}
