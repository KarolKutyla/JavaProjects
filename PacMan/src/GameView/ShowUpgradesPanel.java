package GameView;

import javax.swing.*;
import java.awt.*;

public class ShowUpgradesPanel extends JPanel {

    final static String path = "Resources/Graphics/Frames/";
    ImageIcon icon;
    int maxNumber;

    public ShowUpgradesPanel(int maxNumber, int current, String fileName)
    {
        this.setBackground(Color.YELLOW);
        this.maxNumber = maxNumber;
        icon = new ImageIcon(path+fileName+".png");
        this.setLayout(new GridLayout(1,maxNumber));
        for(int i = 0; i < current; i++)
        {
            this.add(new JLabel(icon));
        }
    }

    public void checkGraphics(int current)
    {
        if(this.getComponentCount() > current)
        {
            this.remove(getComponentCount()-1);
        }else if(this.getComponentCount() < current)
        {
            while (this.getComponentCount() < current)
            {
                this.add(new JLabel(icon));
            }
        }
        repaint();
    }
}
