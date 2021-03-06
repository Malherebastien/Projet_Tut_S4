package Model;

/**
 * Représente un joueur
 *
 * @author Groupe109
 */

public class Joueur
{
    private final int INIT_TWIST_LOCK = 1;

    private int nbTwistLock; //Commence à 20
    private int score;

    private String couleur;
    private String codeCouleur;
    private String nom;
    private boolean humain;

    /**
     * Constructeur de la classe Joueur
     *
     * @param couleur
     * @param codeCouleur
     * @param humain
     */

    public Joueur(String couleur, String codeCouleur, boolean humain)
    {
        this.nbTwistLock = INIT_TWIST_LOCK;
        this.score = 0;
        this.couleur = couleur;
        this.codeCouleur = codeCouleur;
        this.humain = humain;

        this.nom = "";
    }

    public Joueur(String couleur, String codeCouleur)
    {
        this.nbTwistLock = INIT_TWIST_LOCK;
        this.score = 0;
        this.couleur = couleur;
        this.codeCouleur = codeCouleur;

        this.nom = "";
    }

    //Overide dans AI
    public String jouerTour(Container[][] tabContainer)
    {
        return "";
    }

    /**
     * Accesseur du nom du joueur
     *
     * @return
     */
    public String getNom() { return this.nom;}

    /**
     * Modificateur du nom du joueur
     *
     * @param nom
     */
    public void setNom(String nom) { this.nom = nom;}

    /**
     * Accesseur du nombre de Twist-Lock
     *
     * @return nbTwistLock
     */
    public int getNbTwistLock()
    {
        return nbTwistLock;
    }

    /**
     * Modificateur du nombre de Twist-Lock
     *
     * @param nbTwistLock
     */
    public void setNbTwistLock(int nbTwistLock)
    {
        this.nbTwistLock = nbTwistLock;
    }

    /**
     * Accesseur du score
     *
     * @return score
     */
    public int getScore()
    {
        return score;
    }

    /**
     * Modificateur du score du joueur
     *
     * @param score
     */
    public void setScore(int score)
    {
        this.score = score;
    }

    /**
     * Accesseur de la couleur du joueur
     *
     * @return couleur
     */
    public String getCouleur()
    {
        return couleur;
    }

    /**
     * Modificateur de la couleur
     *
     * @param couleur
     */
    public void setCouleur(String couleur)
    {
        this.couleur = couleur;
    }

    /**
     * Accesseur du code couleur
     *
     * @return codeCouleur
     */
    public String getCodeCouleur()
    {
        return codeCouleur;
    }

    /**
     * Accesseur de la base
     *
     * @return String
     */
    public String getBase()
    {
        return "\033[30m";
    }

    public boolean isHumain()
    {
        return humain;
    }

    /**
     * Méthode de mise en forme
     *
     * @return String
     */
    public String toString()
    {
        return codeCouleur + couleur + getBase();
    }
}
