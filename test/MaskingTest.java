import org.junit.Test;

import java.io.IOException;

import controller.FileControllerImpl;
import controller.Image;
import controller.ImageFactory;
import controller.OtherImages;
import controller.PixelImpl;
import model.ImageDB;
import model.Load;
import model.Sepia;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

/**
 * Masking testfile.
 */
public class MaskingTest {

  @org.junit.Test
  public void sepiaMask() {
    String[] script = {"load res/testing.png testing",
        "load res/testingMask.png testingMask",
        "sepia testing testingMask testing-sepia-mask",
        "save res/testing-sepia-mask.png testing-sepia-mask"};
    FileControllerImpl obj = new FileControllerImpl();
    try {
      obj.processScript(script);
      assertTrue(true);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  public void maskTest() throws IOException {
    ImageDB imageDB = new ImageDB();
    Image expected = new OtherImages(2, 2);
    expected.setPixel(0, 0, new PixelImpl(100, 88, 69));
    expected.setPixel(1, 0, new PixelImpl(0, 0, 255));
    expected.setPixel(0, 1, new PixelImpl(0, 255, 0));
    expected.setPixel(1, 1, new PixelImpl(0, 100, 200));
    Image image;
    Image image2;
    String path = "testing.png";
    String path2 = "testingMask.png";
    try {
      image = ImageFactory.loadImage(path);
    } catch (Exception e) {
      throw new IOException("Failed to load image: " + path + e);
    }
    try {
      image2 = ImageFactory.loadImage(path2);
    } catch (Exception e) {
      throw new IOException("Failed to load image: " + path + e);
    }
    new Load().loadToDb("testing", image, imageDB);
    new Load().loadToDb("testingMask", image2, imageDB);
    //images.put("testing", image);
    Sepia sepia = new Sepia();
    Image[] actual = sepia.process(new String[]{"sepia",
        "testing", "testingMask", "testing-sepia-mask"},
        imageDB);
    Image actual_level_adjust = actual[0];
    for (int i = 0; i < actual.length; i++) {
      for (int j = 0; j < actual[i].getWidth(); j++) {
        assertArrayEquals(expected.getPixel(i, j).getRGB(),
            actual_level_adjust.getPixel(i, j).getRGB(), 1);
      }
    }
  }


}