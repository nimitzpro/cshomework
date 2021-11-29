package raytracer;

/** The perspective where the image will be printed from
 * */

public class Camera {
	private final Point eye;
	private final Vector vector_x;
	private final Vector vector_y;
	private final Vector vector_z;

	private final double windowDistance;
	private final double windowWidth;
	private final double windowHeight;

	private final int height;
	private final int width;

	/**
	 * 
	 * @param eye (where the camera is located)
	 * @param center (where the camera is facing)
	 * @param up direction
	 * @param field_of_view_y how much of the y axis is in view
	 * @param width of the picture
	 * @param height of the picture
	 */

	public Camera(Point eye, Point center, Vector up, double field_of_view_y, int width, int height) {
		field_of_view_y = Math.toRadians(field_of_view_y);
		double field_of_view_x = field_of_view_y * width / height;

		Vector at = new Vector(eye, center);
		vector_z = at.negate().normalize();
		vector_x = up.cross(vector_z).normalize();
		vector_y = vector_z.cross(vector_x);

		this.eye = eye;
		this.width = width;
		this.height = height;

		windowDistance = 1.0;
		windowHeight = Math.sin(field_of_view_y / 2.0) * windowDistance * 2.0;
		windowWidth = Math.sin(field_of_view_x / 2.0) * windowDistance * 2.0;

		Log.debug("  ViewFrame:");
		Log.debug("    Org: " + eye);
		Log.debug("    X:   " + vector_x);
		Log.debug("    Y:   " + vector_y.toString());
		Log.debug("    Z:   " + vector_z);

		Log.debug("    Window width: " + windowWidth);
		Log.debug("          height: " + windowHeight);
	}

	/**
	 * 
	 * @param col amount of columns
	 * @param row amount of rows
	 * @return Ray class
	 */

	public Ray getRay(int col, int row) {
		return getRay(col, row, 0.5, 0.5);
	}

	/**
	 * 
	 * @param col amount of columns
	 * @param row amount of rows
	 * @param pixelAdjustmentX amount of pixels to add to x
	 * @param pixelAdjustmentY amount of pixels to add to y
	 * @return Ray class
	 */

	public Ray getRay(int col, int row, double pixelAdjustmentX, double pixelAdjustmentY) {
		double x = (((double)col + pixelAdjustmentX) / width) * windowWidth - (windowWidth / 2.0);
		double y = (((double)row + pixelAdjustmentY) / height) * windowHeight - (windowHeight / 2.0);

		Vector v = new Vector(eye, convertCoords(new Point(x, y, -windowDistance)));

		Ray ray = new Ray(eye, v);
		Log.debug("    Final ray: " + ray);

		return ray;
	}

	/**
	 * 
	 * @param p takes point class
	 * @return a point class
	 */

	public Point convertCoords(Point p) {
		Vector v = convertCoords(new Vector(p.x, p.y, p.z));
		return new Point(v.x, v.y, v.z);
	}

	/**
	 * 
	 * @param p takes a point class
	 * @return Matrix class
	 */

	public Vector convertCoords(Vector p) {
		Matrix rT = new Matrix(new double[][]{
				{vector_x.x, vector_y.x, vector_z.x, 0},
				{vector_x.y, vector_y.y, vector_z.y, 0},
				{vector_x.z, vector_y.z, vector_z.z, 0},
				{0, 0, 0, 1}
		});
		Matrix tInv = new Matrix(new double[][]{
				{1, 0, 0, eye.x},
				{0, 1, 0, eye.y},
				{0, 0, 1, eye.z},
				{0, 0, 0, 1}
		});

		Matrix matrix = tInv.times(rT);
		return matrix.times(new Vector(p.x, p.y, p.z));
	}
}
