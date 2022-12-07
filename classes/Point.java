package classes;

public class Point {
	private Float x;
	private Float y;
	
	public Point(Float x, Float y) {
		super();
		this.x = x;
		this.y = y;
	}

	public Float getX() {
		return x;
	}

	public Float getY() {
		return y;
	}
	
	

	public void setX(Float x) {
		this.x = x;
	}

	public void setY(Float y) {
		this.y = y;
	}

	@Override
	public String toString() {
		return "(" + x + "," + y + ")";
	}
	
	
}
