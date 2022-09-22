package raytracer;

import raytracer.pigments.Finish;
import raytracer.pigments.Pigment;

import java.awt.*;
import java.io.IOException;

/** Creates the scene
 * 
 */

public class Test5 {
    // this test creates the same image as the text file test05.txt (without the checkered pigment)
    /**
     * 
     * @throws IOException when the program execution is stopped manually
     */

    public static void main() throws IOException {

        RayTracer rayTracer = new RayTracer(3840, 2160);

        // adding view to raytracer
        final int[] eyePosition = new int[]{1, -10, 5};
        final int[] screenCentre = new int[]{1, 10, -3};
        final int[] upDirection = new int[]{0, 0, 1};
        final int fieldOfView = 40;
        rayTracer.addView(eyePosition, screenCentre, upDirection, fieldOfView);

        // adding ambient light to raytracer
        final int[] lightPosition0 = new int[]{0, 0, 0};
        rayTracer.addAmbientLight(lightPosition0, new Color(0.5f, 0.5f, 0.5f, 1), 0f, 0f, 0f);

        // adding light 2 to raytracer
        final int[] lightPosition1 = new int[]{-100, -100, 100};
        rayTracer.addLight(lightPosition1, new Color(1f, 1f, 1f, 1), 1f, 0f, 0f);

        // adding light 3 to raytracer
        final int[] lightPosition2 = new int[]{1, 4, 3};
        rayTracer.addLight(lightPosition2, new Color(.4f, .4f, .4f, 1), 0f, 0f, .01f);

        Pigment pigment1 = rayTracer.createSolidPigment(new Color(1.0f, 0.0f, 0.0f, 1));

        Pigment pigment2 = rayTracer.createSolidPigment(new Color(0.0f, 0.6f, 0.0f, 1));

        Pigment pigment3 = rayTracer.createSolidPigment(new Color(0.0f, 0.0f, 1.0f, 1));

        Pigment pigment4 = rayTracer.createSolidPigment(new Color(0.8f, 0.3f, 0.7f, 1));

        Pigment pigment5 = rayTracer.createSolidPigment(new Color(0.5f, 0.2f, 0.8f, 1));

        // creating 4 finishes
        Finish finish0 = rayTracer.createFinish(0.7f, 0.3f, 0.0f, 1f, 0.0f, 0.0f, 0f);
        Finish finish1 = rayTracer.createFinish(0.1f, 0.1f, 0.8f, 400f, 0.0f, 0.9f, 1.45f);
        Finish finish2 = rayTracer.createFinish(0.3f, 0.1f, 1.0f, 1000f, 0.9f, 0.0f, 0f);
        Finish finish3 = rayTracer.createFinish(1.0f, 0.0f, 0.0f, 1f, 0.0f, 0.0f, 0f);

        // adding 5 spheres to raytracer
        int[] centre_position0 = new int[]{3, 2, 1};
        rayTracer.addSphere(pigment1, finish2, centre_position0, 1);

        int[] centre_position1 = new int[]{-1, 4, 1};
        rayTracer.addSphere(pigment2, finish2, centre_position1, 1);

        int[] centre_position2 = new int[]{1, -1, 1};
        rayTracer.addSphere(pigment3, finish1, centre_position2, 1);

        int[] centre_position3 = new int[]{0, 0, -100};
        rayTracer.addSphere(pigment4, finish0, centre_position3, 100);

        int[] centre_position4 = new int[]{0, 0, 0};
        rayTracer.addSphere(pigment5, finish3, centre_position4, 500);

        // adding a triangle to raytracer
        int[] point1 = new int[]{0, 1, 0};
        int[] point2 = new int[]{1, 2, 2};
        int[] point3 = new int[]{0, 0, 2};

        // Pigment pigment6 = rayTracer.createPigment(Color.black, "solid");
        // rayTracer.addTriangle(pigment6, finish3, point1, point2, point3);

        // TESTING ALEX
        Pigment aGradientPigment1 = rayTracer.createGradientPigment(point1, point1, point3, Color.black, Color.white);


        // adding a triangle to raytracer
        int[] p1 = new int[]{0, 2, 0};
        int[] p2 = new int[]{2, 4, 4};
        int[] p3 = new int[]{0, 0, 4};

        // Pigment pigment6 = rayTracer.createPigment(Color.black, "solid");
        // rayTracer.addTriangle(pigment6, finish3, point1, point2, point3);

        // TESTING ALEX
        Pigment aGradientPigment2 = rayTracer.createGradientPigment(p1, p2, p3, Color.yellow, Color.white);  
        rayTracer.addTriangle(aGradientPigment2, finish3, point1, point2, point3);

        // drawing image with 2 inputs: output file name, output file format
        rayTracer.draw("test05", "png");
    }
}
