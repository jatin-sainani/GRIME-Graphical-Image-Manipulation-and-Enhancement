package model;

import controller.Image;
import controller.Pixel;
import controller.PixelImpl;

/**
 * The {@code Combine} class implements the {@code ImageProcessing} interface
 * to combine three separate color images (red, green, and blue) into a single
 * RGB image.
 */
public class Combine implements ImageProcessing {

  /**
   * Processes the specified red, green, and blue images by combining them
   * into a single RGB image.
   *
   * @param args   an array of strings representing the processing commands, where:
   *               - args[1] is the output image name,
   *               - args[2] is the name of the red image,
   *               - args[3] is the name of the green image,
   *               - args[4] is the name of the blue image.
   * @param images a map where the keys are image identifiers and the values are
   *               {@code Image} objects to be processed.
   * @return an array containing the newly created combined {@code Image}.
   * @throws IllegalArgumentException if the args array does not contain exactly
   *                                   five elements.
   */
  @Override
  public Image[] process(String[] args, ImageDB images)
          throws IllegalArgumentException {
    if (args.length != 5) {
      throw new IllegalArgumentException("Combine command requires three image name and output");
    }
    Image red = images.get(args[2]);
    Image green = images.get(args[3]);
    Image blue = images.get(args[4]);
    int width = red.getWidth();
    int height = red.getHeight();
    Image combinedImage =  red.createImage(red.getWidth(), red.getHeight());
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        double r = red.getPixel(j,i).getRed();
        double g = green.getPixel(j,i).getRed();
        double b = blue.getPixel(j,i).getRed();
        Pixel pixel = new PixelImpl(r,g,b);
        combinedImage.setPixel(j,i,pixel);
      }
    }
    return new Image[]{combinedImage};


  }

  /**
   * Extracts the name for the output image from the provided arguments.
   *
   * @param args an array of strings representing the processing commands, where
   *             args[1] is expected to be the output image name.
   * @return an array containing the name of the output image.
   */
  @Override
  public String[] getName(String[] args) {
    return new String[]{args[1]};
  }
}
