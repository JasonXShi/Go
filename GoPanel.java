import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.*;

public class GoPanel extends JPanel {
	ArrayList<Piece> pieceList = new ArrayList<Piece>(); //array list that stores all the pieces
	ArrayList<ArrayList<Boolean>> adjacency = new ArrayList<ArrayList<Boolean>>(); //array that checks for neighbors

	boolean[][] squares; //squares in the grid
	double width; //width of each square
	double height; //height of each square

	public GoPanel(boolean[][] in) {
		squares = in;
	}

	public void paintComponent(Graphics g) { // paint square
		super.paintComponent(g);
		// draw background
		setBackground(new Color(255, 228, 196));
		width = (double) this.getWidth() / squares[0].length;
		height = (double) this.getHeight() / squares.length;
		// draw lines
		g.setColor(Color.BLACK);
		for (double x = 1; x < squares[0].length; x++) { //starts at 1 to create left border
			g.drawLine((int) Math.round(x * width), (int) width - 2, (int) Math.round((x) * width), 
					(int) Math.round(this.getHeight() - (height))); //take out 1 height to create right border
		}
		for (double y = 1; y < squares[0].length; y++) { //starts at 1 to create top border
			g.drawLine((int) Math.round(height) + 4, (int) Math.round((y) * height),
					(int) Math.round(this.getWidth() - (width)), (int) Math.round((y) * height)); //take out 1 width to create bottom border
		}
		// draw piece with black outline and fill with respective color
		for (int a = 0; a < pieceList.size(); a++) {
			g.setColor(Color.BLACK);
			g.drawOval(pieceList.get(a).getX() - 15, pieceList.get(a).getY() - 15, 30, 30);
			if (pieceList.get(a).getType().equals("white")) { //white type pieces color white
				g.setColor(Color.WHITE);
				g.fillOval(pieceList.get(a).getX() - 14, pieceList.get(a).getY() - 14, 28, 28);
			} else { //black type pieces color black
				g.fillOval(pieceList.get(a).getX() - 15, pieceList.get(a).getY() - 15, 30, 30);
			}
		}
	}

	public void addPiece(int newx, int newy, String newType) {
		//add piece
		pieceList.add(new Piece(newx, newy, newType)); //add to list
		adjacency.add(new ArrayList<Boolean>()); //add to adjacency
		for (int a = 0; a < adjacency.size(); a++) {
			adjacency.get(a).add(false);
		}
		for (int a = 0; a < adjacency.size(); a++) {
			adjacency.get(adjacency.size() - 1).add(false);
		}
	}
	//getters and setters for piece list
	public void setPieceList(ArrayList<Piece> pieceList) {
		this.pieceList = pieceList;
	}
	public ArrayList<Piece> getPieceList() {
		return pieceList;
	}
}
