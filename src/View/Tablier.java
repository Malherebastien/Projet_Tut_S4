package View;

import Model.CoinButton;
import Model.ContainerJPanel;
import Model.Joueur;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.TitledBorder;

public class Tablier extends JFrame {
    public static CoinButton tableauCoinButton[];
    private static ArrayList<ContainerJPanel> arrayCP = new ArrayList<ContainerJPanel>();
    public Tablier(Joueur[] joueur) {
        this.setTitle("Bouton");
        this.setSize(400, 300);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLayout(new FlowLayout());
        JPanel grille = new JPanel(new GridLayout(10,7));

        for ( int i = 0 ; i < 70 ; i++ ) {
            ContainerJPanel cP = new ContainerJPanel();
            arrayCP.add(cP);
            grille.add(cP);
        }

        Color[] color = {Color.red, Color.green, Color.blue, Color.yellow};

        for(int i = 0; i < joueur.length; i++) {
            JPanel pan = new JPanel(new BorderLayout());
            pan.add(new JLabel(new ImageIcon("im" + i + ".png")), BorderLayout.NORTH);
            JLabel nbCoups = new JLabel("Joueur " + i + " : " + joueur[i].getScore() + ", il lui reste " + joueur[i].getNbTwistLock());
            pan.add(nbCoups, BorderLayout.SOUTH);
            add(pan);
        }

        add(grille);

        this.setVisible(true);
        this.pack();
    }

    public static void main(String args[]) {
        Joueur[] j = new Joueur[4];

        j[0] = new Joueur("red", "#f00");
        j[1] = new Joueur("green", "#0f0");
        j[2] = new Joueur("blue", "#00f");
        j[3] = new Joueur("yellow", "#ff0");


        new Tablier(j);
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
                            if ( numPanel > 6 ) {
                                arrayCP.get(numPanel - 7).getCoinButton(3).setColor("red");
                                if ( numPanel % 7 != 0)
                                {
                                    arrayCP.get( numPanel - 8).getCoinButton(4).setColor("red");
                                    arrayCP.get( numPanel - 1).getCoinButton(2).setColor("red");
                                }
                            }
                            else if ( numPanel != 0 )
                            {
                                arrayCP.get( numPanel - 1).getCoinButton(2).setColor("red");
                            }
                            break;
                        case 2:
                            System.out.println( numPanel % 7 );
                            if (numPanel > 6) {
                                if ( numPanel % 7 != 6) {
                                    arrayCP.get( numPanel - 6).getCoinButton(3).setColor("red");
                                    arrayCP.get( numPanel + 1).getCoinButton(1).setColor("red");
                                }
                                arrayCP.get( numPanel - 7).getCoinButton(4).setColor("red");
                            }
                            else if ( numPanel != 6 )
                            {
                                arrayCP.get( numPanel + 1).getCoinButton(1).setColor("red");
                            }
                            break;
                        case 3:
                            if (numPanel < 63) {
                                if ( numPanel % 7 != 0)
                                {
                                    arrayCP.get( numPanel + 6).getCoinButton(2).setColor("red");
                                    arrayCP.get( numPanel - 1).getCoinButton(4).setColor("red");
                                }
                                arrayCP.get(numPanel + 7).getCoinButton(1).setColor("red");
                            }
                            else if ( numPanel != 63 )
                            {
                                arrayCP.get(numPanel - 1).getCoinButton(4).setColor("red");
                            }
                            break;
                        case 4:
                            if ( numPanel < 63 ) {
                                if ( numPanel % 7 != 6) {
                                    arrayCP.get( numPanel + 1).getCoinButton(3).setColor("red");
                                    arrayCP.get(numPanel + 8).getCoinButton(1).setColor("red");
                                }
                                arrayCP.get( numPanel + 7).getCoinButton(2).setColor("red");
                            }
                            else if ( numPanel != 69 )
                            {
                                arrayCP.get(numPanel + 1).getCoinButton(3).setColor("red");
                            }
                            break;
                    }
                }
            }
        }
    }
}