package Model;

public class Joueur
{
    private final int INIT_TWIST_LOCK = 20;

    private int nbTwistLock; //Commence à 20
    private int score;

    private String couleur;
    private String codeCouleur;

    public Joueur(String couleur, String codeCouleur)
    {
        this.nbTwistLock = INIT_TWIST_LOCK;
        this.score = score;
        this.couleur = couleur;
        this.codeCouleur = codeCouleur;
    }

    public int getNbTwistLock()
    {
        return nbTwistLock;
    }

    public void setNbTwistLock(int nbTwistLock)
    {
        this.nbTwistLock = nbTwistLock;
    }

    public int getScore()
    {
        return score;
    }

    public void setScore(int score)
    {
        this.score = score;
    }

    public String getCouleur()
    {
        return couleur;
    }

    public void setCouleur(String couleur)
    {
        this.couleur = couleur;
    }

    public String getCodeCouleur()
    {
        return codeCouleur;
    }

    public String getBase()
    {
        return "\033[30m";
    }
}
