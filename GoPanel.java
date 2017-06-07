import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.*;
public class GoPanel extends JPanel{
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
		for (double x = 0.5; x < squares[0].length+1.5; x++) {
			g.drawLine ((int)Math.round(x*width), (int)width-20,(int)Math.round((x)*width),(int)Math.round(this.getHeight()-(height/2)));
		}
		for (double y = 0.5; y < squares[0].length+1.5; y++) {
			g.drawLine ((int)Math.round(height)-16, (int)Math.round((y)*height), (int)Math.round(this.getWidth()-(width/2)), (int)Math.round((y)*height));
		}
	}
	
	public void setsquares(boolean[][] nextsquares) {
		
	}
}
