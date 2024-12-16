package model;

import controller.Image;
import controller.Pixel;
import controller.PixelImpl;

/**
 * The {@code flipVertical} class implements the {@code ImageProcessing} interface
 * to flip an image vertically.
 */
public class FlipVertical implements ImageProcessing {

  /**
   * Processes the specified image by flipping it vertically.
   *
   * @param args   an array of strings representing the processing commands, where:
   *               - args[1] is the name of the image to be flipped,
   *               - args[2] is the name of the output image.
   * @param images a map where the keys are image identifiers and the values are
   *               {@code Image} objects to be processed.
   * @return an array containing the vertically flipped {@code Image}.
   * @throws IllegalArgumentException if the args array does not contain exactly
   *                                  three elements.
   */
  @Override
  public Image[] process(String[] args, ImageDB images)
          throws IllegalArgumentException {
    if (args.length != 3) {
      throw new IllegalArgumentException("flipVertical command requires an image name and output");
    }
    Image obj = images.get(args[1]);
    int width = obj.getWidth();
    int height = obj.getHeight();
    Image verticalImage = obj.createImage(width, height);

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        double r = obj.getPixel(x, y).getRed();
        double g = obj.getPixel(x, y).getGreen();
        double b = obj.getPixel(x, y).getBlue();
        Pixel pixel = new PixelImpl(r, g, b);
        //double[] rgb = obj.getPixel(, )[y][x];
        verticalImage.setPixel(x, height - 1 - y, pixel);
      }
    }
    return new Image[]{verticalImage};
  }

  /**
   * Extracts the name for the output image from the provided arguments.
   *
   * @param args an array of strings representing the processing commands, where
   *             args[2] is expected to be the output image name.
   * @return an array containing the name of the output image.
   */
  @Override
  public String[] getName(String[] args) {
    return new String[]{args[2]};
  }
}
