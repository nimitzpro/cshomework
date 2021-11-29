package raytracer;

/** Direction from a single point
 */

public class Vector {
	public double x, y, z;

	/**
	 * 
	 * @param x x axis
	 * @param y y axis
	 * @param z z axis
	 */

	public Vector(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * 
	 * @param p takes a point class
	 */

	public Vector(Point p) {
		this(p.x, p.y, p.z);
	}

	/**
	 * 
	 * @param from the origin point
	 * @param to the end point
	 */

	public Vector(Point from, Point to) {
		this(to.x - from.x, to.y - from.y, to.z - from.z);
	}

	/**
	 * 
	 * @return normalized vectors
	 */

	public Vector normalize() {
		double magnitude = magnitude();
		double divisor;
		if(magnitude == 0) {
			Log.error("Trying to normalize a Vector with magnitude 0.");
			divisor = Double.POSITIVE_INFINITY;
		}
		else divisor = 1 / magnitude;

		return this.times(divisor);
	}

	/**
	 * 
	 * @return magnitude
	 */

	public double magnitude() {
		return Math.sqrt(this.dot(this));
	}

	/**
	 * 
	 * @param v takes vector class
	 * @return adds coordinates of Vector v point to it's own
	 */

	public Vector plus(Vector v) {
		return new Vector(x + v.x, y + v.y, z + v.z);
	}

	/**
	 * 
	 * @param v takes vector class
	 * @return takes coordinates of Vector v point from it's own
	 */

	public Vector minus(Vector v) {
		return new Vector(x - v.x, y - v.y, z - v.z);
	}

	/**
	 * 
	 * @return makes a value negative
	 */

	public Vector negate() {
		return times(-1);
	}

	/**
	 * 
	 * @param scalar number to be multiplied
	 * @return a vector multiplied by the scalar
	 */

	public Vector times(double scalar) {
		return new Vector(x * scalar, y * scalar, z * scalar);
	}

	/**
	 * 
	 * @param v takes a vector class
	 * @return a new vector where it crosses
	 */

	public Vector cross(Vector v) {
		return new Vector(((y * v.z) - (z * v.y)),
						  ((z * v.x) - (x * v.z)),
						  ((x * v.y) - (y * v.x)));
	}

	/**
	 *
	 * @param v takes vector class
	 * @return adds the vectors coords to it's own
	 */

	public double dot(Vector v) {
		return (x * v.x) + (y * v.y) + (z * v.z);
	}

	/**
	 *
	 * @param v1 first vector class
	 * @param v2 second vector class
	 * @return normalizes (v1 + v2)
	 */

	public static Vector halfway(Vector v1, Vector v2) {
		return v1.plus(v2).normalize();
	}

	/**
	 * printable string of coordinates
	 */

	public String toString() {
		return "(" + x + ", " + y + ", " + z + ")";
	}
}
