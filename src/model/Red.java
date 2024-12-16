package model;

import controller.Pixel;

/**
 * The {@code Red} class implements the {@code ImageProcessing} interface
 * to create a new image that represents the red component of the original image.
 */
public class Red extends ApplyChannel {

  /**
   * Abstract method implementation of getChannel that returns the applicable channel pixel.
   *
   * @param pixel pixel to get the channel
   * @return resultant red channel
   */
  @Override
  protected double[] getChannel(Pixel pixel) {
    double[] channel = new double[3];
    channel[0] = pixel.getRed();
    channel[1] = channel[0];
    channel[2] = channel[1];
    return channel;
  }

}

