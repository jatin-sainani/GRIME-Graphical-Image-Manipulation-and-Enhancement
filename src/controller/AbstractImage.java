package controller;

import java.util.Arrays;
import java.util.Objects;

/**
 * An abstract class representing an image with a width, height, and pixels.
 * This class provides basic implementations for common image operations like
 * retrieving dimensions and pixels, setting pixel values, and checking for
 * equality between images. Subclasses implements the createImage
 * method to provide specific image type creations.
 */

public abstract class AbstractImage implements Image {
  private final int width;
  private final int height;
  private final Pixel[][] pixels;// Store RGB values

  /**
   * Constructs an {@code AbstractImage} with the specified width and height.
   *
   * @param width  the width of the image (must be greater than 0)
   * @param height the height of the image (must be greater than 0)
   * @throws IllegalArgumentException if width or height is less than or equal to 0
   */

  public AbstractImage(int width, int height) {
    if (width <= 0 || height <= 0) {
      throw new IllegalArgumentException("Width and height must be greater than zero.");
    }
    this.width = width;
    this.height = height;
    this.pixels = new Pixel[height][width];
  }

  /**
   * Creates a new image of the specified width and height.
   * This method must be implemented by subclasses according to file type.
   *
   * @param width  the width of the new image
   * @param height the height of the new image
   * @return the newly created image
   */
  @Override
  public abstract Image createImage(int width, int height);

  /**
   * Returns the width of the image.
   *
   * @return the width of the image
   */
  @Override
  public int getWidth() {

    return width;
  }

  /**
   * Returns the height of the image.
   *
   * @return the height of the image
   */
  @Override
  public int getHeight() {

    return height;
  }

  /**
   * Retrieves the pixel at the specified (x, y) coordinates.
   *
   * @param x the x-coordinate of the pixel
   * @param y the y-coordinate of the pixel
   * @return the pixel at the specified coordinates
   * @throws IllegalArgumentException if the coordinates are out of bounds
   */
  @Override
  public Pixel getPixel(int x, int y) {
    if (x < 0 || y < 0 || x >= width || y >= height) {
      throw new IllegalArgumentException("Pixel coordinates are out of bounds.");
    }
    return pixels[y][x];
  }

  /**
   * Sets the pixel at the specified (x, y) coordinates.
   *
   * @param x the x-coordinate where the pixel will be set
   * @param y the y-coordinate where the pixel will be set
   * @param pixel the pixel to set at the specified coordinates
   * @throws IllegalArgumentException if the coordinates are out of bounds
   */
  public void setPixel(int x, int y, Pixel pixel) {
    if (x < 0 || y < 0 || x >= width || y >= height) {
      throw new IllegalArgumentException("Pixel coordinates are out of bounds.");
    }
    pixels[y][x] = pixel;
  }

  /**
   * Compares this image with the specified object for equality.
   * Two images are considered equal if they have the same width, height,
   * and identical pixel values.
   * @param o the object to be compared for equality with this image
   * @return {@code true} if the specified object is the exact object
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof AbstractImage)) {
      return false;
    }
    AbstractImage that = (AbstractImage) o;
    return width == that.width && height == that.height && Objects.deepEquals(pixels, that.pixels);
  }

  /**
   * Returns a hash code value for this image. The hash code is based on
   * the width, height, and the pixel data of the image.
   *
   * @return the hash code value for this image
   */
  @Override
  public int hashCode() {
    return Objects.hash(width, height, Arrays.deepHashCode(pixels));
  }


}
