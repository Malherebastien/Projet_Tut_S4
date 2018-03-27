package Model;

public class Coin
{//
    private boolean occupe;
    private Joueur occupant;

    public Coin()
    {
        occupe = false;
    }

    public Joueur getOccupant()
    {
        return occupant;
    }

    public void setOccupant(Joueur occupant)
    {
        this.occupant = occupant;
        this.occupe = true;
    }

    public boolean isOccupe()
    {
        return occupe;
    }

    public void setOccupe(boolean occupe)
    {
        this.occupe = occupe;
    }

    public String toString()
    {
        if (this.occupe)
            return this.occupant.getCodeCouleur() + "+" + this.occupant.getBase();
        return "*";
    }
}
