package model;

import controller.Image;

/**
 * Class that supports having a vertical split between modded and original image.
 */
public class VerticalLineSplit {

  /**
   * split function that accepts original and modded image to return the splitted image.
   *
   * @param original   Before Image
   * @param modded     After Image
   * @param splitWidth the width of the vertical line.
   * @return the split image.
   * @throws IllegalArgumentException when the width is invalid
   */
  protected Image split(Image original, Image modded, int splitWidth)
      throws IllegalArgumentException {
    int width = original.getWidth();
    if ((splitWidth > width) || (splitWidth < 0)) {
      throw new IllegalArgumentException("The split width must be between 0 and " + width);
    }
    int height = original.getHeight();
    Image splitImage = original.createImage(width, height);
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        if (j < splitWidth) {
          splitImage.setPixel(j, i, modded.getPixel(j, i));
        } else {
          splitImage.setPixel(j, i, original.getPixel(j, i));
        }
      }
    }
    return splitImage;
  }

  /**
   * split function that accepts name of the original and name
   * of the modded image to return the splitted image.
   *
   * @param original   Before Image name
   * @param modded     After Image name
   * @param splitWidth the width of the vertical line.
   * @return the split image.
   */

  public Image split(String original, String modded, int splitWidth, ImageDB db) {
    Image originalImage = db.get(original);
    Image moddedImage = db.get(modded);
    splitWidth = originalImage.getWidth() / 100 * splitWidth;
    return split(originalImage, moddedImage, splitWidth);
  }

}
