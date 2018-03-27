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
        this.setLayout(new GridLayout(3,2));
        alCoinButton.add(new CoinButton("BD"));
        alCoinButton.add(new CoinButton("BG"));
        alCoinButton.add(new CoinButton("HD"));
        alCoinButton.add(new CoinButton("HG"));
        for ( int i = 0; i < 2; i++ ) {
            this.add(alCoinButton.get(i));
            alCoinButton.get(i).addActionListener(this);
        }

        float[] hsb = new float[3];
        Color.RGBtoHSB(211,211,211,hsb);

        JLabel vide = new JLabel("");
        vide.setBackground(Color.getHSBColor(hsb[0],hsb[1],hsb[2]));
        vide.setOpaque(true);

        this.add(vide);

        JLabel nb = new JLabel("51");
        nb.setBackground(Color.getHSBColor(hsb[0],hsb[1],hsb[2]));
        nb.setOpaque(true);

        this.add(nb);

        for ( int i = 2; i < 4; i++ ) {
            this.add(alCoinButton.get(i));
            alCoinButton.get(i).addActionListener(this);
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
        if (source instanceof CoinButton)
        {
            ((CoinButton)source).setColor("red");
        }
        Tablier.colorerRond();
    }
}
