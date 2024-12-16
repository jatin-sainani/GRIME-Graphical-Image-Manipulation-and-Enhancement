package model;

import controller.Image;
import controller.Pixel;
import controller.PixelImpl;

/**
 * The {@code ColorCorrect} class implements the {@code ImageProcessing} interface
 * to create a new Color Corrected version of the original image.
 */
public class ColorCorrect implements ImageProcessing {

  @Override
  public Image[] process(String[] args, ImageDB images)
      throws IllegalArgumentException {
    if (args.length != 3 && args.length != 5) {
      throw new IllegalArgumentException("Color-correct command requires an image name"
          + " and output");
    }

    Image inputImage = images.get(args[1]);
    int[][] redHist = calculateHistogram(inputImage, "red");
    int[][] greenHist = calculateHistogram(inputImage, "green");
    int[][] blueHist = calculateHistogram(inputImage, "blue");

    int redPeak = findMeaningfulPeak(redHist);
    int greenPeak = findMeaningfulPeak(greenHist);
    int bluePeak = findMeaningfulPeak(blueHist);

    int avgPeak = (redPeak + greenPeak + bluePeak) / 3;

    int redOffset = avgPeak - redPeak;
    int greenOffset = avgPeak - greenPeak;
    int blueOffset = avgPeak - bluePeak;

    Image correctedImage = adjustColors(inputImage, redOffset, greenOffset, blueOffset);

    //Split functionality.
    if ((args.length == 5) && (args[3].equals("split"))) {
      int split = (Integer.parseInt(args[4])) * correctedImage.getWidth() / 100;
      VerticalLineSplit vSplit = new VerticalLineSplit();
      correctedImage = vSplit.split(inputImage, correctedImage, split);
    }

    return new Image[]{correctedImage};
  }

  @Override
  public String[] getName(String[] args) {
    return new String[]{args[2]};
  }

  private int[][] calculateHistogram(Image img, String channel) {
    int[][] histogram = new int[256][1];
    for (int y = 0; y < img.getHeight(); y++) {
      for (int x = 0; x < img.getWidth(); x++) {
        Pixel pixel = img.getPixel(x, y);
        int value = 0;
        switch (channel) {
          case "red":
            value = (int) pixel.getRed();
            break;
          case "green":
            value = (int) pixel.getGreen();
            break;
          case "blue":
            value = (int) pixel.getBlue();
            break;
          default:
            break;
        }
        histogram[value][0]++;
      }
    }
    return histogram;
  }

  private int findMeaningfulPeak(int[][] histogram) {
    int peakValue = 0;
    int peakFrequency = 0;
    for (int i = 11; i < 245; i++) {
      if (histogram[i][0] > peakFrequency) {
        peakFrequency = histogram[i][0];
        peakValue = i;
      }
    }
    return peakValue;
  }

  private Image adjustColors(Image img, int redOffset, int greenOffset, int blueOffset) {
    Image correctedImage = img.createImage(img.getWidth(), img.getHeight());

    for (int y = 0; y < img.getHeight(); y++) {
      for (int x = 0; x < img.getWidth(); x++) {
        Pixel pixel = img.getPixel(x, y);

        int correctedRed = (int) (Math.max(0, Math.min(255, pixel.getRed() + redOffset)));
        int correctedGreen = (int) (Math.max(0, Math.min(255, pixel.getGreen() + greenOffset)));
        int correctedBlue = (int) (Math.max(0, Math.min(255, pixel.getBlue() + blueOffset)));

        correctedImage.setPixel(x, y, new PixelImpl(correctedRed, correctedGreen, correctedBlue));
      }
    }

    return correctedImage;
  }
}
