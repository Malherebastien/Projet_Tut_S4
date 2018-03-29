package Model;

/**
 * Classe preset, sert à préparer l'ihm en préconfigurant le bouton d'un coin
 *
 * @author Groupe109
 */

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;

public class CoinButton extends JButton
{
    private Image i;
    private String coin;
    private String color;

    /**
     * Constructeur de la classe CoinButton
     * Initialise un coin, y attribue une image et cen
     *
     * @param coin
     */
    public CoinButton(String coin)
    {
        this.coin = coin;
        try
        {
            i = ImageIO.read(new File("src/Images/imgContainer" + coin + "_base.png"));
        } catch (Exception e)
        {
            System.out.println("Le lien de l'image est invalide !");
        }
        this.setIcon(new ImageIcon(i));
        //this.setMargin(new Insets(0, 0, 0, 0));
        //this.setBorder(null);
        this.color = "default";
    }

    /**
     * Modificateur de color,
     * Sert à définir la couleur en fonction de la couleur souhaitée
     *
     * @param color
     */
    public void setColor(String color)
    {
        switch (color)
        {
            case "red":
                try
                {
                    i = ImageIO.read(new File("src/Images/imgContainer" + coin + "_red.png"));
                    this.setIcon(new ImageIcon(i));
                    this.color = "red";
                } catch (Exception e)
                {
                    System.out.println("Le lien de l'image est invalide !");
                }
                break;

            case "green":
                try
                {
                    i = ImageIO.read(new File("src/Images/imgContainer" + coin + "_green.png"));
                    this.setIcon(new ImageIcon(i));
                    this.color = "green";
                } catch (Exception e)
                {
                    System.out.println("Le lien de l'image est invalide !");
                }
                break;

            case "blue":
                try
                {
                    i = ImageIO.read(new File("src/Images/imgContainer" + coin + "_blue.png"));
                    this.setIcon(new ImageIcon(i));
                    this.color = "blue";
                } catch (Exception e)
                {
                    System.out.println("Le lien de l'image est invalide !");
                }
                break;

            case "yellow":
                try
                {
                    i = ImageIO.read(new File("src/Images/imgContainer" + coin + "_yellow.png"));
                    this.setIcon(new ImageIcon(i));
                    this.color = "yellow";
                } catch (Exception e)
                {
                    System.out.println("Le lien de l'image est invalide !");
                }
                break;
        }
    }

    /**
     * Réinitialise la couleur d'un container par la couleur de base
     */
    public void resetColor()
    {
        try
        {
            i = ImageIO.read(new File("src/Images/imgContainer" + coin + "_base.png"));
        } catch (Exception e)
        {
            System.out.println("Le lien de l'image est invalide !");
        }
        this.setIcon(new ImageIcon(i));
    }

    /**
     * Modificateur de la couleur
     *
     * @return color
     */
    public String getColor()
    {
        return this.color;
    }
}
