package model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import controller.Image;
import controller.Pixel;
import controller.PixelImpl;

/**
 * The {@code Histogram} class implements the {@code ImageProcessing} interface
 * to generate a histogram visualization for an image's red, green, and blue channels.
 */
public class Histogram implements ImageProcessing {

  /**
   * Creates a histogram image of size 256x256 representing the RGB channels of the
   * given image.
   *
   * @param img the input {@code Image} whose histogram will be generated.
   * @return a new {@code Image} object representing the histogram visualization.
   */
  private Image generateHistogramImage(Image img) {
    int width = 256;
    int height = 256;

    BufferedImage histogramImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    Graphics2D g = histogramImage.createGraphics();
    Image result = img.createImage(width, height);

    g.setColor(Color.WHITE);
    g.fillRect(0, 0, width, height);

    // Draw grid pattern
    g.setColor(Color.LIGHT_GRAY);
    int gridSpacing = 32;
    for (int i = gridSpacing; i < width; i += gridSpacing) {
      g.drawLine(i, 0, i, height);  // Vertical lines
      g.drawLine(0, i, width, i);   // Horizontal lines
    }

    // Initialize histograms for each channel
    int[] redHist = new int[256];
    int[] greenHist = new int[256];
    int[] blueHist = new int[256];

    // Fill histograms with pixel data
    for (int y = 0; y < img.getHeight(); y++) {
      for (int x = 0; x < img.getWidth(); x++) {
        int red = (int) img.getPixel(x, y).getRed();
        int green = (int) img.getPixel(x, y).getGreen();
        int blue = (int) img.getPixel(x, y).getBlue();
        redHist[red]++;
        greenHist[green]++;
        blueHist[blue]++;
      }
    }

    // Normalize histograms to fit within image height
    int max = Math.max(Math.max(getMaxValue(redHist), getMaxValue(greenHist))
        , getMaxValue(blueHist));
    for (int i = 0; i < 256; i++) {
      redHist[i] = (int) ((redHist[i] / (double) max) * height);
      greenHist[i] = (int) ((greenHist[i] / (double) max) * height);
      blueHist[i] = (int) ((blueHist[i] / (double) max) * height);
    }

    // Draw histograms
    g.setColor(Color.RED);
    drawLineGraph(g, redHist, width, height);
    g.setColor(Color.GREEN);
    drawLineGraph(g, greenHist, width, height);
    g.setColor(Color.BLUE);
    drawLineGraph(g, blueHist, width, height);

    g.dispose();
    return convertToCustomImage(histogramImage, result);
  }

  private int getMaxValue(int[] histogram) {
    int max = 0;
    for (int value : histogram) {
      max = Math.max(max, value);
    }
    return max;
  }

  private void drawLineGraph(Graphics2D g, int[] hist, int width, int height) {
    for (int i = 1; i < hist.length; i++) {
      g.drawLine(i - 1, height - hist[i - 1], i, height - hist[i]);
    }
  }

  @Override
  public Image[] process(String[] args, ImageDB images)
      throws IllegalArgumentException {
    if (args.length != 3) {
      throw new IllegalArgumentException("Histogram command requires an image name and" +
          " output name.");
    }
    Image img = images.get(args[1]);
    return new Image[]{generateHistogramImage(img)};
  }

  @Override
  public String[] getName(String[] args) {
    return new String[]{args[2]};
  }


  // Method to convert BufferedImage to your Image type
  private static Image convertToCustomImage(BufferedImage bufferedImage, Image img) {
    int width = bufferedImage.getWidth();
    int height = bufferedImage.getHeight();
    Image customImage = img.createImage(width, height);

    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        int rgb = bufferedImage.getRGB(x, y);

        int red = (rgb >> 16) & 0xFF;
        int green = (rgb >> 8) & 0xFF;
        int blue = rgb & 0xFF;

        Pixel pixel = new PixelImpl(red, green, blue);
        customImage.setPixel(x, y, pixel);
      }
    }
    return customImage;
  }

}



