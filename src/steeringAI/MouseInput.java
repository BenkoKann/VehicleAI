package steeringAI;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class MouseInput extends MouseAdapter implements MouseMotionListener{
	
	private int x, y;
	
//	public void mousePressed(MouseEvent e) {
//		this.x = e.getX();
//		this.y = e.getY();
//	
//	}
	
	public void mouseMoved(MouseEvent e) {
		this.x = e.getX();
		this.y = e.getY();
	}
	
	public void mouseDragged(MouseEvent e) {
		this.x = e.getX();
		this.y = e.getY();
	}
	

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	

}
