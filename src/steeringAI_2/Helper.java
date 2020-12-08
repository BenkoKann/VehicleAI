package steeringAI_2;

import java.awt.Color;

public class Helper {
	
	/**
	 * 
	 * @param min
	 * @param max
	 * @return number between two arguments, including the min and not including the max
	 */
	public static double random(double min, double max) {
		return Math.random() * (max - min + 1) + min;
	}
	/**
	 * 
	 * @param max
	 * @return num between 0 and max, not including max
	 */
	public static double random(double max) {
		return Math.random() * max;
	}
	
	/**
	 * 
	 * @param c1 interpolate from this color
	 * @param c2 interpolate to this color
	 * @param amt number between 0 and 1
	 * @return interpolated color
	 * 
	 * @example 
	 * 
	 * 1 = c2 color
	 * .5 = some color inbetween
	 * 0 = c1 color
	 */
	public static Color lerpColor(Color c1, Color c2, double amt) {
		
		// prevent extrapolation
		amt = Math.max(Math.min(amt, 1), 0);
		
		int g = (int) (c1.getGreen() - c1.getGreen() * (1-amt) + c2.getGreen() * (1-amt));
		int r = (int) (c1.getRed() - c1.getRed() * (1-amt) + c2.getRed() * (1-amt));
		int b = (int) (c1.getBlue() - c1.getBlue() * (1-amt) + c2.getBlue() * (1-amt));
		
		return new Color(r, g, b);
		
	}

}
