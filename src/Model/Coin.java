package Model;

/**
 * Représente les 4 différents coins d'un Twist-Lock
 *
 * @author Groupe109
 */

public class Coin
{
    private boolean occupe;
    private Joueur occupant;

    /**
     * Constructeur de la classe Coin
     */
    public Coin()
    {
        occupe = false;
    }

    /**
     * Accesseur au booleen occupant
     *
     * @return occupant
     */
    public Joueur getOccupant()
    {
        return occupant;
    }

    /**
     * Modificateur de l'occupant en changeant le booleen occupe
     *
     * @param occupant
     */
    public void setOccupant(Joueur occupant)
    {
        this.occupant = occupant;
        if (occupant != null)
            this.occupe = true;
        else
            this.occupe = false;
    }

    /**
     * Vérifie si un coin est occupé
     *
     * @return occupe
     */
    public boolean isOccupe()
    {
        return occupe;
    }

    /**
     * Modificateur du booleen occupe
     *
     * @param occupe
     */
    public void setOccupe(boolean occupe)
    {
        this.occupe = occupe;
    }

    /**
     * Méthode de mise en forme de la classe Coin
     *
     * @return String
     */
    public String toString()
    {
        if (this.occupe)
            return this.occupant.getCodeCouleur() + "+" + this.occupant.getBase();
        return "*";
    }
}
