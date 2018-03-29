package Model;

import java.util.ArrayList;

public class AI extends Joueur
{

    public AI(String couleur, String codeCouleur)
    {
        super(couleur, codeCouleur, false);
    }

    @Override
    public String jouerTour(Container[][] tabContainer)
    {
        int scoreMax = 0;
        int sommeScore = 0;
        String placement = "";

        ArrayList<Coin> coinTraverses = new ArrayList<>();

        for (int lig = 0; lig < tabContainer.length; lig++)
        {
            for (int col = 0; col < tabContainer[0].length; col++)
            {
                Coin[] coins = tabContainer[lig][col].getCoins();

                for (int i = 0; i < 4; i++)
                {
                    if (!coins[i].isOccupe() && !coinTraverses.contains(coins[i]))
                    {
                        coinTraverses.add(coins[i]);
                        coins[i].setOccupant(this);
                        sommeScore = 0;
                        for (int lig2 = 0; lig2 < tabContainer.length; lig2++)
                        {
                            for (int col2 = 0; col2 < tabContainer[0].length; col2++)
                            {
                                sommeScore += tabContainer[lig2][col2].calculerScore();
                            }
                        }
                        if (sommeScore > scoreMax)
                        {
                            scoreMax = sommeScore;
                            char colonne = (char) (col + 65);
                            placement = "" + lig  + " " + col + " " + i;
                        }
                        coins[i].setOccupant(null);
                    }
                }
            }
        }
        return placement;
    }
}
