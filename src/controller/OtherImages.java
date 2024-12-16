package controller;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * The {@code JPGImage} class represents an image in JPG format. It provides methods to load
 * and save JPG images from and to disk, and to manipulate pixel data.
 * This class extends {@link AbstractImage}, implementing the required methods
 * for handling JPG images.
 */
public class OtherImages extends AbstractImage {

  /**
   * Constructs a new {@code JPGImage} object with the specified width and height.
   *
   * @param width  the width of the image in pixels
   * @param height the height of the image in pixels
   * @throws IllegalArgumentException if the width or height are non-positive
   */
  public OtherImages(int width, int height) {
    super(width, height);
  }

  /**
   * Creates a new {@code JPGImage} instance with the specified dimensions.
   *
   * @param width  the width of the new image in pixels
   * @param height the height of the new image in pixels
   * @return a new {@code JPGImage} object with the given width and height
   */
  @Override
  public Image createImage(int width, int height) {
    return new OtherImages(width, height);
  }

  /**
   * Loads a JPG image from the specified file path and returns an {@code Image} object.
   * The method reads the image's pixel data and constructs an {@code Image} object using
   * the pixel values from the JPG file.
   *
   * @param path the file path to the JPG image
   * @return an {@code Image} object representing the loaded JPG image
   * @throws IOException if there is an error reading the image file
   */
  protected static Image load(String path) throws IOException {
    try {
      BufferedImage img = ImageIO.read(new File(path));
      int width = img.getWidth();
      int height = img.getHeight();
      OtherImages image = new OtherImages(width, height);

      boolean hasAlpha = img.getColorModel().hasAlpha();

      for (int y = 0; y < height; y++) {
        for (int x = 0; x < width; x++) {
          int pixel = img.getRGB(x, y);

          int red = (pixel >> 16) & 0xFF;
          int green = (pixel >> 8) & 0xFF;
          int blue = pixel & 0xFF;
          Pixel p = new PixelImpl(red, green, blue);

          image.setPixel(x, y, p);
        }
      }
      return image;
    } catch (IOException e) {
      System.err.println("Error loading image: " + e.getMessage());
      return null;
    }
  }

  /**
   * Saves the given {@code Image} object to the specified file path in JPG format.
   * The method converts the pixel data from the {@code Image} object into a BufferedImage and
   * writes it to disk.
   *
   * @param path  the file path where the image should be saved
   * @param image the {@code Image} object to save
   * @throws IOException if there is an error saving the image file
   */
  protected static void save(String path, Image image) throws IOException {
    int width = image.getWidth();
    int height = image.getHeight();
    BufferedImage moddedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        int r = (int) image.getPixel(x, y).getRed();
        int g = (int) image.getPixel(x, y).getGreen();
        int b = (int) image.getPixel(x, y).getBlue();

        //need to clamp
        r = Math.min(255, Math.max(0, r));
        g = Math.min(255, Math.max(0, g));
        b = Math.min(255, Math.max(0, b));

        // Model.Combine the R, G, B values into a single int for setRGB()
        int rgb = (r << 16) | (g << 8) | b;

        moddedImage.setRGB(x, y, rgb);
      }
    }
    try {
      File outputFile = new File(path);
      String extension = outputFile.getName().substring(outputFile.getName().lastIndexOf(".") + 1);
      ImageIO.write(moddedImage, extension, outputFile);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}

