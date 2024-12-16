package model;

import controller.Image;
import controller.Pixel;
import controller.PixelImpl;

/**
 * Abstract class to apply a kernel to an image.
 */
abstract class ApplyKernel implements ImageProcessing {


  /**
   * Applies a convolution kernel to the given image to sharpen it.
   *
   * @param img    the input image to which the kernel will be applied.
   * @param kernel a 2D array representing the convolution kernel to use for sharpening.
   * @return a new {@code Image} object with the sharpened effect applied.
   */
  protected Image applyKernel(Image img, double[][] kernel) {
    int width = img.getWidth();
    int height = img.getHeight();
    Image result = img.createImage(width, height);

    int kernelSize = kernel.length;
    int kernelOffset = kernelSize / 2;

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        double[] newRGB = new double[3];  // To store the new RGB values

        for (int ky = -kernelOffset; ky <= kernelOffset; ky++) {
          for (int kx = -kernelOffset; kx <= kernelOffset; kx++) {
            int imgX = Math.min(Math.max(x + kx, 0), width - 1); // Clamp to image borders
            int imgY = Math.min(Math.max(y + ky, 0), height - 1);
            Pixel pixel = img.getPixel(imgX, imgY);

            double[] pixelRGB = pixel.getRGB();

            for (int i = 0; i < 3; i++) {
              newRGB[i] += pixelRGB[i] * kernel[ky + kernelOffset][kx + kernelOffset];
            }
          }
        }

        double[] finalRGB = new double[3];
        for (int i = 0; i < 3; i++) {
          finalRGB[i] = Math.max(0, Math.min(255, newRGB[i]));  // Clamp values to [0, 255]
        }
        double r = finalRGB[0];
        double g = finalRGB[1];
        double b = finalRGB[2];
        PixelImpl newPixel = new PixelImpl(r, g, b);
        result.setPixel(x, y, newPixel);
      }
    }
    return result;
  }


  @Override
  public Image[] process(String[] args, ImageDB images)
      throws IllegalArgumentException {
    if (args.length != 3 && args.length != 5 && args.length != 4) {
      throw new IllegalArgumentException("Blur and Sharpen command requires an image name"
          + " and output");
    }
    double[][] kernel = getKernel();
    Image obj = images.get(args[1]);

    Image resultImage = applyKernel(obj, kernel);

    //Mask Image
    if ((args.length == 4)) {
      Image maskImage = images.get(args[2]);
      Masking mask = new Masking();
      // Combine source and blurred images using the mask
      resultImage = mask.applyMask(obj, resultImage, maskImage);
    }
    //Split functionality.
    if ((args.length == 5) && (args[3].equals("split"))) {
      int split = (Integer.parseInt(args[4])) * obj.getWidth() / 100;
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
  @Override
  public String[] getName(String[] args) {
    if ((args.length == 4)) {
      return new String[]{args[3]};
    }
    return new String[]{args[2]};
  }

  abstract protected double[][] getKernel();

}
