package controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import model.Blue;
import model.Blur;
import model.ColorCorrect;
import model.Compression;
import model.DownScale;
import model.FlipHorizontal;
import model.FlipVertical;
import model.Green;
import model.Histogram;
import model.ImageDB;
import model.LevelsAdjust;
import model.Load;
import model.Luma;
import model.Red;
import model.Sepia;
import model.Sharpen;
import model.VerticalLineSplit;
import view.IView;
import view.View;

/**
 * Controller class for the GUI operation.
 */
public class GUIController implements Features {
  private ImageDB imageDB = new ImageDB();
  private IView view;
  private int imageName = 0;
  private Load load = new Load();


  /**
   * .
   *
   * @param v View
   */
  public void setView(View v) {
    view = v;
    //provide view with all the callbacks
    view.getToolPanel().addFeatures(this);
  }


  @Override
  public void load(String path) throws IOException {
    try {
      loadImage(path, String.valueOf(imageName));
      BufferedImage img = ImageIO.read(new File(path));
      view.getImageDisplayPanel().setImage(img);
      String[] histInput = {"histogram", String.valueOf(imageName), "Hist"};
      updateViewWithImageAndHistogram(histInput, imageDB);
    } catch (Exception ex) {
      throw new RuntimeException(ex);
    }
  }


  @Override
  public void save(String path) throws IOException {
    try {
      saveImage(path, String.valueOf(imageName));
    } catch (Exception ex) {
      throw new IllegalArgumentException(ex);
    }
  }


  private void viewUpdate(Image[] img) {
    view.getImageDisplayPanel().setImage(convertImage(img[0]));
    imageName++;
    load.loadToDb(String.valueOf(imageName), img[0], imageDB);
    String[] histInput = {"histogram", String.valueOf(imageName), "Hist"};
    updateViewWithImageAndHistogram(histInput, imageDB);
  }

  @Override
  public void red() {
    String[] input = {"red-component", String.valueOf(imageName), String.valueOf(imageName + 1)};
    Image[] img = new Red().process(input, imageDB);
    viewUpdate(img);
  }

  @Override
  public void blue() {
    String[] input = {"", String.valueOf(imageName), String.valueOf(imageName + 1)};
    //initial string element is just a filler and can be blank
    Image[] img = new Blue().process(input, imageDB);
    viewUpdate(img);
  }

  @Override
  public void green() {
    String[] input = {"", String.valueOf(imageName),
        String.valueOf(imageName + 1)}; //initial string element is just a filler and can be blank
    Image[] img = new Green().process(input, imageDB);
    viewUpdate(img);
  }

  @Override
  public void grayscale() {
    String[] input = {"", String.valueOf(imageName),
        String.valueOf(imageName + 1)}; //initial string element is just a filler and can be blank
    Image[] img = new Luma().process(input, imageDB);
    viewUpdate(img);
  }

  @Override
  public void horizontalFlip() {
    String[] input = {"", String.valueOf(imageName),
        String.valueOf(imageName + 1)}; //initial string element is just a filler and can be blank
    Image[] img = new FlipHorizontal().process(input, imageDB);
    view.getImageDisplayPanel().setImage(convertImage(img[0]));
    viewUpdate(img);

  }

  @Override
  public void verticalFlip() {
    String[] input = {"", String.valueOf(imageName),
        String.valueOf(imageName + 1)}; //initial string element is just a filler and can be blank
    Image[] img = new FlipVertical().process(input, imageDB);
    viewUpdate(img);

  }

  //  @Override
  //  public void brighten(int increment) {
  //    String[] brighten = {"brighten",String.valueOf(increment),
  //        String.valueOf(imageName),String.valueOf(imageName +1)};
  //    Image[] img = new Brighten().process(brighten,imageDB);
  //    view.getImageDisplayPanel().setImage(convertImage(img[0]));
  //    imageName++;
  //    load.loadToDb(String.valueOf(imageName), img[0], imageDB);
  //    String[] histInput = {"histogram", String.valueOf(imageName), "Hist" };
  //    updateViewWithImageAndHistogram(histInput,imageDB);
  //
  //  }

  @Override
  public void blur() {
    String[] input = {"", String.valueOf(imageName),
        String.valueOf(imageName + 1)};
    Image[] img = new Blur().process(input, imageDB);
    viewUpdate(img);
  }

  @Override
  public void sharpen() {
    String[] input = {"", String.valueOf(imageName), String.valueOf(imageName + 1)};
    Image[] img = new Sharpen().process(input, imageDB);
    viewUpdate(img);
  }

  @Override
  public void sepia() {
    String[] input = {"", String.valueOf(imageName), String.valueOf(imageName + 1)};
    Image[] img = new Sepia().process(input, imageDB);
    viewUpdate(img);
  }

  @Override
  public void compress(String percentage) throws IllegalArgumentException {

    String[] input = {"", percentage, String.valueOf(imageName), String.valueOf(imageName + 1)};
    Image[] img = new Compression().process(input, imageDB);
    viewUpdate(img);
  }

  @Override
  public void colorCorrection() {
    String[] input = {"", String.valueOf(imageName),
        String.valueOf(imageName + 1)};
    Image[] img = new ColorCorrect().process(input, imageDB);
    viewUpdate(img);
  }

  @Override
  public void levelAdjust(String b, String m, String w) throws IllegalArgumentException {
    String[] input = {"", b, m, w,
        String.valueOf(imageName),
        String.valueOf(imageName + 1)};
    Image[] img = new LevelsAdjust().process(input, imageDB);
    viewUpdate(img);

  }

  @Override
  public void split(int width) throws IllegalArgumentException {
    Image img = new VerticalLineSplit().split(String.valueOf(imageName - 1),
        String.valueOf(imageName), width, imageDB);
    view.getImageDisplayPanel().setImage(convertImage(img));
    if (width == 0) {
      imageName--;
    }
  }

  @Override
  public void downscale(String width, String height) throws IllegalArgumentException {
    String[] input = {"", String.valueOf(imageName), width, height,
        String.valueOf(imageName + 1)};
    Image[] img = new DownScale().process(input, imageDB);
    viewUpdate(img);
  }


  private void updateViewWithImageAndHistogram(String[] histInput, ImageDB imageDB) {
    // Update image panel
    //view.getImageDisplayPanel().setImage(convertImage(img));

    // Generate and update histogram
    Image[] histogramImage = new Histogram().process(histInput, imageDB);
    view.getHistogramDisplayPanel().setImage(convertImage(histogramImage[0]));
  }

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

  private void loadImage(String path, String imageName) throws IOException {
    Image image;
    try {
      image = ImageFactory.loadImage(path);
    } catch (Exception e) {
      throw new IOException("Failed to load image: " + path, e);
    }
    load.loadToDb(imageName, image, imageDB);
  }

  private BufferedImage convertImage(Image image) {
    int width = image.getWidth();
    int height = image.getHeight();
    BufferedImage moddedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        int r = (int) image.getPixel(x, y).getRed();
        int g = (int) image.getPixel(x, y).getGreen();
        int b = (int) image.getPixel(x, y).getBlue();

        //need to clamp
        r = Math.min(255, Math.max(0, r));
        g = Math.min(255, Math.max(0, g));
        b = Math.min(255, Math.max(0, b));

        // Model.Combine the R, G, B values into a single int for setRGB()
        int rgb = (r << 16) | (g << 8) | b;

        moddedImage.setRGB(x, y, rgb);
      }
    }
    return moddedImage;
  }

}
