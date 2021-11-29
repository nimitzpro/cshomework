package raytracer.pigments;

import raytracer.Point;

import java.awt.Color;

/** One solid color on a surface
 * 
 */

public class SolidPigment implements Pigment {
    public Color color;

    /**
     * 
     * @param color takes color class
     */

    public SolidPigment(Color color) {
        this.color = color;
    }

    /**
     * @param p point of color
     * @return color at point p
     */

    public Color getColor(Point p) {
        return color;
    }

    /**
     * @return string of the type of color it is
     */

    public String toString() {
        return "solid";
    }
}