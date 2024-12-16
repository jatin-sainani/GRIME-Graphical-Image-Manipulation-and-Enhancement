import java.io.IOException;

import controller.FileControllerImpl;
import controller.Image;
import controller.ImageFactory;
import controller.OtherImages;
import controller.PixelImpl;
import model.DownScale;
import model.ImageDB;
import model.Load;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test class for downscale.
 */
public class DownScaleTest {


  @org.junit.Test
  public void downScalePixel() throws IOException {
    ImageDB db = new ImageDB();
    Image expected = new OtherImages(3, 2);
    expected.setPixel(0, 0, new PixelImpl(0, 0, 0)); // Pixel at (0, 0)
    expected.setPixel(1, 0, new PixelImpl(84, 84, 84)); // Pixel at (1, 0)
    expected.setPixel(2, 0, new PixelImpl(255, 255, 255));
    expected.setPixel(0, 1, new PixelImpl(255, 255, 255)); // Pixel at (0, 1)
    expected.setPixel(1, 1, new PixelImpl(170, 170, 170));
    expected.setPixel(2, 1, new PixelImpl(0, 0, 0));


    Image image;
    String path = "4x4.png";
    try {
      image = ImageFactory.loadImage(path);
    } catch (Exception e) {
      throw new IOException("Failed to load image: " + path + e);
    }
    new Load().loadToDb("testing", image, db);
    DownScale downScale = new DownScale();
    Image[] actual = downScale.process(new String[]{
        "", "testing", "3","2",
        "testing-downscale"},
        db);
    Image actual_downscaled = actual[0];
    for (int i = 0; i < actual_downscaled.getWidth(); i++) {
      for (int j = 0; j < actual_downscaled.getHeight(); j++) {
        assertArrayEquals(expected.getPixel(i, j).getRGB(),
            actual_downscaled.getPixel(i, j).getRGB(), 1);
      }
    }
  }

  @org.junit.Test
  public void downScaleSameRatio() throws IOException {
    String[] script = {
        "load res/MFAImage.jpeg MFAImage",
        "down-scale MFAImage 1000 1300 MFAImage-down-scale",
        "save res/MFAImage-down-scale.jpeg MFAImage-down-scale",

    };
    FileControllerImpl obj = new FileControllerImpl();
    try {
      obj.processScript(script);
      assertTrue(true);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @org.junit.Test
  public void downScaleDiffRatio() throws IOException {
    String[] script = {
        "load res/MFAImage.jpeg MFAImage",
        "down-scale MFAImage 900 1400 MFAImage-down-scale-2",
        "save res/MFAImage-down-scale-2.jpeg MFAImage-down-scale-2",

    };
    FileControllerImpl obj = new FileControllerImpl();
    try {
      obj.processScript(script);
      assertTrue(true);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

}