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

    public Coin[] getCoins()
    {
        return coins;
    }

    public String toString1()
    {
        return this.coins[0].toString() + "----" + this.coins[1].toString();
    }

    public String toString2()
    {
        return "| " + String.format("%2d",this.valeur) + " |";
    }

    public String toString3()
    {
        return this.coins[2].toString() + "----" + this.coins[3].toString();
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

    public void setProprietaire(Joueur proprietaire)
    {
        this.proprietaire = proprietaire;
    }
}
