package View;

import Model.CoinButton;
import Model.Container;
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
    private JPanel[] panCoups;
    private JLabel[] scoreJoueur;
    private Joueur[] joueur;
    public Tablier(Joueur[] joueur) {
        this.setTitle("Bouton");
        this.setSize(400, 300);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLayout(new FlowLayout());
        JPanel grille = new JPanel(new GridLayout(10,7));
        grille.setPreferredSize(new Dimension(700,900));
        this.joueur = joueur;

        for ( int i = 0 ; i < 70 ; i++ ) {
            ContainerJPanel cP = new ContainerJPanel(this);
            arrayCP.add(cP);
            grille.add(cP);
        }

        panCoups = new JPanel[joueur.length];
        scoreJoueur = new JLabel[joueur.length];

        for(int i = 0; i < joueur.length; i++) {
            JPanel pan = new JPanel(new BorderLayout());
            JLabel jlab = new JLabel();
            try {
                jlab.setIcon(new ImageIcon(ImageIO.read(new File("src/Images/imgJoueur" + (i+1) +".png"))));
            } catch (IOException e) {
                System.out.println("Chemin de l'image incorrect");
            }
            pan.add(jlab, BorderLayout.NORTH);
            scoreJoueur[i] = new JLabel(joueur[i].getNom() + " : " + joueur[i].getScore());
            pan.add(scoreJoueur[i], BorderLayout.CENTER);
            panCoups[i] = new JPanel(new GridLayout(2,10));
            for(int j = 0; j < joueur[i].getNbTwistLock(); j++) {
                try {
                    panCoups[i].add(new JLabel(new ImageIcon(ImageIO.read(new File("src/Images/imgPoint_" + (i+1) +".png")))));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            pan.add(panCoups[i], BorderLayout.SOUTH);
            pan.setBorder(BorderFactory.createLoweredBevelBorder());
            add(pan);
        }

        add(grille);

        this.setVisible(true);
        this.pack();
    }

    public void actualiserScore() {
        for(int i = 0; i < joueur.length; i++) {
            scoreJoueur[i].setText(joueur[i].getNom() + " , " + joueur[i].getScore());
            panCoups[i].removeAll();
            for(int j = 0; j < joueur[i].getNbTwistLock(); j++) {
                try {
                    panCoups[i].add(new JLabel(new ImageIcon(ImageIO.read(new File("src/Images/imgPoint_" + (i+1) +".png")))));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //t
    public void colorerRond()
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