package raytracer;

import raytracer.pigments.*;
import raytracer.shapes.*;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

/** Used to create the scene
 * 
 */

public class RayTracer {
	public static final int MAX_RECURSION_LEVEL = 5;
	public static final Color BACKGROUND_COLOR = Color.GRAY;

	private Camera camera;
	private final ArrayList<Light> lights = new ArrayList<>();
	private final ArrayList<Shape> shapes = new ArrayList<>();
	private final int width;
	private final int height;

	/**
	 * 
	 * @param width of the picture
	 * @param height of the picture
	 */

	public RayTracer(int width, int height) {
		this.width = width;
		this.height = height;
	}

	/**
	 * 
	 * @param hit takes in a hit object
	 * @param depth takes in an integer as depth
	 * @return color of the pixel not hit by light
	 */

	private Color shade(RayHit hit, int depth) {
		Color color = Color.BLACK;
		Light ambientLight = lights.get(0);
		if (ambientLight != null && hit.shape.finish.ambience > 0) {
			color = ColorUtil.blend(color, ColorUtil.intensify(hit.shape.getColor(hit.point), ambientLight.getColor(hit, null)));
		}

		for (Light light: lights) {
			Vector lightRayVec = new Vector(hit.point, light.location);
			Ray lightRay = new Ray(hit.point, lightRayVec);
			lightRay.t = lightRayVec.magnitude();
			RayHit obstruction = findHit(lightRay);
			if (obstruction == null) {
				Color light_color = light.getColor(hit, lightRay);
				color = ColorUtil.blend(color, light_color);
			}
		}

		if (depth <= MAX_RECURSION_LEVEL) {
			if (hit.shape.finish.isReflective()) {
				color = ColorUtil.blend(color, ColorUtil.intensify(trace(hit.getReflectionRay(), depth + 1), hit.shape.finish.reflection));
			}

			if (hit.shape.finish.isTransparent()) {
				color = ColorUtil.blend(color, ColorUtil.intensify(trace(hit.getTransmissionRay(), depth + 1), hit.shape.finish.transparancy));
			}
		}
		return color;
	}

	/**
	 * 
	 * @param ray takes in ray object
	 * @return where the ray hits and object
	 */

	private RayHit findHit(Ray ray) {
		RayHit hit = null;

		for (Shape shape : shapes) {
			RayHit h = shape.intersect(ray);
			if (h != null && h.t < ray.t) {
				hit = h;
				ray.t = h.t;
			}
		}
		return hit;
	}

	/**
	 * 
	 * @param ray takes in ray object
	 * @param depth takes in depth as an integer
	 * @return grey color
	 */

	private Color trace(Ray ray, int depth) {

		RayHit hit = findHit(ray);

		if (hit != null) {
			return shade(hit, depth);
		}
		return BACKGROUND_COLOR;
	}

	/**
	 * 
	 * @param outFile the file that's gonna be the output
	 * @param formatName the type of file it will be
	 * @throws IOException triggered when program is stopped early
	 */

	public void draw(String outFile, String formatName) throws IOException {
		final BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		long start = System.currentTimeMillis();

		for (int r = 0; r < height; r++) {
			if (r % 5 == 0) Log.info((height - r) + " rows left to trace.");
			for (int c = 0; c < width; c++) {
				image.setRGB(c, r, getPixelColor(c, r).getRGB());
			}
		}

		Log.info("Finished in: " + (System.currentTimeMillis() - start) + "ms");
		String fileName = outFile + "." + formatName;

		ImageIO.write(image, formatName, new File(fileName));
	}

	/**
	 * 
	 * @param col columns
	 * @param row rows
	 * @return color of the pixel
	 */

	public Color getPixelColor(int col, int row) {
		int bmpRow = height - 1 - row;

		if (Main.ANTI_ALIAS) {
			Ray ray = camera.getRay(col, bmpRow, 0, 0);
			Color c1 = trace(ray, 0);
			ray = camera.getRay(col, bmpRow, .5, 0);
			Color c2 = trace(ray, 0);
			ray = camera.getRay(col, bmpRow, 0, .5);
			Color c3 = trace(ray, 0);
			ray = camera.getRay(col, bmpRow, .5, .5);
			Color c4 = trace(ray, 0);

			return ColorUtil.average(c1, c2, c3, c4);
		} else {
			Ray ray = camera.getRay(col, bmpRow);
			return trace(ray, 0);
		}
	}

