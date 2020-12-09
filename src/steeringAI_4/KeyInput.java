package steeringAI_4;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter{
	
	public static boolean isChecked = true; // value to toggle
	
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_SPACE) {
			isChecked = !isChecked;
		}
		
		
	}


	
	
	
}
