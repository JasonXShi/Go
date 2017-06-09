public class Piece {
	int x;
	int y;
	String label;
	boolean highlighted;
	public Piece(int newx, int newy){
		x = newx;
		y = newy;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
}
