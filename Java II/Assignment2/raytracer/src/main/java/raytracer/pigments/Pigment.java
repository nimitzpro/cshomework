package raytracer.pigments;

import raytracer.Point;

import java.awt.Color;



@FunctionalInterface
public interface Pigment {

	/**
	 * 
	 * @param p point of color
	 * @return point p
	 */

	Color getColor(Point p);
}