package Model;

import Controler.PartieConsole;
import Reseau.Serveur;

import java.util.HashMap;

/**
 * Représente les conteneurs
 *
 * @author Groupe109
 */

public class Container
{
    private int valeur;             // Aléatoire entre 5 et 54

    private Joueur proprietaire;    // Le joueur qui possède le container. null si non possédé ou inégalité.

    private Coin[] coins;
    private int nbCoins;            // Nombre de coins remplis

    /**
     * Constructeur de la classe Container
     * Initialise un tableau de 4 coins pour chaque conteneur
     *
     * @param valeur
     */
    public Container(int valeur)
    {
        this.valeur = valeur;
        this.proprietaire = null;

        this.coins = new Coin[4];
        this.nbCoins = 0;           // Sert juste à mettre les coins dans l'ordre.
    }

    /**
     * Ajoute un coin au tableau de coins
     *
     * @param coin
     */
    public void addCoin(Coin coin)
    {
        this.coins[nbCoins] = coin;
        this.nbCoins++;
    }

    /**
     * Modificateur qui appelle lui-même le modificateur setProprietaire
     * Calcul le score du joueur si non nul
     */
    public void setScoreJoueur()
    {
        setProprietaire();
        if (proprietaire != null)
            proprietaire.setScore(proprietaire.getScore() + this.valeur);
    }

	/**
	 * Modificateur qui appelle lui-même le modificateur setProprietaire
	 * Calcul le score du joueur si non nul
	 */
	public void setScoreJoueurServeur()
	{
		setProprietaireServeur();
		if (proprietaire != null)
			proprietaire.setScore(proprietaire.getScore() + this.valeur);
	}

    /**
     * Accesseur de coin
     *
     * @return coins
     */
    public Coin[] getCoins()
    {
        return coins;
    }

    /**
     * Méthode de mise en forme de la classe Container #1
     * Correspond à la ligne du haut, aux premiers coins
     *
     * @return String
     */
    public String toString1()
    {
        return "----" + this.coins[1].toString();
    }

    /**
     * Méthode de mise en forme de la classe Container #2
     *
     * @return String
     */
    public String toString2()
    {
        if (proprietaire != null)
            return " " + this.proprietaire.getCodeCouleur() + String.format("%2d",this.valeur) + this.proprietaire.getBase() + " |";

        return " " + String.format("%2d",this.valeur) + " |";
    }

    /**
     * Méthode de mise en forme de la classe Container #3
     * Correspond à la ligne de bas de l'application, aux derniers coins
     *
     * @return String
     */
    public String toString3()
    {
        return "----" + this.coins[2].toString();
    }

    /**
     * Accesseur de la variable valeur
     *
     * @return valeur
     */
    public int getValeur()
    {
        return valeur;
    }

    /**
     * Modificateur de la variable valeur
     *
     * @param valeur
     */
    public void setValeur(int valeur)
    {
        this.valeur = valeur;
    }

    /**
     * Accesseur du propriétaire
     *
     * @return
     */
    public Joueur getProprietaire()
    {
        return proprietaire;
    }

    /**
     * Modificateur du propriétaire
     */
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

    /**
     * Modificateur du propriétaire pour le serveur
     */
    public void setProprietaireServeur()
    {
        this.proprietaire = null;

        HashMap<Joueur, Integer> mapJoueur = new HashMap<>();

        for (int i = 0; i < Serveur.joueurs.length ; i++)
            mapJoueur.put(Serveur.joueurs[i], 0);

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
