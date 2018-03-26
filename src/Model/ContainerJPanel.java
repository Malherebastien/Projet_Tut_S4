package Model;

import View.Tablier;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ContainerJPanel extends JPanel implements ActionListener {
    private ArrayList <CoinButton> alCoinButton   = new ArrayList<CoinButton>();
    public ContainerJPanel()
    {
        this.setLayout(new GridLayout(2,2));
        alCoinButton.add(new CoinButton("BD"));
        alCoinButton.add(new CoinButton("BG"));
        alCoinButton.add(new CoinButton("HD"));
        alCoinButton.add(new CoinButton("HG"));
        for ( CoinButton cb : alCoinButton ) {
            this.add(cb);
            cb.addActionListener(this);
        }
        this.setBorder(BorderFactory.createEmptyBorder(1,1,1,1));
        this.setVisible(true);
    }
    public CoinButton getCoinButton( int id )
    {
        try
        {
            return this.alCoinButton.get(id-1);
        }
        catch (NullPointerException npe)
        {
            System.out.println("Le Coin Button demandé n'existe pas");
        }
        return null;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source instanceof CoinButton) {
            ((CoinButton)source).setColor("red");
        }
        Tablier.actualiserIHM();
    }
}
