package model;

import controller.Pixel;

/**
 * The {@code Luma} class implements the {@code ImageProcessing} interface
 * to create a new image that represents the luma (brightness) of the original image,
 * calculated using the formula:
 * <code>luma = 0.2126 * red + 0.7152 * green + 0.0722 * blue</code>.
 */
public class Luma extends ApplyChannel {

  /**
   * Abstract method implementation of getChannel that returns the applicable channel pixel.
   *
   * @param pixel pixel to get the channel
   * @return resultant luma channel
   */
  @Override
  protected double[] getChannel(Pixel pixel) {
    double[] channel = new double[3];
    channel[0] = pixel.getRed();
    channel[1] = pixel.getGreen();
    channel[2] = pixel.getBlue();
    double luma = channel[0] * 0.2126 + channel[1] * 0.7152 + channel[2] * 0.0722;
    channel[0] = luma;
    channel[1] = channel[0];
    channel[2] = channel[1];
    return channel;
  }
}
