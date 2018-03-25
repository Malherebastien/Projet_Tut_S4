package View;

import Model.CoinButton;
import Model.ContainerJPanel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Tablier extends JFrame {
    public Tablier() {
        this.setTitle("Bouton");
        this.setSize(300, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setLayout(new GridLayout(10,7));

        for ( int i = 0 ; i < 70 ; i++ )
            this.getContentPane().add(new ContainerJPanel());

        this.setVisible(true);
        this.pack();
    }

    public static void main(String args[]) {
        new Tablier();
    }
}