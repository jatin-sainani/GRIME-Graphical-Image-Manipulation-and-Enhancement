import java.io.IOException;
import java.util.Scanner;

import controller.GUIController;
import controller.FileController;
import controller.FileControllerImpl;
//import controller.ImageController;
import view.View;

/**
 * The {@code ImageManipulation} class provides a command-line interface for
 * manipulating images using various commands. It allows users to load images,
 * save images, apply different transformations, and manage image files through
 * a scripting system.
 */
public class ImageManipulation {

  /**
   * The main method serves as the entry point for the image manipulation program.
   * It initializes a scanner for user input, displays available commands,
   * and processes the user's input commands or script.
   *
   * @param args command-line arguments (not used in this implementation).
   * @throws IOException if an I/O error occurs during file operations.
   */
  public static void main(String[] args) throws IOException {


    if (args.length == 0) {
      gui();
    } else if (args.length == 2 && args[0].equalsIgnoreCase("-file")) {
      script(args[1]);
    } else if (args.length == 1 && args[0].equalsIgnoreCase("-text")) {
      interactive();
    }


  }

  /**
   * Displays the instructions for using the image manipulation commands.
   * This includes descriptions of available commands and their expected
   * formats for user reference.
   */
  private static void instructions() {
    System.out.println("run script-file: Load and run the script commands in the specified file.");

    System.out.println("exit: To exit the program.");

    System.out.println("load image-path image-name: Load an image from the specified "
        + "path and refer it to henceforth by the given image name.");

    System.out.println("save image-path image-name: Save the image with the given name "
        + "to the specified path which should include the name of the file.");

    System.out.println("red-component image-name dest-image-name: Create an image with "
        + "the red-component of the image with the given name, and refer to it "
        + "henceforth in the program by the given destination name. Similar commands"
        + " for green, blue, value, luma, intensity components should be supported. "
        + "Note that the images for value, luma and intensity will be greyscale images.");

    System.out.println("horizontal-flip image-name dest-image-name: Flip an image horizontally"
        + "to create a new image, referred to henceforth by the given destination name.");

    System.out.println("vertical-flip image-name dest-image-name: Flip an image vertically "
        + "to create a new image, referred to henceforth by the given destination name.");

    System.out.println("brighten increment image-name dest-image-name: brighten the image "
        + "by the given increment to create a new image, referred to henceforth by "
        + "the given destination name. The increment may be positive (brightening) or "
        + "negative (darkening).");

    System.out.println("rgb-split image-name dest-image-name-red dest-image-name-green "
        + "dest-image-name-blue: split the given image into three images containing "
        + "its red, green and blue components respectively. These would be the same "
        + "images that would be individually produced with the red-component, "
        + "green-component and blue-component commands.");

    System.out.println("rgb-combine image-name red-image green-image blue-image: Combine "
        + "the three images that are individually red, green and blue into a "
        + "single image that gets its red, green and blue components from the "
        + "three images respectively.");

    System.out.println("blur image-name dest-image-name: blur the given image and store "
        + "the result in another image with the given name.");

    System.out.println("sharpen image-name dest-image-name: sharpen the given image and "
        + "store the result in another image with the given name.");

    System.out.println("sepia image-name dest-image-name: produce a sepia-toned "
        + "version of the given image and store the result in another image "
        + "with the given name.");

    System.out.println("Please refer to useme for further information");

  }

  private static void gui() {
    View view = new View();
    GUIController controller = new GUIController();
    controller.setView(view);
  }

  private static void interactive() {
    Scanner scanner = new Scanner(System.in);
    System.out.println("Please enter the command in the below format:");
    instructions(); // printing the instructions for the commands
    FileController controller = new FileControllerImpl();
    while (true) {
      String input = scanner.nextLine().trim();
      if (input.equalsIgnoreCase("exit")) {
        break;  // Exit the loop if user enters "exit"
      }
      try {
        String[] commands = input.split("\\r?\\n"); //handling both CLI and run script
        // Process the script lines
        controller.processScript(commands);
        //System.out.println("The commands have been processed");
      } catch (IOException e) {
        System.err.println("Error reading the command: " + e.getMessage());
      }
    }
    scanner.close();// Close the scanner to avoid resource leaks
    System.out.println("The commands have been processed");
  }

  private static void script(String path) {
    FileController controller = new FileControllerImpl();
    String[] command = new String[]{"run" + " " + path};
    try {
      controller.processScript(command);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    System.out.println("The file has been processed");
  }

}