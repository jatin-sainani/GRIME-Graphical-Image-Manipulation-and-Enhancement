import org.junit.Test;

import java.io.IOException;

import controller.Image;
import controller.ImageFactory;
import controller.OtherImages;
import controller.PixelImpl;
import model.ColorCorrect;
import model.ImageDB;
import model.Load;

import static org.junit.Assert.assertArrayEquals;

/**
 * ColorCorrectTest class.
 */
public class ColorCorrectTest {

  @Test
  public void ColorCorrect() throws IOException {
    ImageDB imageDB = new ImageDB();
    Image expected = new OtherImages(2, 2);
    expected.setPixel(0, 0, new PixelImpl(255, 0, 0)); // Pixel at (0, 0)
    expected.setPixel(1, 0, new PixelImpl(100, 0, 155)); // Pixel at (1, 0)
    expected.setPixel(0, 1, new PixelImpl(100, 255, 0)); // Pixel at (0, 1)
    expected.setPixel(1, 1, new PixelImpl(100, 100, 100));
    Image image;
    String path = "testing.png";
    try {
      image = ImageFactory.loadImage(path);
    } catch (Exception e) {
      throw new IOException("Failed to load image: " + path + e);
    }
    new Load().loadToDb("testing", image, imageDB);
    ColorCorrect colorCorrect = new ColorCorrect();
    Image[] actual = colorCorrect.process(new String[]{
        "color-correct",
        "testing", "testing-color-correct"},
        imageDB);
    Image actual_color_correct = actual[0];
    for (int i = 0; i < actual.length; i++) {
      for (int j = 0; j < actual[i].getWidth(); j++) {
        assertArrayEquals(expected.getPixel(i, j).getRGB(),
            actual_color_correct.getPixel(i, j).getRGB(), 1);
      }
    }
  }

  @Test
  public void ColorCorrectWrongPixels() throws IOException {
    ImageDB imageDB = new ImageDB();
    Image expected = new OtherImages(2, 2);
    expected.setPixel(0, 0, new PixelImpl(255, 0, 0)); // Pixel at (0, 0)
    expected.setPixel(1, 0, new PixelImpl(0, 0, 155)); // Pixel at (1, 0)
    expected.setPixel(0, 1, new PixelImpl(100, 255, 0)); // Pixel at (0, 1)
    expected.setPixel(1, 1, new PixelImpl(100, 100, 100));
    Image image;
    String path = "testing.png";
    try {
      image = ImageFactory.loadImage(path);
    } catch (Exception e) {
      throw new IOException("Failed to load image: " + path + e);
    }
    new Load().loadToDb("testing", image, imageDB);
    ColorCorrect colorCorrect = new ColorCorrect();
    Image[] actual = colorCorrect.process(new String[]{
        "color-correct",
        "testing", "testing-color-correct"},
        imageDB);
    Image actual_color_correct = actual[0];
    try {
      for (int i = 0; i < actual.length; i++) {
        for (int j = 0; j < actual[i].getWidth(); j++) {
          assertArrayEquals(expected.getPixel(i, j).getRGB(),
              actual_color_correct.getPixel(i, j).getRGB(), 1);
        }
      }
    } catch (Exception e) {
      throw new IOException("Pixels values do not match " + e);
    }

  }


}