import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.sound.sampled.Clip;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.swing.JFrame;
import javax.swing.Timer;

public class DemineurFrame extends JFrame {
	DemineurPanel demineurPanel;
	DemineurPanel demineurPnelTop;
	InterfacePanel interfacePanel;
	Demineur demineur;
	Timer timer;
	MouseAdapter mouseAdapter;

	public DemineurFrame() {
		setTitle("Demineur CCI 2024");
		setSize(new Dimension(554, 677));
		initialiserComponents();
		initialiserTimer();
		initialiserMouseAdapter();
		restartGame();
		setResizable(false);
	}

	private void initialiserComponents() {
		this.demineur = new Demineur(10, 10, 10);
		this.demineurPanel = new DemineurPanel(demineur);
		this.interfacePanel = new InterfacePanel(demineur);
		interfacePanel.setPreferredSize(new Dimension(574, 100));
		demineurPanel.setPreferredSize(new Dimension(580, 580));
		add(interfacePanel, BorderLayout.NORTH);
		add(demineurPanel, BorderLayout.CENTER);

	}

	private void initialiserTimer() {
		timer = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (demineur.time == 999) {
					demineur.stop = true;
					timer.stop();
					removeMouseListener(mouseAdapter);
				}
				demineur.time++;
				interfacePanel.repaint();
			}
		});
	}

	private void initialiserMouseAdapter() {
		mouseAdapter = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				timer.start();
				int x = e.getX();
				int y = e.getY() - (getHeight() - demineurPanel.getHeight());
				int hCellule = demineurPanel.getHeight() / demineur.getNbLignes();
				int wCellule = demineurPanel.getWidth() / demineur.getNbColonnes();
				int ligne = y / hCellule;
				int colonne = x / wCellule;
				if (e.getButton() == MouseEvent.BUTTON3 && !demineur.identify[ligne][colonne]) {
					demineur.identify[ligne][colonne] = true;
					demineur.nbrBombes--;
					if (demineur.allBombsIdentified()) {
						demineur.win = true;
						timer.stop();
						demineur.showAllCellules();
						removeMouseListener(this);
					}
				} else {
					if (demineur.identify[ligne][colonne]) {
						demineur.identify[ligne][colonne] = false;
						demineur.nbrBombes++;
					}
					demineur.visualiserZeros(new Position(ligne, colonne));
					demineur.visible[ligne][colonne] = true;
					if (demineur.bombes[ligne][colonne]) {
						demineur.bombCellule[ligne][colonne] = true;
						loadAudio();
						timer.stop();
						demineur.stop = true;
						demineur.showAllBombs();
						demineur.repaintIdentifiedFalse();
						removeMouseListener(this);
					}
				}
				interfacePanel.repaint();
				demineurPanel.repaint();
			}
		};
		addMouseListener(mouseAdapter);
	}

	private void restartGame() {
		interfacePanel.button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				timer.stop();
				demineur = new Demineur(10, 10, 10);
				demineurPanel.setDemineur(demineur);
				interfacePanel.setDemineur(demineur);
				demineurPanel.repaint();
				interfacePanel.repaint();
				addMouseListener(mouseAdapter);
				revalidate();
				repaint();

			}
		});
	}
	
	private void loadAudio() {
		try {
			URL file = new URL("file:resources/audio.wav");
			AudioInputStream ais = AudioSystem.getAudioInputStream(file);
			Clip clip = AudioSystem.getClip();
			clip.open(ais);
			clip.start();
	} catch (Exception exception) {
		exception.printStackTrace();
	}
	}

}
