package model;

import controller.Pixel;

/**
 * The {@code Green} class implements the {@code ImageProcessing} interface
 * to create a new image that consists of only the green component of the original image.
 */
public class Green extends ApplyChannel {

  /**
   * Abstract method implementation of getChannel that returns the applicable channel pixel.
   *
   * @param pixel pixel to get the channel
   * @return resultant green channel
   */
  @Override
  protected double[] getChannel(Pixel pixel) {
    double[] channel = new double[3];
    channel[0] = pixel.getGreen();
    channel[1] = channel[0];
    channel[2] = channel[1];
    return channel;
  }
}
