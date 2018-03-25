package Model;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;

public class CoinButton extends JButton {
    private Image i;
    public CoinButton( String coin ) {
        //On essaye de lire l'image
        try
        {
            i = ImageIO.read(new File("src/Images/imgContainer"+coin+"_base.png"));
        }
        catch(Exception e)
        {
            System.out.println("Le lien de l'image est invalide !");
        }
        this.setIcon(new ImageIcon(i));
        this.setMargin(new Insets(0, 0, 0, 0));
    }
}
