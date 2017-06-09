import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.*;
public class GoPanel extends JPanel{
	ArrayList<Piece> pieceList = new ArrayList<Piece>();
	ArrayList<ArrayList<Boolean>> adjacency = new ArrayList<ArrayList<Boolean>>();
	boolean[][] squares;
	boolean[][] invis;
	double width;
	double height;
	public GoPanel(boolean[][] in){
		squares = in;
		invis = in;
	}
	public void paintComponent(Graphics g){ //paint square
		super.paintComponent(g);
		for (int a = 0; a < pieceList.size(); a++) {
			g.setColor(Color.BLACK);
			g.drawOval(pieceList.get(a).getX() - 15, pieceList.get(a).getY() - 15, 30, 30);
		}
		setBackground(new Color(255,228,196));
		width = (double)this.getWidth() / squares[0].length;
		height = (double)this.getHeight() / squares.length;
		if (Go.moved == true){
		g.setColor(Color.BLACK);
		}
		if (Go.moved == false){
			g.setColor(Color.WHITE);
		}
		for (int row = 0; row < squares.length; row++) {
			for (int column = 0; column < squares.length; column++) {
				if (squares[row][column] == true){
					g.fillRect( (int)(column*width), (int)(row*height),
					(int)width+1, (int)height+1);
				}
			}
		}
		g.setColor(Color.BLACK);
		for (double x = 1; x < squares[0].length; x++) {
			g.drawLine ((int)Math.round(x*width), (int)width-2,(int)Math.round((x)*width),(int)Math.round(this.getHeight()-(height)));
		}
		for (double y = 1; y < squares[0].length; y++) {
			g.drawLine ((int)Math.round(height)+4, (int)Math.round((y)*height), (int)Math.round(this.getWidth()-(width)), (int)Math.round((y)*height));
		}
	}
	public void addPiece(int newx, int newy) {
		pieceList.add(new Piece(newx, newy));
		adjacency.add(new ArrayList<Boolean>());
		for (int a = 0; a < adjacency.size(); a++) {
			adjacency.get(a).add(false);
		}
		for (int a = 0; a < adjacency.size(); a++) {
			adjacency.get(adjacency.size()-1).add(false);
		}
	}	
}
