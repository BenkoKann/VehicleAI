package steeringAI;

public class Vector {
	
	public double x;
	public double y;
	
	public Vector() {
		this(0, 0);
	}
	
	public Vector(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public void limit(double max) {
		if(this.getMagnitude() > max) {
			this.setMagnitude(max);
		}
	}
	
	public double getDirection() {
		return Math.atan2(this.y, this.x);
	}
	
	public void setDirection(double angle) {
		double magnitude = this.getMagnitude();
		this.x = Math.cos(angle) * magnitude;
		this.y = Math.sin(angle) * magnitude;
	}
	

	
	public Vector add(Vector v2) {
		return new Vector(this.x + v2.x, this.y + v2.y);
	}
	
	public void addTo(Vector v2) {
		this.x += v2.x;
		this.y += v2.y;
	}
	
	public Vector subtract(Vector v2) {
		return new Vector(this.x - v2.x, this.y - v2.y);
	}
	
	public void subtractFrom(Vector v2) {
		this.x -= v2.x;
		this.y -= v2.y;
	}
	
	public Vector multiply(double scalar) {
		return new Vector(this.x * scalar, this.y * scalar);
	}
	
	public void multiplyBy(double scalar) {
		this.x *= scalar;
		this.y *= scalar;
	}
	
	public Vector divide(double scalar) {
		return new Vector(this.x / scalar, this.y / scalar);
	}
	
	public void divideBy(double scalar) {
		this.x /= scalar;
		this.y /= scalar;
	}
	
	public Vector normalize() {
		return new Vector(this.x / this.getMagnitude(), this.y / this.getMagnitude());
	}
	
	public void normalizeBy() {
		this.x /= this.getMagnitude();
		this.y /= this.getMagnitude();
	}
	
	public double getMagnitude() {
		return Math.sqrt(this.x * this.x + this.y * this.y);
	}
	
	public void setMagnitude(double magnitude) {
		double direction = this.getDirection();
		this.x = Math.cos(direction) * magnitude;
		this.y = Math.sin(direction) * magnitude;
	}
	
	public Vector copy() {
		return new Vector(this.x, this.y);
	}
	
	public String toString() {
		return "x: " + this.x + "y: " + this.y;
	}
	
	
	
	
}
