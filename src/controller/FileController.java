package controller;

import java.io.IOException;

/**
 * The {@code FileController} interface provides methods for processing scripts
 * related to image manipulation, as well as methods for managing images by
 * loading, saving, and retrieving them from memory.
 */
public interface FileController {

  /**
   * Processes an array of script lines that may contain commands for loading,
   * saving, and manipulating images. This method is expected to handle all
   * supported commands defined within the system.
   *
   * @param scriptLines an array of script lines to be processed
   * @throws IOException if an error occurs while performing file operations
   */
  void processScript(String[] scriptLines) throws IOException;

  /**
   * Sets an image in the internal map using the specified filename as the key.
   * This method is typically used to load or modify an image stored in memory.
   *
   * @param filename the name to associate with the image
   * @param image the image object to be stored
   * @throws IllegalArgumentException if the image is null
   */
  void setImage(String filename, Image image);

  /**
   * Retrieves an image by its filename from the internal map. If the image
   * is not found, an exception is thrown.
   *
   * @param filename the name of the image to retrieve
   * @return the image associated with the specified filename
   * @throws IllegalArgumentException if the image is not found
   */
  Image getImage(String filename);

}
