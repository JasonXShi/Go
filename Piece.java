public class Piece {
	int x;
	int y;
	String type;
	

	public Piece(int newx, int newy, String newType) { //each piece has 3 parameters, its coordinates and color
		x = newx;
		y = newy;
		type = newType;
	}
	//getters and setters for x, y, and type
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
	public String getType(){
		return type;
	}
	public void setType(String newType){
		this.type = newType;
	}

}

