package Model;

import javax.swing.*;
import java.awt.*;

public class ContainerJPanel extends JPanel {
    private CoinButton cb1 = new CoinButton("BD");
    private CoinButton cb2 = new CoinButton("BG");
    private CoinButton cb3 = new CoinButton("HD");
    private CoinButton cb4 = new CoinButton("HG");
    public ContainerJPanel()
    {
        this.setLayout(new GridLayout(2,2));
        this.add(cb1);
        this.add(cb2);
        this.add(cb3);
        this.add(cb4);
        this.setVisible(true);
    }
}
