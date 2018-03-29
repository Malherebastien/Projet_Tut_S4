package Model;

public class Joueur
{
    private final int INIT_TWIST_LOCK = 3;

    private int nbTwistLock; //Commence à 20
    private int score;

    private String couleur;
    private String codeCouleur;
    private String nom;
    private boolean humain;

    public Joueur(String couleur, String codeCouleur, boolean humain)
    {
        this.nbTwistLock = INIT_TWIST_LOCK;
        this.score = 0;
        this.couleur = couleur;
        this.codeCouleur = codeCouleur;
        this.humain = humain;

        this.nom = "";
    }

    //Overide dans AI
    public String jouerTour(Container[][] tabContainer)
    {
        return "";
    }

    public String getNom() { return this.nom;}

    public void setNom(String nom) { this.nom = nom;}

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

    public boolean isHumain()
    {
        return humain;
    }

    public String toString()
    {
        return codeCouleur + couleur + getBase();
    }
}
