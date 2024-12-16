package model;

import controller.Image;
import controller.Pixel;
import controller.PixelImpl;

/**
 * This class takes in the image and adjusts the color levels.
 */
public class LevelsAdjust implements ImageProcessing {

  @Override
  public Image[] process(String[] args, ImageDB images)
      throws IllegalArgumentException {
    if ((args.length != 6) && (args.length != 8)) {
      throw new IllegalArgumentException("Levels-adjust command requires b, m, w values"
          + ", an image name, and output name.");
    }

    int b = Integer.parseInt(args[1]);
    int m = Integer.parseInt(args[2]);
    int w = Integer.parseInt(args[3]);

    if (b < 0 || m < 0 || w < 0 || b >= m || m >= w || w > 255) {
      throw new IllegalArgumentException("b, m, w values must be in ascending order and "
          + "between 0 and 255.");
    }

    Image inputImage = images.get(args[4]);
    if (inputImage == null) {
      throw new IllegalArgumentException("Image not found: " + args[4]);
    }

    double[] coefficients = computeCoefficients(b, m, w);
    double a = coefficients[0];
    double bCoeff = coefficients[1];
    double c = coefficients[2];

    Image adjustedImage = applyLevelsAdjustment(inputImage, a, bCoeff, c);

    //Split functionality.
    if ((args.length == 8) && (args[6].equals("split"))) {
      int split = (Integer.parseInt(args[4])) * adjustedImage.getWidth() / 100;
      VerticalLineSplit vSplit = new VerticalLineSplit();
      adjustedImage = vSplit.split(inputImage, adjustedImage, split);
    }


    return new Image[]{adjustedImage};
  }

  @Override
  public String[] getName(String[] args) {
    return new String[]{args[5]};
  }

  private double[] computeCoefficients(int b, int m, int w) {
    double computeA = Math.pow(b, 2) * (m - w) - b * (Math.pow(m, 2) - Math.pow(w, 2))
        + w * Math.pow(m, 2) - m * Math.pow(w, 2);
    double computeAa = -b * (128 - 255) + 128 * w - 255 * m;
    double computeAb = Math.pow(b, 2) * (128 - 255) + 255 * Math.pow(m, 2) - 128 * Math.pow(w, 2);
    double computeAc = Math.pow(b, 2) * (255 * m - 128 * w) - b * (255 * Math.pow(m, 2) - 128
        * Math.pow(w, 2));

    double a = computeAa / computeA;
    double bCoeff = computeAb / computeA;
    double c = computeAc / computeA;

    return new double[]{a, bCoeff, c};
  }

  private Image applyLevelsAdjustment(Image img, double a, double bCoeff, double c) {
    Image adjustedImage = img.createImage(img.getWidth(), img.getHeight());

    for (int y = 0; y < img.getHeight(); y++) {
      for (int x = 0; x < img.getWidth(); x++) {
        Pixel pixel = img.getPixel(x, y);

        double newRed = clamp(applyQuadratic(a, bCoeff, c, pixel.getRed()));
        double newGreen = clamp(applyQuadratic(a, bCoeff, c, pixel.getGreen()));
        double newBlue = clamp(applyQuadratic(a, bCoeff, c, pixel.getBlue()));

        adjustedImage.setPixel(x, y, new PixelImpl(newRed, newGreen, newBlue));
      }
    }
    return adjustedImage;
  }

  private double applyQuadratic(double a, double b, double c, double value) {
    return a * Math.pow(value, 2) + b * value + c;
  }

  private double clamp(double value) {
    return Math.max(0, Math.min(255, value));
  }
}

