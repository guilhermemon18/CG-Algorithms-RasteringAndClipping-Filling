package classes;

public class Edge implements Comparable<Edge>{

	private Point sourcePoint;
	private Point destinationPoint;
	private float m;
	public Edge(Point origem, Point destino) {
		super();
		this.sourcePoint = origem;
		this.destinationPoint = destino;
		this.m = (float) (this.destinationPoint.getX() - this.sourcePoint.getX()) 
				/ (this.destinationPoint.getY() - this.sourcePoint.getY());
	}

	public Point getSourcePoint() {
		return sourcePoint;
	}

	public Point getDestinationPoint() {
		return destinationPoint;
	}

	@Override//As arestas devem estar ordenadas em ordem crescente  da coordenada x  de 
	//sua extremidade inferior (xmin) = this.sourcePoint.getX(). igual ao x de origem.
	public int compareTo(Edge o) {
		// TODO Auto-generated method stub
//		if(this.sourcePoint.getX() < o.sourcePoint.getX())
//			return -1;
//		else if(this.sourcePoint.getX() > o.sourcePoint.getX())
//			return 1;
//		return 0;
		return this.sourcePoint.getX().compareTo(o.sourcePoint.getX());
	}
	

	public Float getM() {
		return m;
//		return (float) (this.destinationPoint.getX() - this.sourcePoint.getX()) 
//				/ (this.destinationPoint.getY() - this.sourcePoint.getY());
	}
	
	public void updateX() {
		this.sourcePoint.setX((this.sourcePoint.getX() + this.getM()));
		//this.sourcePoint.setX(Math.round(this.sourcePoint.getX() + this.getM()));
		
		//this.destinationPoint.setX(Math.round(this.sourcePoint.getX() + this.getM()));
	}

	@Override
	public String toString() {
		return sourcePoint + "--" + destinationPoint +  " mValue: " + getM();
	}



}
