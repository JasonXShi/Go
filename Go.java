
/*	Game of Go
 * 	By: Alec Xia and Jason Shi
 * 	6/16/2017
 *	Multiplier game designed to capture
 *	and take over territories
 *	Two Players
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.*;
import javax.swing.plaf.synth.SynthSeparatorUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class Go implements MouseListener, ActionListener {
	int tableSize = 20; // size 19 x 19 (plus border on the side)
	boolean[][] squares = new boolean[tableSize][tableSize]; // each array is
																// table size x
																// table size
	boolean[][] nextsquares = new boolean[tableSize][tableSize];
	int neighbors = 0; // number of neighbors next to a certain piece
	JFrame frame = new JFrame("Go");
	GoPanel panel = new GoPanel(squares); // grid in middle
	Container north = new Container(); // space on side
	Container east = new Container(); // space on side
	Container south = new Container(); // space on side
	Container west = new Container(); // space on side
	JButton back = new JButton("Take Back");
	JButton pass = new JButton("Pass Turn");
	JLabel turn = new JLabel("                                 Turn: Black");
	boolean running = false;
	final static int BLACK_MOVE = 0; // black to move
	final static int WHITE_MOVE = 1; // white to move
	static int moving = BLACK_MOVE; // starts with black
	static boolean moved = false; // boolean flips the turn
	Piece first = null;
	ArrayList<ArrayList<Piece>> completed = new ArrayList<ArrayList<Piece>>(); // completed
																				// game
																				// board
																				// array
																				// list
	int blacknum; // black territory
	int whitenum; // white territory
	int gridcount = 361; // total spots on board (19x19)
	int doublepass; // count back-to-back passes

	public Go() { // create interface
		frame.setSize(900, 900); // appropriate size for game board
		frame.setResizable(true);
		frame.setLayout(new BorderLayout());
		frame.add(panel, BorderLayout.CENTER);
		panel.addMouseListener(this);
		// south container
		south.setLayout((new GridLayout(1, 3)));
		south.add(back);
		back.setEnabled(false);
		back.setBackground(new Color(230, 230, 230));
		back.addActionListener(this);
		south.add(pass);
		pass.setBackground(new Color(230, 230, 230));
		pass.addActionListener(this);
		south.add(turn);
		frame.add(south, BorderLayout.SOUTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setResizable(false); // resizing the board messes with piece
									// placement
	}

	public static void main(String[] args) {
		new Go();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(back)) {
			ArrayList<Piece> temp = panel.getPieceList(); // store the previous
															// pieces into an
															// array
			temp.remove(temp.size() - 1); // remove the last index (-1 because
											// index starts from 0)
			panel.updateAdjacency();
			if (temp.size() == 0) {
				back.setEnabled(false); // do not allow player to take back if
										// no pieces are played
			}
			// panel.setPieceList(temp); //set the pieces to the previous temp
			// state
			moved = !moved; // change the turn back to other player
			if (moved == true) {
				turn.setText("                                 Turn: White"); // show
																				// player
																				// turn
			}
			if (moved == false) {
				turn.setText("                                 Turn: Black"); // show
																				// player
																				// turn
			}
			panel.repaint();
		}
		if (e.getSource().equals(pass)) {
			moved = !moved; // change the turn to other player
			if (moved == true) {
				turn.setText("                                 Turn: White"); // show
																				// player
																				// turn
			}
			if (moved == false) {
				turn.setText("                                 Turn: Black"); // show
																				// player
																				// turn
			}
			doublepass++; // add pass to counter (mouse released will reset
							// counter)
			if (doublepass == 2) { // check if players have passed back-to-back
				doublepass = 0;
				checkWin(); // check win on two passes (if both players pass,
							// game ends)
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// System.out.println(e.getX() + " , " + e.getY()); //was used to debug
		double width = (double) panel.getWidth() / squares[0].length; // width
																		// of
																		// lines
		double height = (double) panel.getHeight() / squares[0].length; // height
																		// of
																		// lines

		int column = Math.min(24, (int) Math.round(e.getX() / width)); // find
																		// column
																		// that
																		// is
																		// closest
																		// to
																		// mouse
		int row = Math.min(24, (int) Math.round(e.getY() / height)); // find row
																		// that
																		// is
																		// closest
																		// to
																		// mouse
		if (column == 0 || row == 0 || column == 20 || row == 20) { // disable
																	// the
																	// columns
																	// and rows
																	// on the
																	// sides
																	// that make
																	// the
																	// border
			return;
		}

		boolean valid = true;
		for (int i = 0; i < panel.getPieceList().size(); i++) { // restrict
																// playing on
																// other pieces
			if (panel.getPieceList().get(i).getX() == (int) (column * width)
					&& panel.getPieceList().get(i).getY() == (int) (row * height)) {
				valid = false;
				break;
			}
		}
		if (valid) {
			moved = !moved; // change turn to other player
			if (moved == true) { // if black to move...
				turn.setText("                                 Turn: White"); // set
																				// label
																				// to
																				// white
				panel.addPiece((int) (column * width), (int) (row * height), "black"); // add
																						// black
																						// piece

			} else if (moved == false) { // if white to move...
				turn.setText("                                 Turn: Black"); // set
																				// label
																				// to
																				// black
				panel.addPiece((int) (column * width), (int) (row * height), "white"); // add
																						// white
																						// piece
			}
			back.setEnabled(true); // allow re-takes
			doublepass = 0; // reset pass counter
		} else {
			// invalid piece
		}
		frame.repaint();
		if (panel.pieceList.size() == gridcount) { // if all spots are filled,
													// check who wins
			checkWin();
		}
	}

	private void checkWin() { // this algorithm checks who has more territory
								// and displays the result
		for (int a = 0; a < panel.adjacency.length; a++) {
			for (int b = 0; b < panel.adjacency.length; b++) {
				if(panel.adjacency[a][b].equals("white")){
					whitenum++;
				}else if(panel.adjacency[a][b].equals("black")){
					blacknum++;
				}
			}
		}
		System.out.println("black" + blacknum);
		System.out.println("white" + whitenum); // used for debugging
		if (blacknum > whitenum) { // if black has more land...
			JOptionPane.showMessageDialog(frame, "Black Wins!");
		}
		if (whitenum > blacknum) { // if white has more land...
			JOptionPane.showMessageDialog(frame, "White Wins!");
		}
	}
}