	/**
	 * 
	 * @param pigment takes in pigment class
	 * @param finish takes in a finish class
	 * @param centre_position the coordinates where the center of the sphere is located
	 * @param size the size of the sphere
	 */

	public void addSphere(Pigment pigment, Finish finish, int[] centre_position, int size) {
		Point c = new Point(centre_position[0], centre_position[1], centre_position[2]);
		Sphere sphere = new Sphere(c, size);
		sphere.setMaterial(pigment, finish);
		shapes.add(sphere);
	}

	/**
	 * 
	 * @param pigment takes in the pigment class
	 * @param finish takes in the finish class
	 * @param point1 The first point
	 * @param point2 the second point
	 * @param point3 the third point
	 */

	public void addTriangle(Pigment pigment, Finish finish, int[] point1, int[] point2, int[] point3) {
		Point p1 = new Point(point1[0], point1[1], point1[2]);
		Point p2 = new Point(point2[0], point2[1], point2[2]);
		Point p3 = new Point(point3[0], point3[1], point3[2]);

		Triangle triangle = new Triangle(p1, p2, p3);
		triangle.setMaterial(pigment, finish);
		shapes.add(triangle);
	}

	/**
	 *
	 * @param ambient amount of ambient light reflected
	 * @param diffuse strength of diffuse reflection
	 * @param specular strength of specular reflection
	 * @param shiny how shiny it is
	 * @param mirror how reflective it is
	 * @param transparency how transparent it is
	 * @param indexOfRefraction index of reflection
	 * @return the finish that's been made
	 */

	public Finish createFinish(float ambient, float diffuse, float specular, float shiny,
							   float mirror, float transparency, float indexOfRefraction) {
		return new Finish(ambient, diffuse, specular, shiny, mirror, transparency, indexOfRefraction);
	}

	/**
	 * 
	 * @param colour takes a color class
	 * @return the new pigment made
	 */

	public Pigment createSolidPigment(Color colour) {
		return new SolidPigment(colour);
	}

	/**
	 *
	 * @param origin point of origin
	 * @param vectorOrigin direction of the starting color
	 * @param vectorDestination direction of the ending color
	 * @param start the color it starts with
	 * @param end the color it ends with
	 */
	public Pigment createGradientPigment(int[] origin, int[] vectorOrigin, int[] vectorDestination, Color start, Color end) {
		Point o = new Point(origin);
		Vector v = new Vector(new Point(vectorOrigin), new Point(vectorDestination));
		return new GradientPigment(o, v, start, end);
	}

	/**
	 * 
	 * @param lightPosition position of the light
	 * @param colour color of the light
	 * @param r red value
	 * @param g green value
	 * @param b blue value
	 */

	public void addAmbientLight(int[] lightPosition, Color colour, float r, float g, float b) {
		Point l = new Point(lightPosition[0], lightPosition[1], lightPosition[2]);
		lights.add(0, new AmbientLight(l, colour, r, g, b));
	}

	/**
	 *
	 * @param lightPosition position of the light
	 * @param colour color of the light
	 * @param r red value
	 * @param g green value
	 * @param b blue value
	 */

	public void addLight(int[] lightPosition, Color colour, float r, float g, float b) {
		Point l = new Point(lightPosition[0], lightPosition[1], lightPosition[2]);
		lights.add(new Light(l, colour, r, g, b));
	}

	/**
	 * 
	 * @param eye where the camera is located
	 * @param centre where it's facing
	 * @param up direction
	 * @param field_of_view_y how much of the y axis it can see
	 */

	public void addView(int[] eye, int[] centre, int[] up, int field_of_view_y) {
		Point e = new Point(eye[0], eye[1], eye[2]);
		Point c = new Point(centre[0], centre[1], centre[2]);
		Vector u = new Vector(up[0], up[1], up[2]);
		camera = new Camera(e, c, u, field_of_view_y, this.width, this.height);
	}
}
