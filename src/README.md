# Image Manipulation Project

## Overview
The Image Manipulation Project provides a Graphical and Command-line interface for manipulating images through various operations such as loading, saving, and transforming images. Users can input commands to perform actions like flipping, brightening, and applying filters to images. The application processes these commands via a controller, supporting both interactive and script-based operations.

## Project Structure

### Main Class
- **ImageManipulation**  
  *Purpose*: The entry point of the application. Handles user input and commands to manipulate images.  
  **Key Methods**:
  - `main(String[] args)`: Initializes the program, and delegates the `GUIController` or `FileController` accordingly.
  - `instructions()`: Displays available commands and their descriptions to the user.

### Controller
- **FileController (Interface)**  
  *Purpose*: Defines methods for processing image manipulation commands, supporting various implementations.  
  **Key Methods**:
  - `processScript(String[] scriptLines)`: Processes commands for image manipulation.
  - `setImage(String filename, Image image)`: Stores an image in the controller.
  - `getImage(String filename)`: Retrieves an image by filename.

- **FileControllerImpl (Class)**  
  *Purpose*: Implements the `FileController` interface, executing the commands defined by the user.  
  **Key Attributes**:
  - `Map<String, ImageProcessing> commands`: Maps command syntax to corresponding image processing classes. 
  **Key Methods**:
  - `processScript(String[] scriptLines)`: Processes each script line and executes commands.
  - `loadImage(String path, String imageName)`: Loads an image from the specified path.
  - `saveImage(String path, String imageName)`: Saves the specified image to a path.


- **Features (Interface) part of v3.0**  
  *Purpose*: Defines methods for processing image manipulation commands, supporting various implementations.  
  **Key Methods**:
  - `load(String path)`: represents the load feature.
  - `save(String path)`: represents the save feature.
  - `red`,`blue`,`green`,`grayscale`,`red`,`horizontalFLip`,`verticalFLip`,`blur`,`sharpen`,`sepia`,`compress`,`colorCorrection`,`levelAdjust`,`split`,'`downscale`: represents the respective feature.

- **GUIController (Class) part of v3.0**  
  *Purpose*: Implements the `Features` interface, delegating the commands given by the GUI input.  
  **Key Attributes**:
  - `Map<String, ImageProcessing> commands`: Maps command syntax to corresponding image processing classes.
    **Key Methods**:
  - `setView(View v)`: sets the view to this controller.
  - `red`,`blue`,`green`,`grayscale`,`red`,`horizontalFLip`,`verticalFLip`,`blur`,`sharpen`,`sepia`,`compress`,`colorCorrection`,`levelAdjust`,`split`,`downscale`,`load`,`save`: implementation of the Feature interface.
### Image Representation
- **Pixel (Interface)**  
  *Purpose*: Defines pixel structure with methods to retrieve RGB channels.  
  **Key Methods**:
  - `getRed()`, `getGreen()`, `getBlue()`: Returns the RGB channels of the pixel.
  - `getRGB()`: Returns the RGB values in an array.

- **PixelImpl (Class)**  
  *Purpose*: Implements `Pixel`, providing RGB data.  
  **Attributes**: `double red`, `double green`, `double blue`

- **Image (Interface)**  
  *Purpose*: Defines image structure and methods to manipulate pixels.  
  **Key Methods**:
  - `getWidth()`, `getHeight()`: Returns image dimensions.
  - `getPixel(int x, int y)`: Retrieves a pixel.
  - `setPixel(int x, int y, Pixel rgb)`: Sets a pixel at coordinates.

- **AbstractImage (Class)**  
  *Purpose*: Abstract implementation of the `Image` interface.  
  **Attributes**: `int width`, `int height`, `Pixel[][] pixels`

### Image Formats
- **OtherImage (Class)**  
  Handles loading/saving of all the other supported image format.
  
- **PPMImage (Class)**  
  Handles loading/saving of PPM images.

- **ImageFactory (Class)**  
  *Purpose*: Factory method to handle image format based on file extension.

### Model
- **ImageDB (Class)**
  
  Image DB class is used to store all the images in the current session.
  
  - **Key Methods**:
    - `protected void loadImage(String filename, Image image)`: Loads image into the DB.
    - `protected Image get(String filename)`: Retrieves the image from the DB.

