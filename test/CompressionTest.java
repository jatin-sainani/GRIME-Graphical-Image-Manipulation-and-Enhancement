
import java.io.IOException;

import controller.FileControllerImpl;
import controller.Image;
import controller.ImageFactory;
import controller.OtherImages;
import model.Compression;
import model.ImageDB;
import model.Load;
import controller.PixelImpl;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test class for compression.
 */
public class CompressionTest {
  //  @org.junit.Test
  //  public void transformTest() throws IOException {
  //    double[] r = new double[]{5, 3, 2, 4, 2, 1, 0, 3};
  //    double[] actual = new Compression().transform(r);
  //    double[] expected = new double[]{5.656, 4.242, 2.121, 2.121, 1.414, -1.414, 0.707, -2.121};
  //    assertArrayEquals(expected, actual, 0.001);
  //  }

  //  @org.junit.Test
  //  public void invertTest() throws IOException {
  //    double[] expected = new double[]{5, 3, 2, 4, 2, 1, 0, 3};
  //
  //    double[] input = new double[]{5.656, 4.242, 2.121, 2.121, 1.414, -1.414, 0.707, -2.121};
  //
  //    double[] actual = new Compression().invert(input);
  //    assertArrayEquals(expected, actual, 0.001);
  //  }

  //  @org.junit.Test
  //  public void transformSequence() throws IOException {
  //    double[] r = new double[]{5, 3, 2, 4, 2, 1, 0, 3};
  //    double[] actual = new Compression().transformSequence(r, 0);
  //    double[] expected = new double[]{7.071, 2.828, 1.000, 0.0, 1.414, -1.414, 0.707, -2.121};
  //    assertArrayEquals(expected, actual, 0.001);
  //  }

  //  @org.junit.Test
  //  public void invertSequence() throws IOException {
  //    double[] expected = new double[]{5, 3, 2, 4, 2, 1, 0, 3};
  //    double[] input = new double[]{7.071, 2.828, 1.000, 0.0, 1.414, -1.414, 0.707, -2.121};
  //    double[] actual = new Compression().invertSequence(input);
  //
  //    assertArrayEquals(expected, actual, 0.001);
  //  }

  //  @org.junit.Test
  //  public void transformSequenceWithThreshold() throws IOException {
  //    double[] r = new double[]{5, 3, 2, 4, 2, 1, 0, 3};
  //    double[] actual = new Compression().transformSequence(r, 1);
  //    double[] expected = new double[]{7.071, 2.828, 0, 0.0, 1.414, -1.414, 0, -2.121};
  //    assertArrayEquals(expected, actual, 0.001);
  //  }

  //  @org.junit.Test
  //  public void haar() throws IOException {
  //    double[][] r = new double[][]{{5, 2}, {1, 4}};
  //    double[][] actual = new Compression().haar(r);
  //    double[][] expected = new double[][]{{6, 0}, {1, 3}};
  //
  //    for (int i = 0; i < actual.length; i++) {
  //      assertArrayEquals(expected[i], actual[i], 0.001);
  //    }
  //  }

  //  @org.junit.Test
  //  public void haarInverse() throws IOException {
  //    double[][] input = new double[][]{{6, 0}, {1, 3}};
  //    double[][] expected = new double[][]{{5, 2}, {1, 4}};
  //    double[][] actual = new Compression().haarInverse(input);
  //
  //    for (int i = 0; i < actual.length; i++) {
  //      assertArrayEquals(expected[i], actual[i], 0.001);
  //      System.out.println(actual[i][0]);
  //      System.out.println(actual[i][1]);
  //    }
  //  }

  @org.junit.Test
  public void compress50() {
    String[] script = {"load testing.png MFAImage",
        "compress 50 MFAImage MFAImage-compressTest",
        "save testing-compression.png MFAImage-compressTest"};
    FileControllerImpl obj = new FileControllerImpl();
    try {
      obj.processScript(script);
      assertTrue(true);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @org.junit.Test
  public void compressionPixel() throws IOException {
    ImageDB db = new ImageDB();
    Image expected = new OtherImages(2, 2);
    expected.setPixel(0, 0, new PixelImpl(0, 0, 0)); // Pixel at (0, 0)
    expected.setPixel(1, 0, new PixelImpl(0, 0, 227)); // Pixel at (1, 0)
    expected.setPixel(0, 1, new PixelImpl(0, 177, 0)); // Pixel at (0, 1)
    expected.setPixel(1, 1, new PixelImpl(0, 177, 227));


    Image image;
    String path = "testing.png";
    try {
      image = ImageFactory.loadImage(path);
    } catch (Exception e) {
      throw new IOException("Failed to load image: " + path + e);
    }
    new Load().loadToDb("testing", image, db);
    Compression compression = new Compression();
    Image[] actual = compression.process(new String[]{
        "compress", "50", "testing",
        "testing-compress"},
        db);
    Image actual_compressed = actual[0];
    for (int i = 0; i < actual_compressed.getHeight(); i++) {
      for (int j = 0; j < actual_compressed.getWidth(); j++) {
        assertArrayEquals(expected.getPixel(i, j).getRGB(),
            actual_compressed.getPixel(i, j).getRGB(), 1);
      }
    }
  }


  @org.junit.Test
  public void compress() {
    String[] script = {"load manhattan-small.png man",
        "compress 20 man man-20",
        "compress 50 man man-50",
        "compress 90 man man-90",
        "save man20.png man-20",
        "save man50.png man-50",
        "save man90.png man-90"};
    FileControllerImpl obj = new FileControllerImpl();
    try {
      obj.processScript(script);
      assertTrue(true);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @org.junit.Test
  public void compress110() throws IllegalArgumentException {
    String[] script = {"load testing.png testing",
        "compress 110 testing testing-110",
        "save koala-110.png testing-110"};
    FileControllerImpl obj = new FileControllerImpl();
    try {
      obj.processScript(script);
    } catch (IOException | IllegalArgumentException e) {
      assertEquals("Threshold must be between 0 and 100", e.getMessage());
      //throw new RuntimeException(e);
    }
  }

  @org.junit.Test
  public void compressNegative() throws IllegalArgumentException {
    String[] script = {"load testing.png testing",
        "compress -10 testing testing-110",
        "save koala-110.png testing-110"};
    FileControllerImpl obj = new FileControllerImpl();
    try {
      obj.processScript(script);
    } catch (IOException | IllegalArgumentException e) {
      assertEquals("Threshold must be between 0 and 100", e.getMessage());
      //throw new RuntimeException(e);
    }
  }

  //  @Test
  //  public void Jar() throws IOException {
  //    ImageManipulation imageManipulation = new ImageManipulation();
  //    imageManipulation.main(new String[]{"-text"});
  //  }


  //  @org.junit.Test
  //  public void transformSequenceNotPowerOfTwo() throws IOException {
  //    double[] r = new double[]{5, 3, 2, 4, 2, 1, 0};
  //    double[] actual= new Compression().transformSequence(r);
  //    double[] expected = new double[]{7.071, 2.828, 1.000, 0.0, 1.414, -1.414, 0.707, -2.121};
  //    assertArrayEquals(expected, actual, 0.001);
  //  }

}
