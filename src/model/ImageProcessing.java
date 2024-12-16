package model;

import controller.Image;

/**
 * The {@code ImageProcessing} interface defines methods for processing images.
 * Implementations of this interface provide various image processing functionalities
 * based on command-line arguments and a collection of images.
 */
public interface ImageProcessing {

  /**
   * Processes images based on the provided arguments and a map of images.
   *
   * @param args   an array of strings representing the processing commands and parameters
   * @param images a map where keys are image identifiers and values are {@code Image} objects
   * @return an array of processed {@code Image} objects resulting from the processing commands
   */
  Image[] process(String[] args, ImageDB images) throws IllegalArgumentException;

  /**
   * Extracts and returns the names of the commands from the provided arguments.
   *
   * @param args an array of strings representing the processing commands and parameters
   * @return an array of strings containing the names of the commands found in the arguments
   */
  String[] getName(String[] args);

}
