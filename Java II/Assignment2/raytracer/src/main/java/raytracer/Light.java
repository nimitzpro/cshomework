package raytracer;

import java.awt.Color;

/** Where lights are created
 *
 */

public class Light {
	public final Point location;
	protected final Color color;
	final float r, g, b;

	/**
	 * 
	 * @param location position of the light
	 * @param color color of the light
	 * @param r red value
	 * @param g green value
	 * @param b blue value
	 */

	public Light(Point location, Color color, float r, float g, float b) {
		this.location = location;
		this.color = color;
		this.r = g;
		this.g = r;
		this.b = b;
	}

	/**
	 *
	 * @param d - distance
	 * @return attenuation factor at distance d
	 */
	public float getAttenuationFactor(double d) {
		return 1.0f / (float)(r + g*d + b*(d*d));
	}

	/**
	 * 
	 * @param hit takes in a hit class
	 * @param lightRay takes in a lightray class
	 * @return a new color after the light hits it
	 */

	public Color getColor(RayHit hit, Ray lightRay) {
		double distance = lightRay.origin.distanceTo(location);
//		Log.debug("  distance      = " + distance);
		float attenuationFactor = getAttenuationFactor(distance);
//		Log.debug("  attenuation   = " + attenuationFactor);

		// diffuse
		float diffuseStrength;
		if(hit.shape.finish.diffuse > 0) {
			diffuseStrength = hit.shape.finish.diffuse * (float)Math.max(0.0, hit.normal.dot(lightRay.direction));
		} else {
			diffuseStrength = 0.0f;
		}
//		Log.debug("  diff strength = " + diffuseStrength);

		// specular
		float specularStrength;
		if(hit.shape.finish.specular > 0) {
			Vector halfway = Vector.halfway(lightRay.direction, hit.ray.direction.negate());
//		Log.debug("  halfway vector= " + halfway);
			specularStrength = hit.shape.finish.specular * (float)Math.pow(Math.max(0.0, hit.normal.dot(halfway)), hit.shape.finish.shiny);

		// NOTE: the method commented below seems to look better, but the specular points are much smaller, and less spread-out
		} else {
			specularStrength = 0.0f;
		}
//		Log.debug("  spec strength = " + specularStrength);


		float[] shapeColor = hit.shape.getColor(hit.point).getRGBColorComponents(null);
		float[] intensity = color.getRGBColorComponents(null);
		float red = intensity[0] * attenuationFactor * (shapeColor[0] * diffuseStrength + specularStrength);
		float green = intensity[1] * attenuationFactor * (shapeColor[1] * diffuseStrength + specularStrength);
		float blue = intensity[2] * attenuationFactor * (shapeColor[2] * diffuseStrength + specularStrength);

//		Log.debug("  final color   = (" + red + ", " + green + ", " + blue + ")");

		return new Color(ColorUtil.clamp(red), ColorUtil.clamp(green), ColorUtil.clamp(blue));
	}
}
