package controller;

/**
 * Represents a generic image that contains a grid of pixels.
 * This interface defines the basic operations for manipulating and retrieving
 * information from an image, such as getting dimensions, retrieving or setting
 * individual pixel values, and creating new images.
 */
public interface Image {

  /**
   * Returns the width of the image.
   *
   * @return the width of the image
   */
  int getWidth();

  /**
   * Returns the height of the image.
   *
   * @return the height of the image
   */
  int getHeight();

  /**
   * Retrieves the pixel at the specified (x, y) coordinates.
   *
   * @param x the x-coordinate of the pixel to retrieve
   * @param y the y-coordinate of the pixel to retrieve
   * @return the {@link Pixel} object at the specified coordinates
   * @throws IllegalArgumentException if the coordinates are out of bounds
   */
  Pixel getPixel(int x, int y);

  /**
   * Sets the pixel at the specified (x, y) coordinates to the given {@link Pixel}.
   *
   * @param x the x-coordinate where the pixel will be set
   * @param y the y-coordinate where the pixel will be set
   * @param rgb the {@link Pixel} object to set at the specified coordinates
   * @throws IllegalArgumentException if the coordinates are out of bounds
   */
  void setPixel(int x, int y, Pixel rgb);

  /**
   * Creates a new image with the specified width and height.
   * This method should return a new instance of an image based on the type of
   * file type specified.
   * @param width  the width of the new image
   * @param height the height of the new image
   * @return a new {@link Image} object with the specified dimensions
   */
  Image createImage(int width, int height);
}


