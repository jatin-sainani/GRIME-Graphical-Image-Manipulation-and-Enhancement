import org.junit.Test;

import java.io.IOException;


import controller.Image;
import controller.ImageFactory;
import controller.OtherImages;
import controller.PixelImpl;
import model.ImageDB;
import model.LevelsAdjust;
import model.Load;


import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * LevelAdjust Test class.
 */
public class LevelsAdjustTest {

  @Test
  public void LevelsAdjust20_100_200() throws IOException {
    ImageDB imageDB = new ImageDB();
    Image expected = new OtherImages(2, 2);
    expected.setPixel(0, 0, new PixelImpl(255, 0, 0)); // Pixel at (0, 0)
    expected.setPixel(1, 0, new PixelImpl(0, 0, 255)); // Pixel at (1, 0)
    expected.setPixel(0, 1, new PixelImpl(0, 255, 0)); // Pixel at (0, 1)
    expected.setPixel(1, 1, new PixelImpl(0, 128, 255));
    Image image;
    String path = "testing.png";
    try {
      image = ImageFactory.loadImage(path);
    } catch (Exception e) {
      throw new IOException("Failed to load image: " + path + e);
    }
    new Load().loadToDb("testing", image, imageDB);
    //images.put("testing", image);
    LevelsAdjust leveladjust = new LevelsAdjust();
    Image[] actual = leveladjust.process(new String[]{"levels-adjust", "20", "100",
        "200", "testing", "testing-levels-adjust"},
        imageDB);
    Image actual_level_adjust = actual[0];
    for (int i = 0; i < actual.length; i++) {
      for (int j = 0; j < actual[i].getWidth(); j++) {
        assertArrayEquals(expected.getPixel(i, j).getRGB(),
            actual_level_adjust.getPixel(i, j).getRGB(), 1);
      }
    }
  }

  @Test
  public void LevelsAdjust_bOutOfBound() throws IOException {
    ImageDB imageDB = new ImageDB();
    Image expected = new OtherImages(2, 2);
    expected.setPixel(0, 0, new PixelImpl(255, 0, 0)); // Pixel at (0, 0)
    expected.setPixel(1, 0, new PixelImpl(0, 0, 255)); // Pixel at (1, 0)
    expected.setPixel(0, 1, new PixelImpl(0, 255, 0)); // Pixel at (0, 1)
    expected.setPixel(1, 1, new PixelImpl(0, 128, 255));
    Image image;
    String path = "testing.png";
    try {
      image = ImageFactory.loadImage(path);
    } catch (Exception e) {
      throw new IOException("Failed to load image: " + path + e);
    }
    new Load().loadToDb("testing", image, imageDB);
    LevelsAdjust leveladjust = new LevelsAdjust();
    try {
      Image[] actual = leveladjust.process(new String[]{"levels-adjust", "-20", "100",
        "200", "testing", "testing-levels-adjust"},
          imageDB);
      Image actual_level_adjust = actual[0];
      for (int i = 0; i < actual.length; i++) {
        for (int j = 0; j < actual[i].getWidth(); j++) {
          assertArrayEquals(expected.getPixel(i, j).getRGB(),
              actual_level_adjust.getPixel(i, j).getRGB(), 1);
        }
      }
    } catch (IllegalArgumentException e) {
      assertEquals("b, m, w values must be in ascending order and "
          + "between 0 and 255.", e.getMessage());
    }

  }

  @Test
  public void LevelsAdjust_mOutOfBound() throws IOException {
    ImageDB imageDB = new ImageDB();
    Image expected = new OtherImages(2, 2);
    expected.setPixel(0, 0, new PixelImpl(255, 0, 0)); // Pixel at (0, 0)
    expected.setPixel(1, 0, new PixelImpl(0, 0, 255)); // Pixel at (1, 0)
    expected.setPixel(0, 1, new PixelImpl(0, 255, 0)); // Pixel at (0, 1)
    expected.setPixel(1, 1, new PixelImpl(0, 128, 255));
    Image image;
    String path = "testing.png";
    try {
      image = ImageFactory.loadImage(path);
    } catch (Exception e) {
      throw new IOException("Failed to load image: " + path + e);
    }
    new Load().loadToDb("testing", image, imageDB);
    LevelsAdjust leveladjust = new LevelsAdjust();
    try {
      Image[] actual = leveladjust.process(new String[]{"levels-adjust", "20", "300",
        "200", "testing", "testing-levels-adjust"},
          imageDB);
      Image actual_level_adjust = actual[0];
      for (int i = 0; i < actual.length; i++) {
        for (int j = 0; j < actual[i].getWidth(); j++) {
          assertArrayEquals(expected.getPixel(i, j).getRGB(),
              actual_level_adjust.getPixel(i, j).getRGB(), 1);
        }
      }
    } catch (IllegalArgumentException e) {
      assertEquals("b, m, w values must be in ascending order and "
          + "between 0 and 255.", e.getMessage());
    }

  }

