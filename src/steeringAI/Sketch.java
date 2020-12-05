package steeringAI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Sketch extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Vehicle v;
	private MouseInput mouseInput;
	private Timer t;
	
	ActionListener gameLoop = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			repaint();
			
		}
		
	};
	
	public Sketch() {
		mouseInput = new MouseInput();
		addMouseListener(mouseInput);
		addMouseMotionListener(mouseInput);
		this.setFocusable(true);
        this.requestFocus();
		
		
		
		
		
		v = new Vehicle(100, 100);
		
		t = new Timer(17, gameLoop);
		t.start();

	}

	public static void main(String[] args) {
		
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBackground(Color.decode("#333333"));
		frame.add(new Sketch());
		
		frame.setLocationRelativeTo(null);
		frame.setBounds(0, 0, 500, 500);
		frame.setPreferredSize(new Dimension(500, 500));
		frame.pack();
		frame.setVisible(true);

	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.decode("#333333"));
		g.fillRect(0, 0, 500, 500);
		
		Vector mouse = new Vector(mouseInput.getX(), mouseInput.getY());
		
		g.setColor(Color.red);
		g.fillOval((int) mouse.x-24, (int)mouse.y-24, 48, 48);
		
		v.seek(mouse);
		v.update();
		v.paintComponent(g);
	}
	

	
	
	
	
	
	
	
	
	
	
}
