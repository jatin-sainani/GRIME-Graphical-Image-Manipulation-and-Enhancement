package model;

import controller.Pixel;

/**
 * The {@code Intensity} class implements the {@code ImageProcessing} interface
 * to create a new image that represents the intensity of the original image,
 * calculated as the average of the red, green, and blue components.
 */
public class Intensity extends ApplyChannel {

  /**
   * Abstract method implementation of getChannel that returns the applicable channel pixel.
   *
   * @param pixel pixel to get the channel
   * @return resultant intensity channel
   */
  @Override
  protected double[] getChannel(Pixel pixel) {
    double[] channel = new double[3];
    channel[0] = pixel.getRed();
    channel[1] = pixel.getGreen();
    channel[2] = pixel.getBlue();
    double intensity = (channel[0] + channel[1] + channel[2]) / 3;
    channel[0] = intensity;
    channel[1] = channel[0];
    channel[2] = channel[1];
    return channel;
  }
}
