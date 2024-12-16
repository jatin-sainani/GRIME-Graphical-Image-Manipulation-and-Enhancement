package model;

/**
 * The {@code Sharpen} class implements the {@code ImageProcessing} interface
 * to apply a sharpening filter to an image, enhancing its edges and details.
 */
public class Sharpen extends ApplyKernel {
  /**
   * Abstract method implemented to return the correct kernel filter.
   *
   * @return Kernel
   */
  @Override
  protected double[][] getKernel() {
    double[][] sharpenKernel = {
        {-1 / 8.0, -1 / 8.0, -1 / 8.0, -1 / 8.0, -1 / 8.0},
        {-1 / 8.0, 1 / 4.0, 1 / 4.0, 1 / 4.0, -1 / 8.0},
        {-1 / 8.0, 1 / 4.0, 1, 1 / 4.0, -1 / 8.0},
        {-1 / 8.0, 1 / 4.0, 1 / 4.0, 1 / 4.0, -1 / 8.0},
        {-1 / 8.0, -1 / 8.0, -1 / 8.0, -1 / 8.0, -1 / 8.0}
    };
    return sharpenKernel;
  }
}