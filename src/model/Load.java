package model;

import controller.Image;

/**
 * Load class of the model to interact between controller and model images.
 */
public class Load {

  /**
   * method to load an image into the imageDB.
   *
   * @param imageName name of the Image.
   * @param image     Object of the image.
   * @param imageDB   object of the image database.
   */
  public void loadToDb(String imageName, Image image, ImageDB imageDB) {
    imageDB.loadImage(imageName, image);
  }

  /**
   * method to get an image into the imageDB.
   *
   * @param imageName name of the Image.
   * @param imageDB   object of the image database.
   * @return Image Object of the image to be saved.
   */
  public Image saveFromDB(String imageName, ImageDB imageDB) {
    return copy(imageDB.get(imageName));
  }

  //copy method to avoid dataleak.
  protected Image copy(Image image) {
    Image copy = image.createImage(image.getWidth(), image.getHeight());
    for (int x = 0; x < image.getWidth(); x++) {
      for (int y = 0; y < image.getHeight(); y++) {
        copy.setPixel(x, y, image.getPixel(x, y));
      }
    }
    return copy;
  }
}
