package model;

import controller.Image;

/**
 * The {@code Split} class implements the {@code ImageProcessing} interface
 * to separate an image into its RGB components, creating three new images
 * for the red, green, and blue channels.
 */
public class Split implements ImageProcessing {


  /**
   * Processes the specified image by splitting it into its red, green,
   * and blue components and returns the newly created images.
   *
   * <p>The expected command format is:
   * <pre>
   * rgb-split image-name dest-image-name-red dest-image-name-green dest-image-name-blue
   * </pre>
   *
   * @param args   an array of strings representing the processing commands, where:
   *               - args[1] is the name of the image to process,
   *               - args[2] is the name of the output image for the red component,
   *               - args[3] is the name of the output image for the green component,
   *               - args[4] is the name of the output image for the blue component.
   * @param images a map where the keys are image identifiers and the values are
   *               {@code Image} objects to be processed.
   * @return an array containing the three new RGB {@code Image} objects.
   * @throws IllegalArgumentException if the args array does not contain exactly
   *                                  five elements.
   */
  @Override
  public Image[] process(String[] args, ImageDB images)
          throws IllegalArgumentException {
    if (args.length != 5) {
      throw new IllegalArgumentException("RGB-Split command requires an image name "
              + "and three output names.");
    }

    String[] red = {"red-component", args[1], args[2]};
    String[] green = {"green-component", args[1], args[3]};
    String[] blue = {"blue-component", args[1], args[4]};
    Image[] redImage = new Red().process(red, images);
    Image[] greenImage = new Green().process(green, images);
    Image[] blueImage = new Blue().process(blue, images);
    Image[] split = {redImage[0], greenImage[0], blueImage[0]};
    return split;
  }

  /**
   * Extracts the names for the output images from the provided arguments.
   *
   * @param args an array of strings representing the processing commands, where
   *             args[2], args[3], and args[4] are expected to be the output
   *             image names for the red, green, and blue components, respectively.
   * @return an array containing the names of the output images for the RGB components.
   */
  @Override
  public String[] getName(String[] args) {
    String[] output = new String[3];
    output[0] = args[2];
    output[1] = args[3];
    output[2] = args[4];
    return output;
  }
}
