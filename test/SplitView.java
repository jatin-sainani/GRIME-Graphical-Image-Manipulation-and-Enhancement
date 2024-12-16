import java.io.IOException;

import controller.FileControllerImpl;
import controller.Image;
import controller.ImageFactory;
import controller.OtherImages;
import controller.PixelImpl;
import model.Blue;
import model.ImageDB;
import model.Load;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Split View Test class.
 */
public class SplitView {
  @org.junit.Test
  public void splitView210() throws IOException {
    String[] script = {"load testing.png testing",
        "blue-component testing testing-split split 210",
        "save testing-split.png testing-split"};
    FileControllerImpl obj = new FileControllerImpl();
    try {
      obj.processScript(script);
      fail();
    } catch (IOException | IllegalArgumentException e) {
      assertEquals("The split width must be between 0 and 2", e.getMessage());
      //throw new RuntimeException(e);
    }
  }

  @org.junit.Test
  public void splitViewNegative() throws IOException {
    String[] script = {"load testing.png testing",
        "blue-component testing testing-split split -200",
        "save testing-split.png testing-split"};
    FileControllerImpl obj = new FileControllerImpl();
    try {
      obj.processScript(script);
      fail();
    } catch (IOException | IllegalArgumentException e) {
      assertEquals("The split width must be between 0 and 2", e.getMessage());
      //throw new RuntimeException(e);
    }
  }

  @org.junit.Test
  public void blueSplitPixel() throws IOException {
    ImageDB db = new ImageDB();
    Image expected = new OtherImages(2, 2);
    expected.setPixel(0, 0, new PixelImpl(0, 0, 0)); // Pixel at (0, 0)
    expected.setPixel(1, 0, new PixelImpl(0, 0, 255)); // Pixel at (1, 0)
    expected.setPixel(0, 1, new PixelImpl(0, 0, 0)); // Pixel at (0, 1)
    expected.setPixel(1, 1, new PixelImpl(0, 100, 200));


    Image image;
    String path = "testing.png";
    try {
      image = ImageFactory.loadImage(path);
    } catch (Exception e) {
      throw new IOException("Failed to load image: " + path + e);
    }
    new Load().loadToDb("testing", image, db);
    Blue blue = new Blue();
    Image[] actual = blue.process(new String[]{"blue-component",
        "testing", "testing-blue", "split", "50"},
        db);
    Image actual_compressed = actual[0];
    for (int i = 0; i < actual_compressed.getHeight(); i++) {
      for (int j = 0; j < actual_compressed.getWidth(); j++) {
        assertArrayEquals(expected.getPixel(i, j).getRGB(),
            actual_compressed.getPixel(i, j).getRGB(), 1);
      }
    }
  }

}