- **VerticalLineSplit (Protected helper class)**
  - Package protected class to help enable split parameters wherever required.
  - **Key Methods**:
    - `protected Image split(Image original, Image modded, int splitWidth)`: Splits the original image and modded image as per the splitwidth

- **Load (Class)**

  Load class is used to interact between model and controller to load and save images.

  - **Key Methods**:
    - `loadToDb(String imageName, Image image, ImageDB imageDB)`: Loads image into the DB.
    - `saveFromDB(String imageName, ImageDB imageDB)`: Returns the image from the DB.
    - `protected Image copy(Image image)`: Creates a copy of the image before returning it.
    
- **Masking (Package Protected Class)**

  Masking class is called as an optional parameter and supports ability to apply any of the existing image manipulations to only part of an image.

  - **Key Methods**:
    - `protected Image applyMask(Image original, Image modded, Image maskImage)`: Takes in the original image, filter applied image and the mask image(grayscale) and returns filter applied as per the mask image

- **ImageProcessing (Interface)**  
  Defines methods for image processing.
  - `Image[] process(String[] args, ImageDB images)` : Process the operation.
  - `String[] getName(String[] args)` : returns the name of the destination image.

    #### Image Processing Classes

    - **ApplyChannel (Abstract class)**

      Class to abstract the application of a channel.
      - **Key Methods**:
        - `abstract protected double[] getChannel(Pixel pixel)`: Gets overridden in each class and returns the applicable filter accordingly.
      - Classes that extend ApplyChannel: **Blue**, **Green**, **Red**, **Luma**, **Value**, **Intensity**

    - **ApplyKernel (Abstract class)**
  
      Class to abstract the application of a kernel.
      - **Key Methods**:
        - `abstract protected double[][] getKernel()`: Gets overridden in each extended class and returns the applicable kernel accordingly.
      - Classes that extend ApplyKernell:  **Blur**, **Sharpen**
    -  **Sepia**, **Brighten**, **Combine**, **flipHorizontal**, **flipVertical**, **Split**,
    - v2.0 Classes/Features - **Compression**, **LevelAdjust**, **ColorCorrect**, **Histogram**
    - v2.0 Optional Parameters Class - **Split**
    - v3.0 Classes/Features - **DownScale**
    - v3.0 Optional Parameters -  **Masking**

  

**NEW RELEASE(v3.0)**

### View
- **IView (Interface)**

  IView interface that defines the functions for the View class.

  - **Key Methods**:
    - `getImageDisplayPanel();`: Returns the Image Display Panel.
    - `getHistogramDisplayPanel();`: Returns the Histogram Display Panel.
    - `getToolPanel();`: Returns the Tool Panel.
    

- **ITool (Interface)**

  ITool interface that defines the functions for the ToolPanel class.

  - **Key Methods**:
    - `addFeatures(Features features);`: Allows adding more features to the ToolPanel.


- **ImagePanel (Interface)**

  ITool interface that defines the functions for the ToolPanel class.

  - **Key Methods**:
    - `setImage(BufferedImage image);`: set the Image to be displayed on the panel

- **View (Class)**

  View class implements the IView interface and sets the layout for all the Panels in the Frame.

  - **Key Methods**:
    - `private setupUIComponent();`: Gets called in the constructor and setups the UI Layout.


- **ToolPanel (Class)**

  ToolPanel class that implements the ITool interface and initialises all the buttons and their action listeners.

  - **Key Methods**:
    - `ToolPanel();`: Constructor that initializes all the buttons and sliders and sets up their layout within the panel.
    - `addFeatures(Features features);`: adds the action listener to all the buttons and sliders.


- **ImageDisplayPanel (Interface)**

  ImageDisplayPanel class implements ImagePanel that defines the layout of the images and sets the display image.

  - **Key Methods**:
    - `setImage(BufferedImage image);`: set the Image to be displayed on the panel


## Design Changes and justifications
 
- One class added to the model that extends the ImageProcessing interface for Downscale feature.
- A package protected class called Masking was created in the model which gets called before returning blur, sharpen, sepia, greyscale(Red,Green,Blue,Value,Luma,Intensity) images.
- Adjusted the exception handling of parameter size for the above classes to accommodate optional split parameter.
- Created a new independent view package with 3 new interface and 3 classes.
- Created a new controller class called GUIController that implements Features interface and interacts between the existing model with the new view

*All the images used in the project are owned or created by the owners of the repo and you are free to use them*
