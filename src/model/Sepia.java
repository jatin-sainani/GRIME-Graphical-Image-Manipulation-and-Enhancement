package model;

import controller.Pixel;

/**
 * The {@code Sepia} class implements the {@code ImageProcessing} interface
 * to apply a sepia tone effect to an image, creating a new image that reflects
 * the classic sepia filter.
 */
public class Sepia extends ApplyChannel {
  @Override
  protected double[] getChannel(Pixel pixel) {

    double[] channel = new double[3];
    double red = pixel.getRed();
    double green = pixel.getGreen();
    double blue = pixel.getBlue();
    double newRed = Math.min(255, (0.393 * red) + (0.769 * green) + (0.189 * blue));
    double newGreen = Math.min(255, (0.349 * red) + (0.686 * green) + (0.168 * blue));
    double newBlue = Math.min(255, (0.272 * red) + (0.534 * green) + (0.131 * blue));
    channel[0] = newRed;
    channel[1] = newGreen;
    channel[2] = newBlue;
    return channel;

  }
}
