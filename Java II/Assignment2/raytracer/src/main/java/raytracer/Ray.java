package raytracer;

/** Used for creating rays
 * 
 */

public class Ray {
	public final Point origin;
	public final Vector direction;
	public double t;

	/**
	 * 
	 * @param origin takes the origin point
	 * @param direction takes vector class as direction
	 */

	public Ray(Point origin, Vector direction) {
		this(origin, direction, true);
	}

	/**
	 * 
	 * @param origin takes the origin point
	 * @param direction takes vector class as direction
	 * @param adjustForError multiplies direction by .001 if true
	 */

	public Ray(Point origin, Vector direction, boolean adjustForError) {
		this.t = Double.POSITIVE_INFINITY;

		this.direction = direction.normalize();

		if(adjustForError) origin = origin.plus(this.direction.times(0.001));

		this.origin = origin;
	}

	/**
	 * 
	 * @param t a double
	 * @return the end of the ray
	 */

	public Point getEnd(double t) {
		return origin.plus(direction.times(t));
	}

	/**
	 * prints out the ray's origin, direction and end
	 */

	public String toString() {
		return "Org:" + origin + " Dir:" + direction + " t:" + t;
	}
}
