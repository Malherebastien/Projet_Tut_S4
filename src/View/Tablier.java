package View;

import Model.CoinButton;
import Model.ContainerJPanel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Tablier extends JFrame {
    public static CoinButton tableauCoinButton[];
    private static ArrayList<ContainerJPanel> arrayCP = new ArrayList<ContainerJPanel>();
    public Tablier() {
        this.setTitle("Bouton");
        this.setSize(300, 300);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setLayout(new GridLayout(10,7));

        for ( int i = 0 ; i < 70 ; i++ ) {
            ContainerJPanel cP = new ContainerJPanel();
            arrayCP.add(cP);
            this.getContentPane().add(cP);
        }
        this.setVisible(true);
        this.pack();
    }

    public static void main(String args[]) {
        new Tablier();
    }

    public static void actualiserIHM()
    {
        for ( int numPanel = 0 ; numPanel < arrayCP.size() ; numPanel++ )
        {
            for (int bord = 1 ; bord <= 4 ; bord++ )
            {
                if ( arrayCP.get(numPanel).getCoinButton(bord).getColor().equals("red") )
                {
                    System.out.println("Bord rouge au container " + numPanel + " et au bord " + bord);
                    switch (bord) {
                        case 1:
                            if (numPanel > 6) {
                                arrayCP.get(numPanel - 7).getCoinButton(3).setColor("red");
                                arrayCP.get(numPanel - 8).getCoinButton(4).setColor("red");
                                arrayCP.get(numPanel - 1).getCoinButton(2).setColor("red");
                            }
                            else if ( numPanel != 0 )
                            {
                                arrayCP.get(numPanel - 1).getCoinButton(2).setColor("red");
                            }
                            break;
                        case 2:
                            if (numPanel > 6) {
                                arrayCP.get(numPanel - 6).getCoinButton(3).setColor("red");
                                arrayCP.get(numPanel - 7).getCoinButton(4).setColor("red");
                                arrayCP.get(numPanel + 1).getCoinButton(1).setColor("red");
                            }
                            else if ( numPanel != 6 )
                            {
                                arrayCP.get(numPanel + 1).getCoinButton(1).setColor("red");
                            }
                            break;
                        case 3:
                            if (numPanel < 63) {
                                arrayCP.get(numPanel + 7).getCoinButton(1).setColor("red");
                                arrayCP.get(numPanel + 6).getCoinButton(2).setColor("red");
                                arrayCP.get(numPanel - 1).getCoinButton(4).setColor("red");
                            }
                            else if ( numPanel != 63 )
                            {
                                arrayCP.get(numPanel - 1).getCoinButton(4).setColor("red");
                            }
                            break;
                        case 4:
                            if (numPanel < 63) {
                                arrayCP.get(numPanel + 1).getCoinButton(3).setColor("red");
                                arrayCP.get(numPanel + 7).getCoinButton(2).setColor("red");
                                arrayCP.get(numPanel + 8).getCoinButton(1).setColor("red");
                            }
                            else if ( numPanel != 63 )
                            {
                                arrayCP.get(numPanel - 1).getCoinButton(4).setColor("red");
                            }
                            break;
                    }
                }
            }
        }
    }
}