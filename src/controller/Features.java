package controller;

import java.io.IOException;

/**
 * This interface represents all the features available.
 */
public interface Features {
  /**
   * Load the image.
   *
   * @param path path of the image
   * @throws IOException if invalid path or filetype given.
   */
  void load(String path) throws IOException;

  /**
   * Load the image.
   *
   * @param path path of the image
   * @throws IOException if invalid path or filetype given.
   */
  void save(String path) throws IOException;

  /**
   * Red component of the image.
   */
  void red();

  /**
   * Blue component of the image.
   */
  void blue();

  /**
   * Green component of the image.
   */
  void green();

  /**
   * Luma Grayscale component of the image.
   */
  void grayscale();

  /**
   * Horizontally flipped image.
   */
  void horizontalFlip();

  /**
   * Vertically flipped image.
   */
  void verticalFlip();
  //void brighten(String increment);

  /**
   * Gaussian blurred image.
   */
  void blur();

  /**
   * Sharpened Image.
   */
  void sharpen();

  /**
   * Sepia filtered Image.
   */
  void sepia();

  /**
   * Haar transform compressed Image.
   *
   * @param percentage of the image to be compressed.
   * @throws IllegalArgumentException when invalid percentage is given.
   */
  void compress(String percentage) throws IllegalArgumentException;

  /**
   * Colo corrected version of the image.
   */
  void colorCorrection();

  /**
   * Adjust the black, mid and white tones.
   *
   * @param b black tones.
   * @param m midtones
   * @param w white tones.
   * @throws IllegalArgumentException when input is not in ascending order
   */
  void levelAdjust(String b, String m, String w) throws IllegalArgumentException;

  /**
   * Splitted image of the original and applied filter.
   *
   * @param width width to be splitted.
   * @throws IllegalArgumentException when invalid input is provided.
   */
  void split(int width) throws IllegalArgumentException;

  /**
   * Downscaled version of the image.
   *
   * @param width  new width.
   * @param height new height
   * @throws IllegalArgumentException when invalid input is provided.
   */
  void downscale(String width, String height) throws IllegalArgumentException;

}