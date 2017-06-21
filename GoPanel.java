import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.text.StyledEditorKit.ForegroundAction;

public class GoPanel extends JPanel {
	ArrayList<Piece> pieceList = new ArrayList<Piece>(); // array list that
															// stores all the
															// pieces
	String[][] adjacency = new String[19][19]; // array that checks for
												// neighbors

	boolean[][] squares; // squares in the grid
	double width; // width of each square
	double height; // height of each square
	ArrayList<Piece> alreadyChecked = new ArrayList<Piece>();

	public GoPanel(boolean[][] in) {
		squares = in;
		for (int i = 0; i < adjacency.length; i++) {
			for (int a = 0; a < adjacency.length; a++) {
				adjacency[i][a] = "none";
			}
		}
	}

	public void paintComponent(Graphics g) { // paint square
		super.paintComponent(g);
		// draw background
		setBackground(new Color(255, 228, 196));
		width = (double) this.getWidth() / squares[0].length;
		height = (double) this.getHeight() / squares.length;
		// draw lines
		g.setColor(Color.BLACK);
		for (double x = 1; x < squares[0].length; x++) { // starts at 1 to
															// create left
															// border
			g.drawLine((int) Math.round(x * width), (int) width - 2, (int) Math.round((x) * width),
					(int) Math.round(this.getHeight() - (height))); // take out
																	// 1 height
																	// to create
																	// right
																	// border
		}
		for (double y = 1; y < squares[0].length; y++) { // starts at 1 to
															// create top border
			g.drawLine((int) Math.round(height) + 4, (int) Math.round((y) * height),
					(int) Math.round(this.getWidth() - (width)), (int) Math.round((y) * height)); // take
																									// out
																									// 1
																									// width
																									// to
																									// create
																									// bottom
																									// border
		}
		for (int a = 0; a < pieceList.size(); a++) {
			//if adjacency[][] is none then take it out from piecelist and repaint
			int col = Math.min(24, (int) Math.round(pieceList.get(a).getX() / width)); // mouse
			int r = Math.min(24, (int) Math.round(pieceList.get(a).getY() / height));
			if (adjacency[col - 1][r - 1].equals("none")) {

				pieceList.remove(a);

				System.out.println("remove a piece");
				repaint();
				break;
			}
			// draw piece with black outline and fill with respective color
			g.setColor(Color.BLACK);
			g.drawOval(pieceList.get(a).getX() - 15, pieceList.get(a).getY() - 15, 30, 30);
			if (pieceList.get(a).getType().equals("white")) { // white type
																// pieces color
																// white
				g.setColor(Color.WHITE);
				g.fillOval(pieceList.get(a).getX() - 14, pieceList.get(a).getY() - 14, 28, 28);
			} else { // black type pieces color black
				g.fillOval(pieceList.get(a).getX() - 15, pieceList.get(a).getY() - 15, 30, 30);
			}
		}

	}

	public void addPiece(int newx, int newy, String newType) {
		// add piece
		pieceList.add(new Piece(newx, newy, newType)); // add to list
		updateAdjacency();
		checkWin();
	}

	private void checkWin() {
		//check for capture on all the array
		for (int i = 0; i < adjacency.length; i++) {
			for (int j = 0; j < adjacency.length; j++) {
				if (adjacency[i][j].equals("black") || adjacency[i][j].equals("white")) {
					alreadyChecked.clear();
					checkCapture(i, j);
				}
			}
		}
	}

	private void checkCapture(int x, int y) {
		// intialize the arrayLists for the surrounding pieces of the same type,
		// and the pieces that need to be checked
		ArrayList<Piece> sameSurround = new ArrayList<Piece>();
		ArrayList<Piece> needCheck = new ArrayList<Piece>();
		int blankSurround = 0;
		//finds the things surrounding that are blank
		if (x > 0) {
			if (adjacency[x][y].equals(adjacency[x - 1][y])) {
				sameSurround.add(new Piece(x - 1, y, adjacency[x][y]));
			} else if (!adjacency[x - 1][y].equals("black") && !adjacency[x - 1][y].equals("white")) {
				blankSurround++;
			}
		}
		if (y > 0) {
			if (adjacency[x][y].equals(adjacency[x][y - 1])) {
				sameSurround.add(new Piece(x, y - 1, adjacency[x][y]));
			} else if (!adjacency[x][y - 1].equals("black") && !adjacency[x][y - 1].equals("white")) {
				blankSurround++;
			}
		}
		if (y < 18) {
			if (adjacency[x][y].equals(adjacency[x][y + 1])) {
				sameSurround.add(new Piece(x, y + 1, adjacency[x][y]));
			} else if (!adjacency[x][y + 1].equals("black") && !adjacency[x][y + 1].equals("white")) {
				blankSurround++;
			}
		}
		if (x < 18) {
			if (adjacency[x][y].equals(adjacency[x + 1][y])) {
				sameSurround.add(new Piece(x + 1, y, adjacency[x][y]));
			} else if (!adjacency[x + 1][y].equals("black") && !adjacency[x + 1][y].equals("white")) {
				blankSurround++;
			}
		}
		//if theres more than 0 blanks than it cannot be captured
		if (blankSurround > 0) {
			return;
		} else {
			// current piece to already checked arraylist, check same surround pieces if not already checked
			alreadyChecked.add(new Piece(x, y, adjacency[x][y]));
			int contains = 0;
			for (int a = 0; a < sameSurround.size(); a++) {
				for (int i = 0; i < alreadyChecked.size(); i++) {
					if (alreadyChecked.get(i).getX() == sameSurround.get(a).getX()
							&& alreadyChecked.get(i).getY() == sameSurround.get(a).getY()) {
						contains++;
					}

				}
				if (contains == 0) {

					needCheck.add(new Piece(sameSurround.get(a).getX(), sameSurround.get(a).getY(),
							sameSurround.get(a).getType()));
					checkCapture(sameSurround.get(a).getX(), sameSurround.get(a).getY());
					return;
				}
				contains = 0;
			}

			if (needCheck.isEmpty()) {
				//if captured then repaint and set adjacency[][] spots to "none" again
				System.out.println("Captured:");
				for (int i = 0; i < alreadyChecked.size(); i++) {
					System.out.println(alreadyChecked.get(i).getX() + " " + alreadyChecked.get(i).getY());
					adjacency[alreadyChecked.get(i).getX()][alreadyChecked.get(i).getY()] = "none";
				}
				repaint();
				return;
			}
		}

	}

	public void updateAdjacency() {
		// uses piecelist to update the adjacency matrix (misnomer, pretty much
		// the board)
		for (int i = 0; i < pieceList.size(); i++) {
			if (pieceList.get(i).getType().equals("black")) {
				adjacency[pieceList.get(i).getX() / 44 - 1][pieceList.get(i).getY() / 40 - 1] = "black";
			} else if (pieceList.get(i).getType().equals("white")) {
				adjacency[pieceList.get(i).getX() / 44 - 1][pieceList.get(i).getY() / 40 - 1] = "white";
			}
		}
	}

	// getters and setters for piece list
	public void setPieceList(ArrayList<Piece> pieceList) {
		this.pieceList = pieceList;
	}

	public ArrayList<Piece> getPieceList() {
		return pieceList;
	}
}
