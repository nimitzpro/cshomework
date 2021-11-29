package raytracer.shapes;

import raytracer.*;

/** Triangle class that extends from shape
 * A triangle is drawn from the different points
 */

public class Triangle extends Shape {
    private final Point p1;
    private final Vector u, v;

    private final double a, b, c, d;

    /** Three points that the triangle will be drawn from
     * 
     * @param p1 first point
     * @param p2 second point
     * @param p3 third point
     */

    public Triangle(Point p1, Point p2, Point p3) {
        this.p1 = p1;

        this.u = new Vector(p1, p2);
        this.v = new Vector(p1, p3);
        Vector normal = u.cross(v).normalize();

        this.a = normal.x;
        this.b = normal.y;
        this.c = normal.z;
        this.d = p1.x * normal.x + p1.y * normal.y + p1.z * normal.z;

    }

    /**
     * @param ray takes ray class
     */

    @Override
    public RayHit intersect(Ray ray) {
        double denominator = (a * ray.direction.x + b * ray.direction.y + c * ray.direction.z);
        if(denominator == 0.0) return null;

        double t = - (a * ray.origin.x + b * ray.origin.y + c * ray.origin.z - d) / denominator;
        Vector normal = new Vector(a, b, c).normalize();
        RayHit planeHit = new RayHit(ray, this, normal, t, true);

        if(t < 0) {
            planeHit = null;
        }

        if(planeHit == null) return null;

        double uu, uv, vv, wu, wv, D;
        uu = u.dot(u);
        uv = u.dot(v);
        vv = v.dot(v);
        Vector w = new Vector(planeHit.point.plus(new Vector(p1).negate()));

        wu = w.dot(u);
        wv = w.dot(v);
        D = uv * uv  - uu * vv;

        double s;
        s = (uv * wv - vv * wu) / D;
        if(s < 0 || s > 1) return null;
        t = (uv * wu - uu * wv) / D;
        if(t < 0 || (s + t) > 1) return null;

        return new RayHit(planeHit.ray, this, planeHit.normal, planeHit.point, true);
    }
}