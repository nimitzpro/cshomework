package raytracer.shapes;

import raytracer.*;
import raytracer.pigments.Finish;
import raytracer.pigments.Pigment;

import java.awt.Color;

/** Shape class
 * 
 * Every shape ever created will have a pigment(Color) and a finish
 */

public abstract class Shape {
	public Pigment pigment;
	public Finish finish;

	/**
	 * 
	 * @param pigment the color of the shape
	 * @param finish how shiny it is
	 * 
	 */

	public final void setMaterial(Pigment pigment, Finish finish) {
		this.pigment = pigment;
		this.finish = finish;
	}

	/**
	 * 
	 * @param ray takes a ray class
	 */

	public abstract RayHit intersect(Ray ray);

	/**
	 * 
	 * @param p takes point p
	 * @return it's own Color class
	 */

	public final Color getColor(Point p) {
		return pigment.getColor(p);
	}
}
