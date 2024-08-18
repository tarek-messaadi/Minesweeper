import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InterfacePanel extends JPanel {
    public Demineur demineur;
    public JButton button;

    public InterfacePanel(Demineur demineur) {
        this.demineur = demineur;
        this.button = new JButton();
        add(button);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintInterface(g);

    }

    private void paintInterface(Graphics g) {
        buttonIcon(g);
        g.setColor(Color.black);
        g.fillRect(50, getHeight() / 2 - 25, 100, 50);
        g.fillRect(getWidth() - 50 - 100, getHeight() / 2 - 25, 100, 50);
        
        if (demineur.time/10 == 0)
          printTextCellule(g, getWidth() - 50 - 100, getHeight() / 2 - 25, 100, 50, "00" + String.valueOf(demineur.time));
        else if (demineur.time/100 == 0)
            printTextCellule(g, getWidth() - 50 - 100, getHeight() / 2 - 25, 100, 50, "0" + String.valueOf(demineur.time));
        else
            printTextCellule(g, getWidth() - 50 - 100, getHeight() / 2 - 25, 100, 50, "" + String.valueOf(demineur.time));

        int x = getHeight() / 2 - 25;
        if (demineur.nbrBombes / 10 == 0 && demineur.nbrBombes > 0)
            printTextCellule(g, 50, x, 100, 50, "0" + String.valueOf(demineur.nbrBombes));
        else
            printTextCellule(g, 50, x, 100, 50, String.valueOf(demineur.nbrBombes));
    }
  
    private void printTextCellule(Graphics g, int x, int y, int wCellule, int hCellule, String text) {
        Font originalFont = g.getFont();
        Font largerFont = originalFont.deriveFont(50f);
        g.setFont(largerFont);
        FontMetrics fm = g.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        int textHeight = fm.getAscent();
        g.setColor(Color.red);
        int positionX = x + (wCellule - textWidth) / 2;
        int positionY = y + (hCellule - textHeight) / 2 + fm.getAscent() - hCellule / 8;
        g.drawString(text, positionX, positionY);
    }

    private void buttonIcon(Graphics g) {
        button.setBackground(Color.GRAY);
        button.setPreferredSize(new Dimension(80, 80));
        if (demineur.stop) 
        	buttonIcon("resources/smilysad.png");
        else 
        	buttonIcon("resources/smily.png");
    
        if (demineur.win) 
        	buttonIcon("resources/good.png");
        
    }
    
    private void buttonIcon(String path) {
        ImageIcon icon = new ImageIcon(path);
        icon = new ImageIcon(icon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH));
        button.setIcon(icon);
    }
    
    public void setDemineur(Demineur demineur) {
        this.demineur = demineur;
    }
}
