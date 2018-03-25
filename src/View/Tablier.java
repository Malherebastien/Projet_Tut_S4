package View;

import Model.CoinButton;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Tablier extends JFrame {
    private CoinButton cb1 = new CoinButton("BD");
    private CoinButton cb2 = new CoinButton("BG");
    private CoinButton cb3 = new CoinButton("HD");
    private CoinButton cb4 = new CoinButton("HG");
    public Tablier() {
        this.setTitle("Bouton");
        this.setSize(300, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        //On définit le layout à utiliser sur le content pane
        //Trois lignes sur deux colonnes
        this.setLayout(new GridLayout(2, 2));

        System.out.println("Working Directory = " +
                System.getProperty("user.dir"));
        //On ajoute le bouton au content pane de la JFrame
        this.getContentPane().add(cb1);
        this.getContentPane().add(cb2);
        this.getContentPane().add(cb3);
        this.getContentPane().add(cb4);
        this.setVisible(true);
        this.pack();
    }

    public static void main(String args[]) {
        new Tablier();
    }
}