package steeringAI_3;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.JPanel;

public class Vehicle extends JPanel{
	
	private Vector acceleration;
	private Vector velocity;
	private Vector position;
	
	private int r;
	private double maxSpeed;
	private double maxForce;
	
	
	private double[] dna = new double[4];
	
	private double health = 1;
	
	
	public Vehicle(double x, double y) {
		this.acceleration = new Vector();
		this.velocity = new Vector(0, -2);
		this.position = new Vector(x, y);
		
		this.r = 6;
		this.maxSpeed = 5;
		this.maxForce = .2;
		
		// Food weight
		this.dna[0] = Helper.random(-5, 5);
		// Poison weight
		this.dna[1] = Helper.random(-5, 5);
		// Food perception
		this.dna[2] = Helper.random(0, 100);
		// Poison perception
		this.dna[3] = Helper.random(0, 100);
		
//		
//		this.dna[0] = 5;
//		this.dna[1] = 0;
	}
	
	public void update() {
		this.health -= 0.01;
		
		this.velocity.addTo(this.acceleration);
		this.velocity.limit(this.maxSpeed);
		this.position.addTo(this.velocity);
		this.acceleration.multiplyBy(0); // reset acceleration each cycle
	}
	
	public void applyForce(Vector force) {
		// could add A = F / M if wanted mass
		this.acceleration.addTo(force);
	}
	
	public void boundaries() { // keep vehicle in screen
		Vector desired = null;
		int d = 25; // distance from edge which we keep vehicle in
		
		if(this.position.x < d) { // too far left
			System.out.println(1);
			desired = new Vector(this.maxSpeed, this.velocity.y); // keep y vel and max the x vel
		} else if(this.position.x > Sketch.WIDTH - d) {
			desired = new Vector(-this.maxSpeed, this.velocity.y);
		}
		
		if(this.position.y < d) {
			desired = new Vector(this.velocity.x, this.maxSpeed);
		} else if(this.position.y > Sketch.HEIGHT - d) {
			desired = new Vector(this.velocity.x, -this.maxSpeed);
		}
		
		if(desired != null) {
			desired.normalizeBy();
			desired.multiplyBy(this.maxSpeed);
			Vector steer = desired.subtract(this.velocity);
			steer.limit(maxForce);
			this.applyForce(steer);
		}
		
		
	}
	
	public void behaviors(ArrayList<Vector> good, ArrayList<Vector> bad) {
		Vector steerG = this.eat(good, 0.2, this.dna[2]);
		Vector steerB = this.eat(bad, -0.5, this.dna[3]);
		
		steerG.multiplyBy(this.dna[0]);
		steerB.multiplyBy(this.dna[1]);
		
		this.applyForce(steerG); // !!! THE STEER ISNT BEING LIMITED HERE after being multiplied
		this.applyForce(steerB); // !!! THE STEER ISNT BEING LIMITED HERE after being multiplied

	}
	
	public boolean dead() {
		return (this.health < 0);
	}
	
	public Vector eat(ArrayList<Vector> food, double nutrition, double perception) {
		double record = Double.MAX_VALUE;
		int closestIndex = -1; // index of food
		for(int i = 0; i < food.size(); i++) {
			double d = food.get(i).distance(this.position);
			if(d < record && d < perception) {
				record = d;
				closestIndex = i;
			}
			
		}
		
		// this is the momement of eating
		if(record < 8) {
			food.remove(closestIndex);
			this.health += nutrition;
		} else if(closestIndex >= 0) {
			return this.seek(food.get(closestIndex));
		}
		
		return new Vector(0, 0); // return 0 vector if no food
		
		
		
		
		
	}
	
	public Vector seek(Vector target) {
		Vector desired = target.subtract(this.position);
		desired.setMagnitude(this.maxSpeed);
		
		// STEER = desired = - velocity
		Vector steer = desired.subtract(this.velocity);
		steer.limit(this.maxForce);
		
		// this.applyForce(steer);
		return steer;
	}
	
	public void paintComponent(Graphics g) {
		
		Color gr = new Color(0, 255, 0);
		Color rd = new Color(255, 0, 0);
		Color col = Helper.lerpColor(gr, rd, health);
				
				
		g.setColor(col);
		//g.setColor(Color.decode("#7F7F7F"));
		Graphics2D g2d = (Graphics2D) g;
		g2d.translate(this.position.x, this.position.y);
		double theta = this.velocity.getDirection() + Math.PI/2;
		g2d.rotate(theta);
		
		Point p1 = new Point(0, -this.r*2);
		Point p2 = new Point(-this.r, this.r*2);
		Point p3 = new Point(this.r, this.r*2);
		
		// [START] VISUAL OF WEIGHTS
		Graphics g2 = g.create();
		Graphics2D g2d2 = (Graphics2D) g2;
		g2d2.setStroke(new BasicStroke(2));
		g2.setColor(Color.green);
		g2.drawOval(-(int)dna[2], -(int)dna[2], (int)dna[2]*2, (int)dna[2]*2); // food PERCEPTION
		g2d2.setStroke(new BasicStroke(3));
		g2.drawLine(0,0,0,(int)(-dna[0]*10));
		
		g2d2.setStroke(new BasicStroke(2));
		g2.setColor(Color.red);
		g2.drawLine(0,0,0,(int)(-dna[1]*10));
		
		g2.setColor(Color.red);
		g2.drawOval(-(int)dna[3], -(int)dna[3], (int)dna[3]*2, (int)dna[3]*2); // poison PERCEPTION // *2 because radius
		
		
		g2.dispose();
		// [END] VISUAL OF WEIGHTS
		
		g2d.fillPolygon(new int[] {p1.x, p2.x, p3.x}, new int[] {p1.y, p2.y, p3.y}, 3);
		
		// [START] VEHICLE OUTLINE
//		g.setColor(Color.white);
//		g2d.drawPolygon(new int[] {p1.x, p2.x, p3.x}, new int[] {p1.y, p2.y, p3.y}, 3);
		// [END] VEHICLE OUTLINE
		
		
		
		// [START] RESET STUFF
		g2d.rotate(-theta);		
		g2d.translate(-this.position.x, -this.position.y);
		// [END] RESET STUFF
		
		
//		g.setColor(Color.BLUE);
//		g.fillRect(-3, -3, 6, 6);
	
		
	//	g2d.rotate(theta);
		
		
		
	}

	

}
