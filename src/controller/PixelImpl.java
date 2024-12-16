package controller;

/**
 * The {@code PixelImpl} class represents a single pixel in an image,
 * storing its RGB (Red, Green, Blue) color values.
 * Each color channel is represented by a double value ranging from 0 to 255.
 * This class provides methods to get and set individual color channels,
 * as well as utility methods to handle all channels at once.
 */
public class PixelImpl implements Pixel {
  private double red;
  private double green;
  private double blue;

  /**
   * Constructs a {@code PixelImpl} object with the specified red, green, and blue values.
   * Each value must be in the range of 0 to 255.
   *
   * @param red   the red component of the pixel
   * @param green the green component of the pixel
   * @param blue  the blue component of the pixel
   * @throws IllegalArgumentException if any of the RGB values are not within the range 0 to 255
   */

  public PixelImpl(double red, double green, double blue) {
    if (red < 0 || red > 255 || green < 0 || green > 255 || blue < 0 || blue > 255) {
      throw new IllegalArgumentException("RGB values must be between 0 and 255.");
    }
    this.red = red;
    this.green = green;
    this.blue = blue;
  }

  /**
   * Returns the red component of this pixel.
   *
   * @return the red value, between 0 and 255
   */
  public double getRed() {
    return red;
  }

  /**
   * Returns the green component of this pixel.
   *
   * @return the green value, between 0 and 255
   */
  public double getGreen() {
    return green;
  }

  /**
   * Returns the blue component of this pixel.
   *
   * @return the blue value, between 0 and 255
   */
  public double getBlue() {
    return blue;
  }


  /**
   * Sets the red component of this pixel.
   *
   * @param red the new red value, between 0 and 255
   * @throws IllegalArgumentException if the red value is not within the range 0 to 255
   */
  public void setRed(double red) {
    this.red = red;
  }

  /**
   * Sets the green component of this pixel.
   *
   * @param green the new green value, between 0 and 255
   * @throws IllegalArgumentException if the green value is not within the range 0 to 255
   */
  public void setGreen(double green) {
    this.green = green;
  }

  /**
   * Sets the blue component of this pixel.
   *
   * @param blue the new blue value, between 0 and 255
   * @throws IllegalArgumentException if the blue value is not within the range 0 to 255
   */
  public void setBlue(double blue) {
    this.blue = blue;
  }

  /**
   * Returns an array containing the red, green, and blue channel values of this pixel.
   *
   * @return a double array of rgb
   */
  public double[] getRGB() {
    return new double[] {red, green, blue};
  }

  /**
   * Sets the red, green, and blue channel values of this pixel.
   * Each value must be between 0 and 255.
   *
   * @param red   the red channel value (0-255)
   * @param green the green channel value (0-255)
   * @param blue  the blue channel value (0-255)
   * @throws IllegalArgumentException if any of the RGB values are out of the 0-255 range
   */
  public void setRGB(double red, double green, double blue) {
    this.red = red;
    this.green = green;
    this.blue = blue;
  }
}

