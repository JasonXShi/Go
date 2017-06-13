import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.*;

public class GoPanel extends JPanel {
	ArrayList<Piece> pieceList = new ArrayList<Piece>();
	ArrayList<ArrayList<Boolean>> adjacency = new ArrayList<ArrayList<Boolean>>();

	boolean[][] squares;
	boolean[][] invis;
	double width;
	double height;

	public GoPanel(boolean[][] in) {
		squares = in;
		invis = in;
	}

	public void paintComponent(Graphics g) { // paint square
		super.paintComponent(g);
		// draw background
		setBackground(new Color(255, 228, 196));
		width = (double) this.getWidth() / squares[0].length;
		height = (double) this.getHeight() / squares.length;
		// draw lines
		g.setColor(Color.BLACK);
		for (double x = 1; x < squares[0].length; x++) {
			g.drawLine((int) Math.round(x * width), (int) width - 2, (int) Math.round((x) * width),
					(int) Math.round(this.getHeight() - (height)));
		}
		for (double y = 1; y < squares[0].length; y++) {
			g.drawLine((int) Math.round(height) + 4, (int) Math.round((y) * height),
					(int) Math.round(this.getWidth() - (width)), (int) Math.round((y) * height));
		}
		// draw piece with black outline and fill with respective color
		for (int a = 0; a < pieceList.size(); a++) {
			g.setColor(Color.BLACK);
			g.drawOval(pieceList.get(a).getX() - 15, pieceList.get(a).getY() - 15, 30, 30);
			if (pieceList.get(a).getType().equals("white")) {
				g.setColor(Color.WHITE);
				g.fillOval(pieceList.get(a).getX() - 14, pieceList.get(a).getY() - 14, 28, 28);
			} else {
				g.fillOval(pieceList.get(a).getX() - 15, pieceList.get(a).getY() - 15, 30, 30);
			}
		}
	}

	public void addPiece(int newx, int newy, String newType) {
		//add piece
		pieceList.add(new Piece(newx, newy, newType));
		adjacency.add(new ArrayList<Boolean>());
		for (int a = 0; a < adjacency.size(); a++) {
			adjacency.get(a).add(false);
		}
		for (int a = 0; a < adjacency.size(); a++) {
			adjacency.get(adjacency.size() - 1).add(false);
		}
	}
	public void setPieceList(ArrayList<Piece> pieceList) {
		this.pieceList = pieceList;
	}
	public ArrayList<Piece> getPieceList() {
		return pieceList;
	}
}
