package model;

import controller.Pixel;

/**
 * The {@code Value} class implements the {@code ImageProcessing} interface
 * to create a new image based on the maximum RGB value of each pixel
 * from the original image.
 */
public class Value extends ApplyChannel {

  /**
   * Abstract method implementation of getChannel that returns the applicable channel pixel.
   *
   * @param pixel pixel to get the channel
   * @return resultant value channel
   */
  @Override
  protected double[] getChannel(Pixel pixel) {
    double[] channel = new double[3];
    channel[0] = pixel.getRed();
    channel[1] = pixel.getGreen();
    channel[2] = pixel.getBlue();
    double value = Math.max(Math.max(channel[0], channel[1]), channel[2]);
    channel[0] = value;
    channel[1] = channel[0];
    channel[2] = channel[1];
    return channel;
  }
}
