package raytracer.pigments;

/** 
 * Finish determines whether or not the pigment is shiny or reflective
 */

public class Finish {
	public float ambience, diffuse, specular, shiny, reflection, transparancy, indexOfReflection;

	/**
	 *
	 * @param ambience amount of ambient light reflected
	 * @param diffuse strength of diffuse reflection
	 * @param specular strength of specular reflection
	 * @param shiny how shiny it is
	 * @param reflection how reflective it is
	 * @param transparancy how transparent it is
	 * @param indexOfReflection index of reflection
	 *
	 */


	public Finish(float ambience, float diffuse, float specular, float shiny, float reflection, float transparancy, float indexOfReflection) {
		this.ambience = ambience;
		this.diffuse = diffuse;
		this.specular = specular;
		this.shiny = shiny;
		this.reflection = reflection;
		this.transparancy = transparancy;
		this.indexOfReflection = indexOfReflection;
	}

	/**
	 * @return true if it's reflective
	 * */

	public boolean isReflective() {
		return reflection > 0;
	}

	/**
	 *
	 * @return true if it's transparent
	 */

	public boolean isTransparent() {
		return transparancy > 0;
	}
}
