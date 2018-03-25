package View;

import javax.swing.*;

public class SelectionJoueur {
    public SelectionJoueur() {
    Object[] possibilities = {"2", "3", "4"};
    String s = (String) JOptionPane.showInputDialog(
            null,
            "Choisir le nom de joueurs",
            "Choix du nombre de joueurs",
            JOptionPane.PLAIN_MESSAGE,
            null,
            possibilities,
            "2");
    }
    public static void main(String args[]) {
        new SelectionJoueur();
    }
}