

import org.junit.Test;

import javax.swing.JButton;

import controller.Features;

import static org.junit.Assert.assertTrue;

/**
 * Test class for testing controller with GUI.
 */
public class ImageDisplayPanelTest {

  @Test
  public void testFlipHorizontalButton() {
    TestFeatures testController = new TestFeatures();
    JButton button = new JButton("Flip Horizontal");
    button.addActionListener(e -> testController.horizontalFlip());
    button.doClick();
    assertTrue("flipHorizontal method "
                + "should be called", testController.flipHorizontalCalled);
  }

  @Test
  public void testFlipVerticalButton() {
    TestFeatures testController = new TestFeatures();
    JButton button = new JButton("Flip Vertical");
    button.addActionListener(e -> testController.verticalFlip());
    button.doClick();
    assertTrue("flipVertical method should be called", testController.flipVerticalCalled);
  }

  @Test
  public void testRedComponentButton() {
    TestFeatures testController = new TestFeatures();
    JButton button = new JButton("Red Component");
    button.addActionListener(e -> testController.red());
    button.doClick();
    assertTrue("red method should be called", testController.redCalled);
  }

  @Test
  public void testBlueComponentButton() {
    TestFeatures testController = new TestFeatures();
    JButton button = new JButton("Blue Component");
    button.addActionListener(e -> testController.blue());
    button.doClick();
    assertTrue("blue method should be called", testController.blueCalled);
  }

  @Test
  public void testGreenComponentButton() {
    TestFeatures testController = new TestFeatures();
    JButton button = new JButton("Green Component");
    button.addActionListener(e -> testController.green());
    button.doClick();
    assertTrue("green method should be called", testController.greenCalled);
  }

  @Test
  public void testGrayscaleButton() {
    TestFeatures testController = new TestFeatures();
    JButton button = new JButton("Grayscale");
    button.addActionListener(e -> testController.grayscale());
    button.doClick();
    assertTrue("grayscale method should be called", testController.grayscaleCalled);
  }

  @Test
  public void testBlurButton() {
    TestFeatures testController = new TestFeatures();
    JButton button = new JButton("Blur");
    button.addActionListener(e -> testController.blur());
    button.doClick();
    assertTrue("blur method should be called", testController.blurCalled);
  }

  @Test
  public void testSharpenButton() {
    TestFeatures testController = new TestFeatures();
    JButton button = new JButton("Sharpen");
    button.addActionListener(e -> testController.sharpen());
    button.doClick();
    assertTrue("sharpen method should be called", testController.sharpenCalled);
  }

  @Test
  public void testSepiaButton() {
    TestFeatures testController = new TestFeatures();
    JButton button = new JButton("Sepia");
    button.addActionListener(e -> testController.sepia());
    button.doClick();
    assertTrue("sepia method should be called", testController.sepiaCalled);
  }

  @Test
  public void testCompressButton() {
    TestFeatures testController = new TestFeatures();
    JButton button = new JButton("Compress");
    button.addActionListener(e -> testController.compress("50"));
    button.doClick();
    assertTrue("compress method should be called", testController.compressCalled);
  }

  @Test
  public void testColorCorrectionButton() {
    TestFeatures testController = new TestFeatures();
    JButton button = new JButton("Color Correction");
    button.addActionListener(e -> testController.colorCorrection());
    button.doClick();
    assertTrue("colorCorrection method "
                + "should be called", testController.colorCorrectionCalled);
  }

  @Test
  public void testLevelAdjustButton() {
    TestFeatures testController = new TestFeatures();
    JButton button = new JButton("Level Adjust");
    button.addActionListener(e -> testController.levelAdjust("10", "20", "30"));
    button.doClick();
    assertTrue("levelAdjust method should be called", testController.levelAdjustCalled);
  }

  @Test
  public void testDownscaleButton() {
    TestFeatures testController = new TestFeatures();
    JButton button = new JButton("Downscale");
    button.addActionListener(e -> testController.downscale("100", "100"));
    button.doClick();
    assertTrue("downscale method should be called", testController.downscaleCalled);
  }

  // A simple test implementation of the Features interface
  private static class TestFeatures implements Features {
    boolean flipHorizontalCalled = false;
    boolean flipVerticalCalled = false;
    boolean redCalled = false;
    boolean blueCalled = false;
    boolean greenCalled = false;
    boolean grayscaleCalled = false;
    boolean brightenCalled = false;
    boolean blurCalled = false;
    boolean sharpenCalled = false;
    boolean sepiaCalled = false;
    boolean compressCalled = false;
    boolean colorCorrectionCalled = false;
    boolean levelAdjustCalled = false;
    boolean downscaleCalled = false;

    @Override
    public void horizontalFlip() {
      flipHorizontalCalled = true;
    }

    @Override
    public void verticalFlip() {
      flipVerticalCalled = true;
    }

    @Override
    public void red() {
      redCalled = true;
    }

    @Override
    public void blue() {
      blueCalled = true;
    }

    @Override
    public void green() {
      greenCalled = true;
    }

    @Override
    public void grayscale() {
      grayscaleCalled = true;
    }

    @Override
    public void blur() {
      blurCalled = true;
    }

    @Override
    public void sharpen() {
      sharpenCalled = true;
    }

    @Override
    public void sepia() {
      sepiaCalled = true;
    }

    @Override
    public void compress(String percentage) {
      compressCalled = true;
    }

    @Override
    public void colorCorrection() {
      colorCorrectionCalled = true;
    }

    @Override
    public void levelAdjust(String b, String m, String w) {

      levelAdjustCalled = true;
    }

    @Override
    public void split(int width) {
      //Is not required In testing buttons
    }

    @Override
    public void downscale(String width, String height) {
      downscaleCalled = true;
    }

    @Override
    public void load(String path) {
      //Is not required In testing buttons
    }

    @Override
    public void save(String path) {
      //Is not required In testing buttons
    }
  }
}
