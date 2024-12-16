package model;

/**
 * The {@code Blur} class implements the {@code ImageProcessing} interface to
 * apply a blur effect to a specified image using a convolution kernel.
 */
public class Blur extends ApplyKernel {

  /**
   * Abstract method implemented to return the correct kernel filter.
   *
   * @return Kernel
   */
  @Override
  protected double[][] getKernel() {
    double[][] blurKernel = {
        {1 / 16.0, 1 / 8.0, 1 / 16.0},
        {1 / 8.0, 1 / 4.0, 1 / 8.0},
        {1 / 16.0, 1 / 8.0, 1 / 16.0}
    };
    return blurKernel;
  }


}
