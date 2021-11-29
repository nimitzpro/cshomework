package raytracer;

/** 
 * 
 */

public class Matrix {
	private final double[][] m;

	/**
	 * 
	 * @param m takes matrix m that stores doubles
	 */

	public Matrix(double[][] m) {
		if(m.length < 4 || m[0].length < 4) throw new IllegalArgumentException("Matrix must be a 4x4 array");
		this.m = m;
	}

	/**
	 * 
	 * @param matrix takes matrix class
	 * @return multiplies this matrix by "matrix"
	 */

	public Matrix times(Matrix matrix) {
		double[][] m2 = matrix.m;
		double[][] r = new double[4][4];

		for(int row=0;row<4;row++) {
			for(int col=0;col<4;col++) {
				r[row][col] = m[row][0] * m2[0][col] + m[row][1] * m2[1][col] + m[row][2] * m2[2][col] + m[row][3] * m2[3][col];
			}
		}

		return new Matrix(r);
	}

	/**
	 * 
	 * @param v takes vector class
	 * @return multiplies a vector v by this matrix, returns new vector
	 */

	public Vector times(Vector v) {
		double x, y, z;

		x = m[0][0] * v.x + m[0][1] * v.y + m[0][2] * v.z + m[0][3];
		y = m[1][0] * v.x + m[1][1] * v.y + m[1][2] * v.z + m[1][3];
		z = m[2][0] * v.x + m[2][1] * v.y + m[2][2] * v.z + m[2][3];

		// fourth coordinate
		double mag = m[3][0] * v.x + m[3][1] * v.y + m[3][2] * v.z + m[3][3];

		x /= mag;
		y /= mag;
		z /= mag;

		return new Vector(x, y, z);
	}
}
