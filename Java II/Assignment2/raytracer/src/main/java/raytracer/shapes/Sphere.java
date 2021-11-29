package raytracer.shapes;

import raytracer.*;

/** The Sphere class
 *  A sphere is created by giving a point for the center and a radius length
 */

public class Sphere extends Shape {
	Point center;
	double radius;

	/**
	 * 
	 * @param center center point
	 * @param radius length of radius (double)
	 */

	public Sphere(Point center, double radius) {
		this.center = center;
		this.radius = radius;
	}

	/**
	 * @param ray takes ray class
	 * @return a new Rayhit class
	 * 
	 */

	public RayHit intersect(Ray ray) {

		Point p = ray.origin;
		Vector u = ray.direction;
		Vector v = new Vector(center, p);
		double b = 2 * (v.dot(u));
		double c = v.dot(v) - radius*radius;
		double discriminant = b*b - 4*c;

		if(discriminant < 0) return null;

		double tMinus = (-b - Math.sqrt(discriminant)) / 2;
		double tPlus = (-b + Math.sqrt(discriminant)) / 2;

		if(tMinus < 0 && tPlus < 0) {
			// sphere is behind the ray
			return null;
		}

		double tValue;
		Vector normal;
		Point intersection;
		boolean incoming;
		if(tMinus < 0 && tPlus > 0) {
			// ray origin lies inside the sphere. take tPlus
			tValue = tPlus;
//			return null;
			intersection = ray.getEnd(tValue);
			normal = new Vector(intersection, center);
			incoming = false;
		} else {
			// both roots positive. take tMinus
			tValue = tMinus;
			intersection = ray.getEnd(tValue);
			normal = new Vector(center, intersection);
			incoming = true;
		}

		return new RayHit(ray, this, normal, intersection, incoming);

	}

	/**
	 * @return color and it's shape
	 */

    public String toString() {
		return pigment + " sphere";
	}
}
