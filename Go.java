import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class Go implements MouseListener, ActionListener{
	int tableSize = 20; //size 19 x 19 (plus border)
	boolean[][] squares = new boolean[tableSize][tableSize]; //each array is table size x table size
	boolean [][] nextsquares = new boolean [tableSize][tableSize];
	int neighbors = 0;
	JFrame frame = new JFrame("Go");
	GoPanel panel = new GoPanel(squares); //grid in middle
	Container north = new Container(); //space on side
	Container east = new Container(); //space on side
	Container south = new Container(); //space on side
	Container west = new Container(); //space on side
	JButton back = new JButton("Take Back");
	JButton pass = new JButton("Pass Turn");
	JLabel turn = new JLabel("                                 Turn: Black");
	boolean running = false;
	final static int BLACK_MOVE = 0;
	final static int WHITE_MOVE = 1;
	static int moving = BLACK_MOVE;
	static boolean moved = false;
	Piece first = null;
	ArrayList<ArrayList<Piece>> completed = new ArrayList<ArrayList<Piece>>();
	public Go(){
		frame.setSize(800, 800);
		frame.setResizable(true);
		frame.setLayout(new BorderLayout());
		frame.add(panel, BorderLayout.CENTER);
		panel.addMouseListener(this);
		//south container
		south.setLayout((new GridLayout(1,3)));
		south.add(back);
		back.setBackground(new Color(230,230,230));
		back.addActionListener(this);
		south.add(pass);
		pass.setBackground(new Color(230,230,230));
		pass.addActionListener(this);
		south.add(turn);
		frame.add(south, BorderLayout.SOUTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setResizable(false);
	}
	public static void main(String[] args) {
		new Go();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(back)){
			//back();
		}
		if (e.getSource().equals(pass)){
			moved = !moved;
			if (moved == true){
				turn.setText("                                 Turn: White");
			}
			if(moved == false){
				turn.setText("                                 Turn: Black");
			}
		}
	}
	@Override
	public void mouseClicked(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {
		System.out.println(e.getX() + " , " + e.getY()); 
		double width = (double)panel.getWidth() / squares[0].length; //width and height of lines
		double height = (double)panel.getHeight() / squares[0].length; 
		
		int column = Math.min(24,(int)Math.round(e.getX() / width)); //number of columns and rows closest to click
		int row = Math.min(24, (int)Math.round(e.getY() / height));
		if (column == 0 ||  row == 0 || column == 20 ||  row == 20){
			return;
		}
		panel.addPiece((int)(column*width), (int)(row*height));
		
		moved = !moved;
		if (moved == true){
			turn.setText("                                 Turn: White");
		}
		if(moved == false){
			turn.setText("                                 Turn: Black");
		}
		frame.repaint();
	}

}
