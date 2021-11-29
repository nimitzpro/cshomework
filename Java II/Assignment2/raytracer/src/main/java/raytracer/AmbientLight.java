package raytracer;

import java.awt.Color;

/** AmbientLight class
 * 
 */

public class AmbientLight extends Light {

	/**
	 * 
	 * @param location where the ambient light is located
	 * @param color ambient light color
	 * @param r red value
	 * @param g green value
	 * @param b blue value
	 */

	public AmbientLight(Point location, Color color, float r, float g, float b) {
		super(location, color, r, g, b);
	}

	/**
	 * @param hit the point where it hits an object
	 * @return color of the pixel that has been hit
	 */

	@Override
	public Color getColor(RayHit hit, Ray lightRay) {
		return ColorUtil.intensify(color, hit.shape.finish.ambience);
	}
}
