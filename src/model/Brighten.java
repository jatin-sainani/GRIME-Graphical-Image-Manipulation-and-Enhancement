package model;

import controller.Image;
import controller.Pixel;
import controller.PixelImpl;

/**
 * The {@code Brighten} class implements the {@code ImageProcessing} interface
 * to increase the brightness of a specified image by a given increment.
 */
public class Brighten implements ImageProcessing {

  /**
   * Processes the specified image by increasing the brightness of each pixel
   * according to the provided increment value.
   *
   * @param args   an array of strings representing the processing commands, where:
   *               - args[1] is the increment/decrement value to brighten/darken the image,
   *               - args[2] is the name of the input image,
   *               - args[3] is the name for the output image.
   * @param images a map where the keys are image identifiers and the values are
   *               {@code Image} objects to be processed.
   * @return an array containing the newly created brightened {@code Image}.
   * @throws IllegalArgumentException if the args array does not contain exactly
   *                                   four elements.
   */
  @Override
  public Image[] process(String[] args, ImageDB images)
          throws IllegalArgumentException {
    if (args.length != 4) {
      throw new IllegalArgumentException("Brighten command requires a value, "
              + "an image name and output");
    }
    Image obj = images.get(args[2]);
    String increment = args[1];
    int increment_num = Integer.parseInt(increment);
    int width = obj.getWidth();
    int height = obj.getHeight();
    Image brightImage = obj.createImage(width, height);

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        double r = obj.getPixel(x,y).getRed();
        double g = obj.getPixel(x,y).getGreen();
        double b = obj.getPixel(x,y).getBlue();
        double final_r = Math.max(0, Math.min(255, r + increment_num));
        double final_g = Math.max(0, Math.min(255, g + increment_num));
        double final_b = Math.max(0, Math.min(255, b + increment_num));
        Pixel final_pixel = new PixelImpl(final_r,final_g,final_b);
        brightImage.setPixel(x, y, final_pixel);
      }
    }
    return new Image[]{brightImage};
  }

  /**
   * Extracts the name for the output image from the provided arguments.
   *
   * @param args an array of strings representing the processing commands, where
   *             args[3] is expected to be the output image name.
   * @return an array containing the name of the output image.
   */
  @Override
  public String[] getName(String[] args) {
    return new String[]{
            args[3]
    };
  }
}
