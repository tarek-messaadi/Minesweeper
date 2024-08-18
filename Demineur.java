import java.util.ArrayList;
import java.util.Random;

public class Demineur {
	int nbLignes;
	int nbColonnes;
	boolean[][] bombes;
	boolean[][] visible;
	boolean[][] click;
	boolean[][] identify;
	boolean[][] repaintFalse;
	boolean[][] bombCellule;
	int nbrBombes;
	boolean stop;
	boolean win;
	int time;

	/**
	 * Constructeur de demineur
	 * 
	 * @param nbrLigne   nombre de ligne de demineur
	 * @param nbrColonne nombre de colonne de demineur
	 * @param nbrBombes  nombre des bombes
	 */
	public Demineur(int nbrLigne, int nbrColonne, int nbrBombes) {
		this.nbLignes = nbrLigne;
		this.nbColonnes = nbrColonne;
		this.nbrBombes = nbrBombes;
		identify = new boolean[nbLignes][nbColonnes];
		click = new boolean[nbLignes][nbColonnes];
		repaintFalse = new boolean[nbLignes][nbColonnes];
		bombCellule = new boolean[nbLignes][nbColonnes];
		initialiserBombes();
		initialiserVisible();
		this.stop = false;
		this.win = false;
		this.time = 0;

	}

	private void initialiserBombes() {
		bombes = new boolean[nbLignes][nbColonnes];
		Random random = new Random();
		int rndLigne = 0;
		int rndColonne = 0;
		// in case We have the same randoms !
		for (int nbr = 0; nbr < nbrBombes; nbr++) {
			do {
				rndLigne = random.nextInt(nbLignes);
				rndColonne = random.nextInt(nbColonnes);
			} while (bombes[rndLigne][rndColonne]);
			bombes[rndLigne][rndColonne] = true;
		}
	}

	private void initialiserVisible() {
		visible = new boolean[nbLignes][nbColonnes];
		for (int ligne = 0; ligne < nbLignes; ligne++)
			for (int colonne = 0; colonne < nbColonnes; colonne++)
				visible[ligne][colonne] = false;
	}

	void showAllBombs() {
		for (int ligne = 0; ligne < getNbLignes(); ligne++)
			for (int colonne = 0; colonne < getNbColonnes(); colonne++)
				if (bombes[ligne][colonne]) {
					visible[ligne][colonne] = true;
					identify[ligne][colonne] = false;
				}
	}

	void showAllCellules() {
		for (int ligne = 0; ligne < getNbLignes(); ligne++)
			for (int colonne = 0; colonne < getNbColonnes(); colonne++)
				visible[ligne][colonne] = true;
	}

	boolean allBombsIdentified() {
		for (int ligne = 0; ligne < getNbLignes(); ligne++)
			for (int colonne = 0; colonne < getNbColonnes(); colonne++)
				if (bombes[ligne][colonne] && !identify[ligne][colonne])
					return false;
		return true;
	}

	public void repaintIdentifiedFalse() {
		for (int ligne = 0; ligne < getNbLignes(); ligne++)
			for (int colonne = 0; colonne < getNbColonnes(); colonne++)
				if (identify[ligne][colonne] && !bombes[ligne][colonne]) {
					repaintFalse[ligne][colonne] = true;
					identify[ligne][colonne] = false;
				}
	}

	private void begin(Position position) {
		if (position.ligne == -1)
			position.ligne = 0;
		if (position.colonne == -1)
			position.colonne = 0;
	}

	private void end(Position position) {
		if (position.ligne == nbLignes)
			position.ligne = nbLignes - 1;
		if (position.colonne == nbColonnes)
			position.colonne = nbColonnes - 1;
	}

	private Position position0Autour(Position position) {
		Position depart = new Position(position.ligne - 1, position.colonne - 1);
		begin(depart);
		return depart;
	}

	private Position positionFinAutour(Position position) {
		Position end = new Position(position.ligne + 1, position.colonne + 1);
		end(end);
		return end;
	}

	private ArrayList<Position> positionsAutour(Position position) {
		ArrayList<Position> positions = new ArrayList<Position>();
		for (int ligne = position0Autour(position).ligne; ligne <= positionFinAutour(position).ligne; ligne++)
			for (int colonne = position0Autour(position).colonne; colonne <= positionFinAutour(
					position).colonne; colonne++)
				positions.add(new Position(ligne, colonne));
		return positions;
	}

	int nbrBombes(Position position) {
		int nbrBombes = 0;
		for (Position positionl : positionsAutour(position))
			if (bombes[positionl.ligne][positionl.colonne])
				nbrBombes += 1;
		return nbrBombes;
	}

	boolean visibleAutour(Position position) {
		for (Position positionl : positionsAutour(position))
			if (!visible[positionl.ligne][positionl.colonne])
				return false;
		return true;
	}

	public void visualiserZeros(Position position) {
		if (nbrBombes(position) == 0 && !click[position.ligne][position.colonne]) {
			click[position.ligne][position.colonne] = true;
			for (Position positionl : positionsAutour(position)) {
				visible[positionl.ligne][positionl.colonne] = true;
				visualiserZeros(new Position(positionl.ligne, positionl.colonne));
			}
		}
	}

	public int getNbColonnes() {
		return nbColonnes;
	}

	public int getNbLignes() {
		return nbLignes;
	}

}
