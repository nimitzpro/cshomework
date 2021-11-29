package raytracer.pigments;

import raytracer.*;

import java.awt.Color;

/** Blends 2 colors together on a surface
 * 
 */

public class GradientPigment implements Pigment {
	private final Point origin;
	private final Vector v;
	private final Color start;
	private final Color end;

	/**
	 * @param origin point of origin
	 * @param vector direction of the gradient
	 * @param start the color it starts with
	 * @param end the color it ends with
	 */
	public GradientPigment(Point origin, Vector vector, Color start, Color end) {
		this.origin = origin;
		this.v = vector;
		this.start = start;
		this.end = end;
	}

	/**
	 * @param p point of color
	 * @return the colour at point p
	 */
	public Color getColor(Point p) {

		double d = Math.abs(new Vector(origin, p).dot(v)) / v.magnitude();
		double percent = (d / v.magnitude());

		percent = Math.abs(percent);

		return ColorUtil.blend(ColorUtil.intensify(start, (float)percent), ColorUtil.intensify(end, 1.0f - (float)percent));
	}


	/**
	 * toString method
	 */
	public String toString() {
		return "gradiented";
	}
}
