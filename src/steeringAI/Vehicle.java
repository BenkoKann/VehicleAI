package steeringAI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

import javax.swing.JPanel;

public class Vehicle extends JPanel{
	
	private Vector acceleration;
	private Vector velocity;
	private Vector position;
	
	private int r;
	private double maxSpeed;
	private double maxForce;
	
	public Vehicle(double x, double y) {
		this.acceleration = new Vector();
		this.velocity = new Vector(0, -2);
		this.position = new Vector(x, y);
		
		this.r = 6;
		this.maxSpeed = 8;
		this.maxForce = 0.2;
	}
	
	public void update() {
		this.velocity.addTo(this.acceleration);
		this.velocity.limit(this.maxSpeed);
		this.position.addTo(this.velocity);
		this.acceleration.multiplyBy(0); // reset acceleration each cycle
	}
	
	public void applyForce(Vector force) {
		// could add A = F / M if wanted mass
		this.acceleration.addTo(force);
	}
	
	public void seek(Vector target) {
		Vector desired = target.subtract(this.position);
		desired.setMagnitude(this.maxSpeed);
		
		// STEER = desired = - velocity
		Vector steer = desired.subtract(this.velocity);
		steer.limit(this.maxForce);
		
		this.applyForce(steer);
	}
	
	public void paintComponent(Graphics g) {
		g.fillRect((int) this.position.x, (int) this.position.y, 6, 6);
		g.setColor(Color.decode("#7F7F7F"));
		Graphics2D g2d = (Graphics2D) g;
		g2d.translate(this.position.x, this.position.y);
		double theta = this.velocity.getDirection() + Math.PI/2;
		g2d.rotate(theta);
		
		Point p1 = new Point(0, -this.r*2);
		Point p2 = new Point(-this.r, this.r*2);
		Point p3 = new Point(this.r, this.r*2);
		
		g2d.fillPolygon(new int[] {p1.x, p2.x, p3.x}, new int[] {p1.y, p2.y, p3.y}, 3);
		
		g.setColor(Color.white);
		g2d.drawPolygon(new int[] {p1.x, p2.x, p3.x}, new int[] {p1.y, p2.y, p3.y}, 3);
		
	
		
	//	g2d.rotate(theta);
		
		
		
	}
	

}
