package controller;

import java.io.IOException;

/**
 * The {@code ImageFactory} class provides utility methods for loading and saving
 * images from and to various file formats. This class supports image formats such as
 * PPM, JPG/JPEG, and PNG, and delegates the file-specific operations to the appropriate
 * image classes.
 */
public class ImageFactory {

  /**
   * Loads an image from the specified file path. The method determines the file format
   * based on the file extension and delegates the loading operation to the appropriate
   * image class (e.g., {@code PPMImage}, {@code JPGImage}).
   *
   * @param path the file path to load the image from
   * @return an {@code Image} object representing the loaded image
   * @throws IOException              if an error occurs while reading the file
   * @throws IllegalArgumentException if the file path is invalid or unsupported
   */
  public static Image loadImage(String path) throws IOException {
    if (path == null || path.isEmpty()) {
      throw new IllegalArgumentException("Image path cannot be null or empty.");
    }
    String extension = getFileExtension(path);
    Image image;
    try {
      switch (extension.toLowerCase()) {
        case "ppm":
          image = PPMImage.load(path);
          break;// Delegate to Controller.PPMImage's load method
        case "jpg":
        case "jpeg":
        case "png":
          image = OtherImages.load(path);
          break;
        default:
          image = OtherImages.load(path); // For all the other images type
          break;
      }
    } catch (IOException e) {
      throw new IOException("Failed to load the image from path: " + path, e);
    }

    return image;
  }

  /**
   * Saves an image to the specified file path. The method determines the file format
   * based on the file extension and delegates the saving operation to the appropriate
   * image class (e.g., {@code PPMImage}, {@code JPGImage}).
   *
   * @param path the file path to save the image to
   * @param img  the {@code Image} object to be saved
   * @throws IOException              if an error occurs while writing the file
   * @throws IllegalArgumentException if the file path is invalid or unsupported
   */
  public static void saveImage(String path, Image img) throws IOException {
    if (path == null || path.isEmpty()) {
      throw new IllegalArgumentException("Image path cannot be null or empty.");
    }
    String extension = getFileExtension(path);
    try {
      switch (extension.toLowerCase()) {
        case "ppm":
          PPMImage.save(path, img);
          break;// Delegate to Controller.PPMImage's save method
        case "jpg":
        case "jpeg":
          OtherImages.save(path, img);  // Delegate to Controller.JPGImage's save method
          break;
        case "png":
          OtherImages.save(path, img);  // Delegate to PNGImage's save method
          break;
        default:
          throw new IllegalArgumentException("Unsupported file format: " + extension);
      }
    } catch (IOException e) {
      throw new IOException("Failed to save the image to path: " + path, e);
    }

  }


  /**
   * Helper method that extracts the file extension from a given file path.
   * This method is used to determine the format of the file (e.g., PPM, JPG, PNG).
   *
   * @param path the file path from which to extract the extension
   * @return the file extension (e.g., "ppm", "jpg", "png")
   * @throws IllegalArgumentException if the file path is invalid
   */
  private static String getFileExtension(String path) {
    int dotIndex = path.lastIndexOf('.');
    if (dotIndex > 0 && dotIndex < path.length() - 1) {
      return path.substring(dotIndex + 1);
    } else {
      throw new IllegalArgumentException("Invalid file path: " + path);
    }
  }
}
