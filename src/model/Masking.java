package model;

import controller.Image;
import controller.Pixel;

class Masking {

  protected Image applyMask(Image original, Image modded, Image maskImage)
      throws IllegalArgumentException {
    if (maskImage.getWidth() != original.getWidth()
            || maskImage.getHeight() != original.getHeight()) {
      throw new IllegalArgumentException("Mask image dimensions must match the source "
          + "image dimensions.");
    }
    int width = original.getWidth();
    int height = modded.getHeight();
    Image finalImage = original.createImage(width, height);

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        Pixel maskPixel = maskImage.getPixel(x, y);
        Pixel sourcePixel = original.getPixel(x, y);
        Pixel processedPixel = modded.getPixel(x, y);

        // If the mask pixel is black, use the processed pixel; otherwise, keep the source pixel
        double maskBrightness = (maskPixel.getRed() + maskPixel.getGreen()
                + maskPixel.getBlue()) / 3.0;
        Pixel finalPixel = maskBrightness < 128 ? processedPixel : sourcePixel;
        finalImage.setPixel(x, y, finalPixel);
      }
    }

    return finalImage;
  }
}

