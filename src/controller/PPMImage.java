package controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * The {@code PPMImage} class represents an image in the PPM (Portable Pixmap) format.
 * It provides methods for loading and saving PPM images, and manipulating pixel data.
 * This class extends {@link AbstractImage}, implementing PPM-specific functionality.
 */
public class PPMImage extends AbstractImage {

  private int maxColorValue;

  /**
   * Constructs a new {@code PPMImage} object with the specified width and height.
   *
   * @param width  the width of the image in pixels
   * @param height the height of the image in pixels
   * @throws IllegalArgumentException if the width or height are non-positive
   */
  public PPMImage(int width, int height) {

    super(width, height);
    if (width <= 0 || height <= 0) {
      throw new IllegalArgumentException("Width and height must be greater than zero.");
    }
  }

  /**
   * Creates a new {@code PPMImage} instance with the specified width and height.
   *
   * @param width  the width of the new image in pixels
   * @param height the height of the new image in pixels
   * @return a new {@code PPMImage} object with the given dimensions
   * @throws IllegalArgumentException if the width or height are non-positive
   */
  @Override
  public Image createImage(int width, int height) {
    if (width <= 0 || height <= 0) {
      throw new IllegalArgumentException("Width and height must be greater than zero.");
    }
    return new PPMImage(width, height);
  }

  /**
   * Loads a PPM image from the specified file path and returns an {@code Image} object.
   * This method reads the pixel data from a PPM file (in P3 format) and constructs an
   * {@code Image} object using the pixel values.
   *
   * @param path the file path to the PPM image
   * @return an {@code Image} object representing the loaded PPM image
   * @throws IOException              if there is an error reading the file
   * @throws IllegalArgumentException if the PPM format is invalid
   */

  protected static Image load(String path) throws IOException {
    File file = new File(path);
    if (!file.exists()) {
      throw new IOException("File not found: " + path);
    }
    Scanner scanner = new Scanner(file);

    // Read the magic number (P3)
    String magicNumber = scanner.next();
    if (!magicNumber.equals("P3")) {
      throw new IllegalArgumentException("Unsupported PPM format: " + magicNumber);
    }

    // Skip comments (if any)
    while (scanner.hasNext("#")) {
      scanner.nextLine();
    }

    // Read image width, height, and the maximum color value (usually 255)
    int width = scanner.nextInt();
    int height = scanner.nextInt();
    if (width <= 0 || height <= 0) {
      throw new IllegalArgumentException("Width and height must be greater than zero.");
    }
    int maxColorValue = scanner.nextInt();
    if (maxColorValue > 255) {
      throw new IllegalArgumentException("Max color value must be 255.");
    }
    // Create a new Controller.PPMImage object
    PPMImage image = new PPMImage(width, height);

    // Read pixel data (R, G, B values for each pixel)
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        double[] rgb = new double[3];
        rgb[0] = scanner.nextInt();  // Model.Red
        rgb[1] = scanner.nextInt();  // Model.Green
        rgb[2] = scanner.nextInt();// Model.Blue
        if (rgb[0] < 0 || rgb[1] < 0 || rgb[2] < 0 || rgb[0] > 255 || rgb[1] > 255
                || rgb[2] > 255) {
          throw new IllegalArgumentException("RGB values must be between 0 and 255.");
        }
        Pixel p = new PixelImpl(rgb[0], rgb[1], rgb[2]);
        image.setPixel(x, y, p);
      }
    }

    scanner.close();
    return image;// Implement PPM loading logic here
  }

  /**
   * Saves the given {@code Image} object to the specified file path in PPM format.
   * This method writes the pixel data in the P3 format (plain text) to the specified file.
   *
   * @param path the file path where the image should be saved
   * @param img  the {@code Image} object to save
   * @throws IOException              if there is an error writing the file
   * @throws IllegalArgumentException if the image is null or the pixel data is invalid
   */
  protected static void save(String path,Image img) throws IOException {
    if (img == null) {
      throw new IllegalArgumentException("Cannot save a null image.");
    }
    FileWriter writer = new FileWriter(path);
    int height = img.getHeight();
    int width = img.getWidth();

    // Write the header (P3)
    writer.write("P3\n");
    writer.write(width + " " + height + "\n");
    writer.write("255\n");  // Max color value is 255

    // Write pixel data (R, G, B values for each pixel)
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        double red = img.getPixel(x, y).getRed();
        double green = img.getPixel(x, y).getGreen();
        double blue = img.getPixel(x, y).getBlue();

        if (red < 0 || green < 0 || blue < 0 || red > 255 || green > 255 || blue > 255) {
          throw new IllegalArgumentException("RGB values must be between 0 and 255.");
        }

        writer.write((int) red + " " + (int) green + " " + (int) blue + " ");
      }
      writer.write("\n");
    }

    writer.close();// Implement PPM saving logic here
  }
}

