package controller;

/**
 * The {@code Pixel} interface defines the basic operations for accessing and retrieving
 * the color components of a pixel in an image. A pixel is represented by its
 * red, green, and blue (RGB) color values.
 */
public interface Pixel {

  /**
   * Gets the red color channel value of the pixel.
   *
   * @return the red color channel value, as a double between 0 and 255
   */
  double getRed();

  /**
   * Gets the green color channel value of the pixel.
   *
   * @return the green color channel value, as a double between 0 and 255
   */
  double getGreen();

  /**
   * Gets the blue color channel value of the pixel.
   *
   * @return the blue color channel value, as a double between 0 and 255
   */
  double getBlue();

  /**
   * Gets an array representing the RGB color values of the pixel.
   * The first element is the red value, the second is the green value,
   * and the third is the blue value.
   * @return a double array containing the RGB values, each between 0 and 255
   */
  double[] getRGB();

}
