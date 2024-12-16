package controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

//import model.*;
import model.Blue;
import model.Blur;
import model.Brighten;
import model.ColorCorrect;
import model.Combine;
import model.Compression;
import model.Green;
import model.Histogram;
import model.ImageDB;
import model.ImageProcessing;
import model.Intensity;
import model.LevelsAdjust;
import model.Load;
import model.Luma;
import model.Red;
import model.Sepia;
import model.Sharpen;
import model.Split;
import model.Value;
import model.FlipHorizontal;
import model.FlipVertical;

/**
 * This class implements the {@link FileController} interface and provides
 * methods to process image-related commands via script files. It manages
 * a collection of images, allows loading and saving of images, and applies
 * various image processing commands.
 */

public class FileControllerImpl implements FileController {

  private Map<String, ImageProcessing> commands;
  private final ImageDB imageDB = new ImageDB();

  /**
   * Constructs a {@code FileControllerImpl} and initializes available
   * image processing commands by registering them in the command map.
   */
  public FileControllerImpl() {
    commands = new HashMap<String, ImageProcessing>();
    registerCommands();
  }

  /**
   * Registers all available image processing commands in the system. This method
   * associates string keys with corresponding command objects to enable
   * script-driven image processing.
   */
  private void registerCommands() {
    commands.put("red-component", new Red());
    commands.put("green-component", new Green());
    commands.put("blue-component", new Blue());
    commands.put("brighten", new Brighten());
    commands.put("blur", new Blur());
    commands.put("sharpen", new Sharpen());
    commands.put("sepia", new Sepia());
    commands.put("luma-component", new Luma());
    commands.put("value-component", new Value());
    commands.put("intensity-component", new Intensity());
    commands.put("horizontal-flip", new FlipHorizontal());
    commands.put("vertical-flip", new FlipVertical());
    commands.put("rgb-split", new Split());
    commands.put("rgb-combine", new Combine());
    commands.put("compress", new Compression());
    commands.put("histogram", new Histogram());
    commands.put("color-correct", new ColorCorrect());
    commands.put("levels-adjust", new LevelsAdjust());
  }

  /**
   * Processes a script containing image commands, loading images, applying
   * transformations, saving images, and managing IO operations.
   *
   * @param scriptLines an array of script lines to be processed
   * @throws IOException if any file operation fails
   */

  @Override
  public void processScript(String[] scriptLines) throws IOException {
    for (String line : scriptLines) {
      line = line.trim();
      if (line.startsWith("#") || line.isEmpty()) {
        continue;  // Ignore comments or empty lines
      }
      String[] parts = line.split(" ");

      if (parts[0].equals("load")) {
        if (parts.length != 3) {
          throw new IllegalArgumentException("Load command requires a file path and "
              + "an image name.");
        }
        loadImage(parts[1], parts[2]);

      } else if (parts[0].equals("save")) {
        if (parts.length != 3) {
          throw new IllegalArgumentException("Save command requires a file path "
              + "and an image name.");
        }
        saveImage(parts[1], parts[2]);
      }

      else if (parts[0].equals("run")) {
        if (parts.length != 2) {
          throw new IllegalArgumentException("Run command requires a file path");
        }
        readScriptFile(parts[1]);
      }

      else if (parts[0].equals("exit")) {
        break;
      }

      else {
        String commandKey = parts[0];
        try {
          ImageProcessing process = commands.get(commandKey);

          if (process != null) {
            Image[] moddedImage = process.process(parts, imageDB);
            String[] filename = process.getName(parts);
            for (int i = 0; i < filename.length; i++) {
              new Load().loadToDb(filename[i], moddedImage[i], imageDB);
            }
          } else {
            System.out.println("Unknown command: " + commandKey);
          }
        } catch (Exception e) {
          throw new IOException(e.getMessage());
        }
      }
    }
  }

  /**
   * Loads an image from a specified file path and assigns it the specified
   * name for future reference.
   *
   * @param path      the file path to load the image from
   * @param imageName the name to assign to the loaded image
   * @throws IOException if there is an issue loading the image
   */
  private void loadImage(String path, String imageName) throws IOException {
    Image image;
    try {
      image = ImageFactory.loadImage(path);
    } catch (Exception e) {
      throw new IOException("Failed to load image: " + path, e);
    }
    new Load().loadToDb(imageName, image, imageDB);
  }

  /**
   * Saves an image with a specified name to a file at the given path.
   *
   * @param path      the file path to save the image to
   * @param imageName the name of the image to save
   * @throws IOException if there is an issue saving the image
   */
  private void saveImage(String path, String imageName) throws IOException {
    Image image = new Load().saveFromDB(imageName, imageDB);
    if (image == null) {
      throw new IllegalArgumentException("Image not found: " + imageName);
    }
    try {
      ImageFactory.saveImage(path, image);
    } catch (IOException e) {
      throw new IOException("Failed to save image: " + path, e);
    }
  }

  /**
   * Reads a script file from the specified path and processes its content.
   *
   * @param filePath the path to the script file to read
   * @throws IOException if there is an issue reading the script file
   */
  private void readScriptFile(String filePath) throws IOException {
    // Read the file and return its contents as an array of strings
    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
      StringBuilder content = new StringBuilder();
      String line;
      while ((line = br.readLine()) != null) {
        content.append(line).append("\n");
      }
      processScript(content.toString().split("\n"));
    } catch (IOException e) {
      throw new IOException("Error reading script file: " + filePath, e);
    }
  }

  /**
   * Sets an image in the internal map using a given filename as the key.
   *
   * @param filename the name to assign to the image
   * @param image    the image object to set
   * @throws IllegalArgumentException if the image is null
   */
  @Override
  public void setImage(String filename, Image image) {
    if (image == null) {
      throw new IllegalArgumentException("Cannot set a null image.");
    }
  }

  /**
   * Retrieves an image by its filename from the internal map.
   *
   * @param filename the name of the image to retrieve
   * @return the image associated with the specified filename
   * @throws IllegalArgumentException if the image is not found
   */
  @Override
  public Image getImage(String filename) {
    return null;
  }
}