package model;

import controller.Image;
import controller.Pixel;
import controller.PixelImpl;

/**
 * The {@code DownScale} class implements the {@code ImageProcessing} interface
 * to downscale an image to a specified width and height.
 */
public class DownScale implements ImageProcessing {

  /**
   * Processes the specified image by downscaling it to the desired dimensions.
   *
   * @param args   an array of strings representing the processing commands, where:
   *               - args[1] is the name of the input image,
   *               - args[2] is the width of the output image,
   *               - args[3] is the height of the output image,
   *               - args[4] is the name of the output image.
   * @param images a map where the keys are image identifiers and the values are
   *               {@code Image} objects to be processed.
   * @return an array containing the downscaled {@code Image}.
   * @throws IllegalArgumentException if the args array does not contain exactly
   *                                  five elements, or if the dimensions are invalid.
   */
  @Override
  public Image[] process(String[] args, ImageDB images) throws IllegalArgumentException {
    if (args.length != 5) {
      throw new IllegalArgumentException("DownScale command requires input image name, "
              + "new width, new height, and output image name.");
    }

    String inputName = args[1];
    int newWidth;
    int newHeight;
    try {
      newWidth = Integer.parseInt(args[2]);
      newHeight = Integer.parseInt(args[3]);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Width and height must be integers.");
    }

    if (newWidth <= 0 || newHeight <= 0) {
      throw new IllegalArgumentException("Width and height must be positive integers.");
    }

    //String outputName = args[4];
    Image inputImage = images.get(inputName);
    int originalWidth = inputImage.getWidth();
    int originalHeight = inputImage.getHeight();

    Image downscaledImage = inputImage.createImage(newWidth, newHeight);

    for (int y = 0; y < newHeight; y++) {
      for (int x = 0; x < newWidth; x++) {
        double srcX = ((double) x / newWidth) * originalWidth;
        double srcY = ((double) y / newHeight) * originalHeight;

        int floorX = (int) Math.floor(srcX);
        int ceilX = (int) Math.ceil(srcX);
        int floorY = (int) Math.floor(srcY);
        int ceilY = (int) Math.ceil(srcY);

        floorX = Math.max(0, Math.min(floorX, originalWidth - 1));
        ceilX = Math.max(0, Math.min(ceilX, originalWidth - 1));
        floorY = Math.max(0, Math.min(floorY, originalHeight - 1));
        ceilY = Math.max(0, Math.min(ceilY, originalHeight - 1));

        Pixel a = inputImage.getPixel(floorX, floorY);
        Pixel b = inputImage.getPixel(ceilX, floorY);
        Pixel c = inputImage.getPixel(floorX, ceilY);
        Pixel d = inputImage.getPixel(ceilX, ceilY);

        double red = interpolate(srcX, srcY, floorX, ceilX, floorY, ceilY, a.getRed(),
                b.getRed(), c.getRed(), d.getRed());
        double green = interpolate(srcX, srcY, floorX, ceilX, floorY, ceilY, a.getGreen(),
                b.getGreen(), c.getGreen(), d.getGreen());
        double blue = interpolate(srcX, srcY, floorX, ceilX, floorY, ceilY, a.getBlue(),
                b.getBlue(), c.getBlue(), d.getBlue());

        Pixel newPixel = new PixelImpl(red, green, blue);
        downscaledImage.setPixel(x, y, newPixel);
      }
    }

    return new Image[]{downscaledImage};
  }

  /**
   * Performs interpolation to compute the color value at a given point.
   *
   * @param srcX   the floating-point x-coordinate in the source image.
   * @param srcY   the floating-point y-coordinate in the source image.
   * @param floorX the floor of the x-coordinate.
   * @param ceilX  the ceiling of the x-coordinate.
   * @param floorY the floor of the y-coordinate.
   * @param ceilY  the ceiling of the y-coordinate.
   * @param a      the color value at (floorX, floorY).
   * @param b      the color value at (ceilX, floorY).
   * @param c      the color value at (floorX, ceilY).
   * @param d      the color value at (ceilX, ceilY).
   * @return the interpolated color value.
   */
  private double interpolate(double srcX, double srcY, int floorX, int ceilX, int floorY,
                             int ceilY, double a, double b, double c, double d) {
    double xDiff = srcX - floorX;
    double yDiff = srcY - floorY;

    double m = b * xDiff + a * (1 - xDiff);
    double n = d * xDiff + c * (1 - xDiff);

    return n * yDiff + m * (1 - yDiff);
  }

  @Override
  public String[] getName(String[] args) {
    return new String[]{args[4]};
  }
}
