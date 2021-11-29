package raytracer;

/** Specifies an x, y and z where objects are located
 * 
 */

public class Point {
	public double x, y, z;

	/**
	 * 
	 * @param x axis
	 * @param y axis
	 * @param z axis
	 */

	public Point(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Point(int[] p){
		this.x = p[0];
		this.y = p[1];
		this.z = p[2];
	}

	/**
	 * 
	 * @param p takes point p
	 * @return distance to point p
	 */

	public double distanceTo(Point p) {
		return Math.sqrt((p.x - x)*(p.x - x) + (p.y - y)*(p.y - y) + (p.z - z)*(p.z - z));
	}

	/**
	 * 
	 * @param v takes vector class
	 * @return adds coordinates of Vector v point to its own
	 */

	public Point plus(Vector v) {
		return new Point(x + v.x, y + v.y, z + v.z);
	}

	/**
	 * prints out points
	 */

	public String toString() {
		return "[" + x + ", " + y + ", " + z + "]";
	}
}
