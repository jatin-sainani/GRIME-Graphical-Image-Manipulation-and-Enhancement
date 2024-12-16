package model;

import controller.Image;
import controller.Pixel;
import controller.PixelImpl;

abstract class ApplyChannel implements ImageProcessing {
  /**
   * Processes the specified image by extracting the blue channel and creating
   * a new image where all pixel values are set to the blue component.
   *
   * @param args   an array of strings representing the processing commands, where:
   *               - args[1] is the name of the input image,
   *               - args[2] is the name for the output image.
   * @param images a map where the keys are image identifiers and the values are
   *               {@code Image} objects to be processed.
   * @return an array containing the newly created blue-tinted {@code Image}.
   * @throws IllegalArgumentException if the args array does not contain exactly
   *                                  three elements.
   */
  @Override
  public Image[] process(String[] args, ImageDB images)
      throws IllegalArgumentException {
    if (args.length != 3 && args.length != 5 && args.length != 4) {
      throw new IllegalArgumentException("grayscale commands requires an image name and output");
    }
    Image obj = images.get(args[1]);
    int width = obj.getWidth();
    int height = obj.getHeight();
    Image resultImage = obj.createImage(obj.getWidth(), obj.getHeight());
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        double[] channel = getChannel(obj.getPixel(j, i));
        //double b = obj.getPixel(j, i).getBlue();
        Pixel pixel = new PixelImpl(channel[0], channel[1], channel[2]);
        resultImage.setPixel(j, i, pixel);
      }
    }

    //Mask Image
    if (args.length == 4) {
      Image maskImage = images.get(args[2]);
      Masking mask = new Masking();
      resultImage = mask.applyMask(obj, resultImage, maskImage);
    }

    //Split functionality.
    if ((args.length == 5) && (args[3].equals("split"))) {
      int split = (Integer.parseInt(args[4])) * width / 100;
      VerticalLineSplit vSplit = new VerticalLineSplit();
      resultImage = vSplit.split(obj, resultImage, split);
    }

    return new Image[]{resultImage};
  }

  /**
   * Extracts the name for the output image from the provided arguments.
   *
   * @param args an array of strings representing the processing commands, where
   *             args[2] is expected to be the output image name.
   * @return an array containing the name of the output image.
   */
  public String[] getName(String[] args) {

    if (args.length == 4) {
      return new String[]{args[3]};
    }
    return new String[]{args[2]};
  }

  abstract protected double[] getChannel(Pixel pixel);
}
