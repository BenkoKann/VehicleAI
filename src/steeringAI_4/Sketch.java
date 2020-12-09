package steeringAI_4;
// https://youtu.be/XaOVH8ZSRNA?list=PLRqwX-V7Uu6YJ3XfHhT2Mm4Y5I99nrIKX&t=1254
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Sketch extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private KeyInput keyInput;
	private Timer t;
	
	public static final int HEIGHT = 600;
	public static final int WIDTH = 800;
	private static final int NFOOD = 0; // number of foods
	private static final int NPOISON = 20; // number of poisons
	private static final int NVEHICLES = 200;
	private static final int PFOOD = 15; // percent to spawn a food each frame
	private static final int PPOISON = 5;// percent spawn a poison
	
	
	public static final int MUTATIONRATE = 30;
	public static final int MAXRADIUS = 150; // radius for attaction
	public static final int MAXFOODWEIGHT = 7; // positive or negative max (absolute max)
	
	ArrayList<Vector> food = new ArrayList<>();
	ArrayList<Vector> poison = new ArrayList<>();
	ArrayList<Vehicle> vehicles = new ArrayList<>();
	
	ActionListener gameLoop = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			repaint();
			
		}
		
	};
	
	public Sketch() {

		keyInput = new KeyInput();
		this.addKeyListener(keyInput);
		this.setFocusable(true);
        this.requestFocus();
		
		
		
		
		
		this.setUp();
		
		

	}

	public void setUp() {
		// v = new Vehicle(100, 100);
		
		for(int i = 0; i < NVEHICLES; i++) {
			int x = (int) Helper.random(WIDTH);
			int y = (int) Helper.random(HEIGHT);
			
			vehicles.add(new Vehicle(x, y));
		}
		
		
		
		for(int i = 0; i < NFOOD; i++) {
			int x = (int) (Math.random() * WIDTH);
			int y = (int) (Math.random() * HEIGHT);
			food.add(new Vector(x, y));
		}
		
		
		for(int i = 0; i < NPOISON; i++) {
			int x = (int) (Math.random() * WIDTH);
			int y = (int) (Math.random() * HEIGHT);
			poison.add(new Vector(x, y));
		}
	
		
		
		
		t = new Timer(17, gameLoop);
		t.start();
		
		
	}
	
	public static void main(String[] args) {
		
		Sketch.initGraphics();

	}
	
	public static void initGraphics() {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBackground(Color.decode("#333333"));
		frame.add(new Sketch());
		
		frame.setLocationRelativeTo(null);
		frame.setBounds(0, 0, WIDTH, HEIGHT);
		frame.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		frame.pack();
		frame.setVisible(true);
	}
	


	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.decode("#333333"));
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		if(Helper.random(0, 100) < PFOOD) { // 5 percent chance of new food spawn
			int x = (int) (Math.random() * WIDTH);
			int y = (int) (Math.random() * HEIGHT);
			food.add(new Vector(x, y));
		}
		
	if(poison.size() < 40) {
		if(Helper.random(0, 100) < PPOISON) { // 1 percent chance of new poison spawn
			int x = (int) (Math.random() * WIDTH);
			int y = (int) (Math.random() * HEIGHT);
			poison.add(new Vector(x, y));
		}
	}
		
	
		
		
		// Vector mouse = new Vector(mouseInput.getX(), mouseInput.getY());
		
		// [START] cursor stuff
		
//		g.setColor(Color.decode("#7F7F7F"));
//		g.fillOval((int) mouse.x-24, (int)mouse.y-24, 48, 48);
//		
//		Graphics2D g2d = (Graphics2D) g;
//		g2d.setStroke(new BasicStroke(2));
//		g.setColor(Color.white);
//		g.drawOval((int) mouse.x-24, (int)mouse.y-24, 48, 48);
		
		// [END] cursor stuff
		
		// [START] food stuff
		
		for(int i = 0; i < food.size(); i++) {
			g.setColor(Color.green);
			g.fillOval((int)food.get(i).x, (int)food.get(i).y, 8, 8);
		}
		
		for(int i = 0; i < poison.size(); i++) {
			g.setColor(Color.red);
			g.fillOval((int)poison.get(i).x, (int)poison.get(i).y, 8, 8);
		}
		
		// [END] food stuff
		
		// v.seek(mouse);
		
		for(int i = 0; i < vehicles.size(); i++) {
			vehicles.get(i).boundaries();
			vehicles.get(i).behaviors(food, poison);
			vehicles.get(i).update();
			vehicles.get(i).paintComponent(g);
			
			Vehicle childVehicle = vehicles.get(i).cloneMe();
			if(childVehicle != null) {
				vehicles.add(childVehicle);
			}
			
			if(vehicles.get(i).dead()) {
				int x = (int) vehicles.get(i).position.x;
				int y = (int) vehicles.get(i).position.y;
				food.add(new Vector(x, y));
				
				vehicles.remove(i);
			}
			
			
		}
	
	//	v.behaviors(food, poison);
		// v.eat(food);
		// v.eat(poison);
	//v.update();
	//v.paintComponent(g);
	}
	

	
	
	
	
	
	
	
	
	
	
}