  @Test
  public void LevelsAdjust_wOutOfBound() throws IOException {
    ImageDB imageDB = new ImageDB();
    Image expected = new OtherImages(2, 2);
    expected.setPixel(0, 0, new PixelImpl(255, 0, 0)); // Pixel at (0, 0)
    expected.setPixel(1, 0, new PixelImpl(0, 0, 255)); // Pixel at (1, 0)
    expected.setPixel(0, 1, new PixelImpl(0, 255, 0)); // Pixel at (0, 1)
    expected.setPixel(1, 1, new PixelImpl(0, 128, 255));
    Image image;
    String path = "testing.png";
    try {
      image = ImageFactory.loadImage(path);
    } catch (Exception e) {
      throw new IOException("Failed to load image: " + path + e);
    }
    new Load().loadToDb("testing", image, imageDB);

    LevelsAdjust leveladjust = new LevelsAdjust();
    try {
      Image[] actual = leveladjust.process(new String[]{"levels-adjust", "20", "100",
        "400", "testing", "testing-levels-adjust"},
          imageDB);
      Image actual_level_adjust = actual[0];
      for (int i = 0; i < actual.length; i++) {
        for (int j = 0; j < actual[i].getWidth(); j++) {
          assertArrayEquals(expected.getPixel(i, j).getRGB(),
              actual_level_adjust.getPixel(i, j).getRGB(), 1);
        }
      }
    } catch (IllegalArgumentException e) {
      assertEquals("b, m, w values must be in ascending order and "
          + "between 0 and 255.", e.getMessage());
    }

  }

  @Test
  public void LevelsAdjust_bGreaterThanm() throws IOException {

    ImageDB imageDB = new ImageDB();
    Image expected = new OtherImages(2, 2);
    expected.setPixel(0, 0, new PixelImpl(255, 0, 0)); // Pixel at (0, 0)
    expected.setPixel(1, 0, new PixelImpl(0, 0, 255)); // Pixel at (1, 0)
    expected.setPixel(0, 1, new PixelImpl(0, 255, 0)); // Pixel at (0, 1)
    expected.setPixel(1, 1, new PixelImpl(0, 128, 255));
    Image image;
    String path = "testing.png";
    try {
      image = ImageFactory.loadImage(path);
    } catch (Exception e) {
      throw new IOException("Failed to load image: " + path + e);
    }
    new Load().loadToDb("testing", image, imageDB);

    LevelsAdjust leveladjust = new LevelsAdjust();
    try {
      Image[] actual = leveladjust.process(new String[]{"levels-adjust", "100", "50",
        "200", "testing", "testing-levels-adjust"},
          imageDB);
      Image actual_level_adjust = actual[0];
      for (int i = 0; i < actual.length; i++) {
        for (int j = 0; j < actual[i].getWidth(); j++) {
          assertArrayEquals(expected.getPixel(i, j).getRGB(),
              actual_level_adjust.getPixel(i, j).getRGB(), 1);
        }
      }
    } catch (IllegalArgumentException e) {
      assertEquals("b, m, w values must be in ascending order and "
          + "between 0 and 255.", e.getMessage());
    }

  }

  @Test
  public void LevelsAdjust_mGreaterThanw() throws IOException {

    ImageDB imageDB = new ImageDB();
    Image expected = new OtherImages(2, 2);
    expected.setPixel(0, 0, new PixelImpl(255, 0, 0)); // Pixel at (0, 0)
    expected.setPixel(1, 0, new PixelImpl(0, 0, 255)); // Pixel at (1, 0)
    expected.setPixel(0, 1, new PixelImpl(0, 255, 0)); // Pixel at (0, 1)
    expected.setPixel(1, 1, new PixelImpl(0, 128, 255));
    Image image;
    String path = "testing.png";
    try {
      image = ImageFactory.loadImage(path);
    } catch (Exception e) {
      throw new IOException("Failed to load image: " + path + e);
    }
    new Load().loadToDb("testing", image, imageDB);

    LevelsAdjust leveladjust = new LevelsAdjust();
    try {
      Image[] actual = leveladjust.process(new String[]{"levels-adjust", "20", "230",
        "200", "testing", "testing-levels-adjust"},
          imageDB);
      Image actual_level_adjust = actual[0];
      for (int i = 0; i < actual.length; i++) {
        for (int j = 0; j < actual[i].getWidth(); j++) {
          assertArrayEquals(expected.getPixel(i, j).getRGB(),
              actual_level_adjust.getPixel(i, j).getRGB(), 1);
        }
      }
    } catch (IllegalArgumentException e) {
      assertEquals("b, m, w values must be in ascending order and "
          + "between 0 and 255.", e.getMessage());
    }

  }

  @Test
  public void LevelsAdjust_bGreaterThanw() throws IOException {

    ImageDB imageDB = new ImageDB();
    Image expected = new OtherImages(2, 2);
    expected.setPixel(0, 0, new PixelImpl(255, 0, 0)); // Pixel at (0, 0)
    expected.setPixel(1, 0, new PixelImpl(0, 0, 255)); // Pixel at (1, 0)
    expected.setPixel(0, 1, new PixelImpl(0, 255, 0)); // Pixel at (0, 1)
    expected.setPixel(1, 1, new PixelImpl(0, 128, 255));
    Image image;
    String path = "testing.png";
    try {
      image = ImageFactory.loadImage(path);
    } catch (Exception e) {
      throw new IOException("Failed to load image: " + path + e);
    }
    new Load().loadToDb("testing", image, imageDB);

    LevelsAdjust leveladjust = new LevelsAdjust();
    try {
      Image[] actual = leveladjust.process(new String[]{"levels-adjust", "200", "230",
        "180", "testing", "testing-levels-adjust"},
          imageDB);
      Image actual_level_adjust = actual[0];
      for (int i = 0; i < actual.length; i++) {
        for (int j = 0; j < actual[i].getWidth(); j++) {
          assertArrayEquals(expected.getPixel(i, j).getRGB(),
              actual_level_adjust.getPixel(i, j).getRGB(), 1);
        }
      }
    } catch (IllegalArgumentException e) {
      assertEquals("b, m, w values must be in ascending order and "
          + "between 0 and 255.", e.getMessage());
    }

  }


}