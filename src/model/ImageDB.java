package model;

import java.util.HashMap;

import controller.Image;

/**
 * Image database class that handles storing of the iamge objects in the model.
 */
public class ImageDB {
  private HashMap<String, Image> images = new HashMap<>();

  protected void loadImage(String filename, Image image) {
    images.put(filename, image);
  }

  protected Image get(String filename) {
    return images.get(filename);
  }
}