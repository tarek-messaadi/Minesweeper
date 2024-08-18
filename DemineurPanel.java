import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class DemineurPanel extends JPanel {
	Demineur demineur;
	int width;
	int heigth;

	public DemineurPanel(Demineur demineur) {
		this.demineur = demineur;
		this.width = 800;
		this.heigth = 700;
		setBounds(100, 0, 800, 700);
	}
	

	@Override
	public void paint(Graphics g) {
		super.paintComponent(g);
		paintDemineur(g);
	}

	private void paintDemineur(Graphics g) {
		for (int ligne = 0; ligne < demineur.nbLignes; ligne++)
			for (int colonne = 0; colonne < demineur.nbColonnes; colonne++)
				paintCellule(g, ligne, colonne);
	}
    
	public void paintCellule(Graphics g, int ligne, int colonne) {
		int hCellule = getHeight()  / demineur.getNbLignes();
		int wCellule = getWidth() / demineur.getNbColonnes();
		int x = colonne * wCellule;
		int y = ligne * hCellule;

		
		if (demineur.visible[ligne][colonne]) {
			if (demineur.bombes[ligne][colonne]) 
				paintCelluleBomb(g, x, y, wCellule, hCellule, new Color(238, 238, 238));
			else 
				paintCelluleNombre(g, x, y, wCellule, hCellule, ligne, colonne);
			
		} else 
			 paintRectangle(g, x, y, wCellule, hCellule, new Color(210, 210, 210)); 
		
		if (demineur.identify[ligne][colonne])
			paintCelluleIdentify(g, x, y, wCellule, hCellule);
		
		if (demineur.repaintFalse[ligne][colonne]) 
			paintCelluleIdentifyFalse(g, x, y, wCellule, hCellule); 
		
		if (demineur.bombCellule[ligne][colonne])
			paintCelluleBomb(g, x, y, wCellule, hCellule, Color.red);

		
	}

	
	private void paintCelluleBomb(Graphics g, int x, int y, int wCellule, int hCellule, Color color) {
		celluleIcon(g, x, y, wCellule, hCellule, "resources/bomb.png", color);
	}
	
	private void paintCelluleIdentify(Graphics g, int x, int y, int wCellule, int hCellule) {
		celluleIcon(g, x, y, wCellule, hCellule, "resources/identify.png", new Color(238, 238, 238));
	}
	
	private void paintCelluleIdentifyFalse(Graphics g, int x, int y, int wCellule, int hCellule) {
		celluleIcon(g, x, y, wCellule, hCellule, "resources/notBomb.png", new Color(238, 238, 238));
	}
	
    private void celluleIcon(Graphics g, int x, int y, int wCellule, int hCellule, String path, Color color) {
		paintRectangle(g, x, y, wCellule, hCellule, color);
		ImageIcon icon = new ImageIcon( path);
        icon = new ImageIcon(icon.getImage().getScaledInstance(wCellule, hCellule, y));
		icon.paintIcon(this, g, x, y);
    }
	
	private void paintCelluleNombre(Graphics g, int x, int y, int wCellule, int hCellule, int ligne, int colonne) {
		 Position positionCellule = new Position(ligne, colonne);
		 paintRectangle(g, x, y, wCellule, hCellule, new Color(238, 238, 238)); 
		 String text = demineur.nbrBombes(positionCellule)==0?"":String.valueOf(demineur.nbrBombes(positionCellule));
		 g.setColor(numberColor(demineur.nbrBombes(positionCellule)));
		 printTextCellule(g, x, y, wCellule, hCellule, text);
	}
	
	private Color numberColor(int number) {
		if (number == 1)
			return Color.BLUE;
		else if (number == 2)
			return Color.GREEN;
		else 
			return Color.RED;
	}
	
	public void paintRectangle(Graphics g, int x, int y, int wCellule, int hCellule, Color color) {
		g.setColor(color);
		g.fillRect(x, y, wCellule, hCellule);
		g.setColor(Color.gray);
		g.drawRect(x, y, wCellule, hCellule);
	}
	
	public void printTextCellule(Graphics g, int x, int y, int wCellule, int hCellule, String text) {
		 Font originalFont = g.getFont();
	     Font largerFont = originalFont.deriveFont(60f); 
	     g.setFont(largerFont);
	     FontMetrics fm = g.getFontMetrics();
	     int textWidth = fm.stringWidth(text);
	     int textHeight = fm.getAscent(); 

	     int positionX = x + (wCellule - textWidth) / 2;
	     int positionY = y + (hCellule - textHeight) / 2 + fm.getAscent() - hCellule/8 ;
         g.drawString(text, positionX, positionY);
	}


	public void setDemineur(Demineur demineur) {
              this.demineur = demineur;	
	}

}
