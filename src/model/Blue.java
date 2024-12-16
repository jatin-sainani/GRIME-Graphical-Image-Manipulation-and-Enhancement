package model;

import controller.Pixel;

/**
 * The {@code Blue} class implements the {@code ImageProcessing} interface to
 * create a blue-image version of a specified image.
 */
public class Blue extends ApplyChannel {

  /**
   * Abstract method implementation of getChannel that returns the applicable channel pixel.
   * @param pixel pixel to get the channel
   * @return resultant blue channel
   */
  @Override
  protected double[] getChannel(Pixel pixel) {
    double[] channel = new double[3];
    channel[0] = pixel.getBlue();
    channel[1] = channel[0];
    channel[2] = channel[1];
    return channel;
  }
}
