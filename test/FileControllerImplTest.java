import java.io.IOException;

import controller.FileController;
import controller.FileControllerImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * A JUnit test class for the Controller class.
 */
public class FileControllerImplTest {

  @org.junit.Test
  public void scriptNotFound() {
    String[] script = {
        "run script/Scriptssss.txt"};
    FileControllerImpl obj = new FileControllerImpl();
    try {
      obj.processScript(script);
    } catch (IOException | IllegalArgumentException e) {
      assertEquals("Error reading script file: script/Scriptssss.txt", e.getMessage());
      //throw new RuntimeException(e);
    }
  }

  @org.junit.Test
  public void levelAdjustTestingImage() throws IOException {
    String[] script = {
        "load testing.png testing",
        "levels-adjust 20 100 200 testing testing-levels-adjust",
        "histogram testing testing-histogram",
        "save resImages/testing-histogram.jpeg testing-histogram",
        "histogram testing-levels-adjust testing-levels-adjust-histogram",
        "save resImages/testing-levels-adjust.png testing-levels-adjust",
        "save resImages/testing-levels-adjust-histogram.png testing-levels-adjust-histogram"

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
  public void colorCorrectTestingImage() throws IOException {
    String[] script = {
        "load testing.png testing",
        "color-correct testing testing-color-correct",
        "histogram testing-color-correct testing-color-correct-histogram",
        "save resImages/testing-color-correct.png testing-color-correct",
        "save resImages/testing-color-correct-histogram.png testing-color-correct-histogram"

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
  public void histogramTest() throws IOException {
    String[] script = {
        "load MFAImage.jpeg MFAImage",
        "histogram MFAImage MFAImage-histogram",
        "save resImages/MFAImage-histogram.jpeg MFAImage-histogram"

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
  public void colorCorrect() throws IOException {
    String[] script = {
        "load MFAImage.jpeg MFAImage",
        "color-correct MFAImage MFAImage-color-correct",
        "histogram MFAImage-color-correct MFAImage-color-correct-histogram",
        "save resImages/MFAImage-color-correct.jpeg MFAImage-color-correct",
        "save resImages/MFAImage-color-correct-histogram.jpeg MFAImage-color-correct-histogram"

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
  public void colorCorrectGalaxy() throws IOException {
    String[] script = {
        "load galaxy-adjusted.png galaxy-adjusted",
        "color-correct galaxy-adjusted galaxy-adjusted-color-correct",
        "save galaxy-adjusted-color-correct.png galaxy-adjusted-color-correct",

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
  public void levelAdjust() throws IOException {
    String[] script = {
        "load MFAImage.jpeg MFAImage",
        "levels-adjust 20 100 200 MFAImage MFAImage-levels-adjust",
        "save resImages/MFAImage-levels-adjust.jpeg MFAImage-levels-adjust",

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
  public void script() {
    String[] script = {
        "run script/Script.txt"};
    FileControllerImpl obj = new FileControllerImpl();
    try {
      obj.processScript(script);
      assertTrue(true);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @org.junit.Test
  public void split() {
    String[] script = {
        "load MFAImage.jpeg MFAImage",
        "rgb-split MFAImage MFAImage-red MFAImage-green MFAImage-blue",
        "save MFAImage-red.jpeg MFAImage-red",
        "save MFAImage-green.jpeg MFAImage-green",
        "save MFAImage-blue.jpeg MFAImage-blue"};
    FileControllerImpl obj = new FileControllerImpl();
    try {
      obj.processScript(script);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    assertTrue(true);
  }

  @org.junit.Test
  public void combine() {
    String[] script = {"load MFAImage.jpeg MFAImage",
        "rgb-split MFAImage MFAImage-red MFAImage-green MFAImage-blue",
        "rgb-combine MFAImage-combined MFAImage-red MFAImage-green MFAImage-blue",
        "save resImages/MFAImage-combined.jpeg MFAImage-combined"};
    FileControllerImpl obj = new FileControllerImpl();
    try {
      obj.processScript(script);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    assertTrue(true);
  }

  @org.junit.Test
  public void testScript() {
    String[] script = {
        "#load MFAImage.jpeg and call it 'MFAImage'",
        "load MFAImage.jpeg MFAImage",
        "#brighten MFAImage by adding 10",
        "brighten 10 MFAImage MFAImage-brighter",
        "#flip MFAImage vertically",
        "vertical-flip MFAImage MFAImage-vertical",
        "#flip the vertically flipped koala horizontally",

        "horizontal-flip MFAImage-vertical MFAImage-vertical-horizontal",
        "#create a greyscale using only the value component, as an image MFAImage-greyscale",
        "value-component MFAImage MFAImage-greyscale",
        "#save MFAImage-brighter",
        "save resImages/MFAImage-brighter.jpeg MFAImage-brighter",
        "#save MFAImage-greyscale",
        "save resImages/MFAImage-gs.jpeg MFAImage-greyscale",
        "#overwrite MFAImage with another file",
        "load MFAImage2.jpeg MFAImage",
        "#give the MFAImage a red tint",
        "rgb-split MFAImage MFAImage-red MFAImage-green MFAImage-blue",
        "#brighten just the red image",
        "brighten 50 MFAImage-red MFAImage-red",
        "#combine them back, but by using the brightened red we get a red tint",
        "rgb-combine MFAImage-red-tint MFAImage-red MFAImage-green MFAImage-blue",
        "save resImages/MFAImage-red-tint.jpeg MFAImage-red-tint"};
    FileControllerImpl obj = new FileControllerImpl();
    try {
      obj.processScript(script);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    assertTrue(true);
  }

  @org.junit.Test
  public void fileConversion() {
    String[] script = {"load MFAImage.jpeg MFAImage",
        "rgb-split MFAImage MFAImage-red MFAImage-green MFAImage-blue",
        "save resImages/MFAImage-red.png MFAImage-red",
        "save resImages/MFAImage-green.jpeg MFAImage-green",
        "save resImages/MFAImage-blue.ppm MFAImage-blue"};
    FileControllerImpl obj = new FileControllerImpl();
    try {
      obj.processScript(script);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    assertTrue(true);
  }

  @org.junit.Test
  public void red() {
    String[] script = {"load MFAImage.jpeg MFAImage",
        "red-component MFAImage MFAImage-red",
        "save resImages/MFAImage-red.jpeg MFAImage-red"};
    FileControllerImpl obj = new FileControllerImpl();
    try {
      obj.processScript(script);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    assertTrue(true);
  }

  @org.junit.Test
  public void load() {
    String[] script = {"load testing.ppm testing"};
    FileControllerImpl obj = new FileControllerImpl();
    try {
      obj.processScript(script);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    assertTrue(true);
  }

  @org.junit.Test
  public void redPng() {
    String[] script = {"load testing.png testing",
        "red-component testing testing-red",
        "save testing-red.png testing-red"};
    FileControllerImpl obj = new FileControllerImpl();
    try {
      obj.processScript(script);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    assertTrue(true);
  }

  @org.junit.Test
  public void bluePng() {
    String[] script = {"load testing.png testing",
        "blue-component testing testing-blue",
        "save testing-blue.png testing-blue"};
    FileControllerImpl obj = new FileControllerImpl();
    try {
      obj.processScript(script);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    assertTrue(true);
  }

  @org.junit.Test
  public void greenPng() {
    String[] script = {"load testing.png testing",
        "green-component testing testing-green",
        "save testing-green.png testing-green"};
    FileControllerImpl obj = new FileControllerImpl();
    try {
      obj.processScript(script);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    assertTrue(true);
  }

  @org.junit.Test
  public void blurPng() {
    String[] script = {"load testing.png testing",
        "blur testing testing-blur",
        "save testing-blur.png testing-blur"};
    FileControllerImpl obj = new FileControllerImpl();
    try {
      obj.processScript(script);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    assertTrue(true);
  }

  @org.junit.Test
  public void sharpenPng() {
    String[] script = {"load testing.png testing",
        "sharpen testing testing-sharpen",
        "save testing-sharpen.png testing-sharpen"};
    FileControllerImpl obj = new FileControllerImpl();
    try {
      obj.processScript(script);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    assertTrue(true);
  }

  @org.junit.Test
  public void lumaPng() {
    String[] script = {"load testing.png testing",
        "luma-component testing testing-luma",
        "save testing-luma.png testing-luma"};
    FileControllerImpl obj = new FileControllerImpl();
    try {
      obj.processScript(script);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    assertTrue(true);
  }

  @org.junit.Test
  public void luma() {
    String[] script = {"load MFAImage.jpeg MFAImage",
        "luma-component MFAImage MFAImage-luma",
        "save resImages/MFAImage-luma.jpeg MFAImage-luma"};
    FileControllerImpl obj = new FileControllerImpl();
    try {
      obj.processScript(script);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    assertTrue(true);
  }

  @org.junit.Test
  public void intensityPng() {
    String[] script = {"load testing.png testing",
        "intensity-component testing testing-intensity",
        "save testing-intensity.png testing-intensity"};
    FileControllerImpl obj = new FileControllerImpl();
    try {
      obj.processScript(script);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    assertTrue(true);
  }

  @org.junit.Test
  public void intensity() {
    String[] script = {"load MFAImage.jpeg MFAImage",
        "intensity-component MFAImage MFAImage-intensity",
        "save resImages/MFAImage-intensity.jpeg MFAImage-intensity"};
    FileControllerImpl obj = new FileControllerImpl();
    try {
      obj.processScript(script);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    assertTrue(true);
  }

  @org.junit.Test
  public void valuePng() {
    String[] script = {"load testing.png testing",
        "value-component testing testing-value",
        "save testing-value.png testing-value"};
    FileControllerImpl obj = new FileControllerImpl();
    try {
      obj.processScript(script);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    assertTrue(true);
  }

  @org.junit.Test
  public void value() {
    String[] script = {"load MFAImage.jpeg MFAImage",
        "value-component MFAImage MFAImage-value",
        "save resImages/MFAImage-value.jpeg MFAImage-value"};
    FileControllerImpl obj = new FileControllerImpl();
    try {
      obj.processScript(script);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    assertTrue(true);
  }

  @org.junit.Test
  public void sepiaPng() {
    String[] script = {"load testing.png testing",
        "sepia testing testing-sepia",
        "save testing-sepia.png testing-sepia"};
    FileControllerImpl obj = new FileControllerImpl();
    try {
      obj.processScript(script);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    assertTrue(true);
  }

  @org.junit.Test
  public void splitPng() {
    String[] script = {"load testing.png testing",
        "rgb-split testing testing-red testing-green testing-blue",
        "save testing-red.png testing-red",
        "save testing-green.png testing-green",
        "save testing-blue.png testing-blue"};
    FileControllerImpl obj = new FileControllerImpl();
    try {
      obj.processScript(script);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    assertTrue(true);
  }

  @org.junit.Test
  public void redppm() {
    String[] script = {"load testing.ppm testing",
        "red-component testing testing-red",
        "save testing-red.ppm testing-red"};
    FileControllerImpl obj = new FileControllerImpl();
    try {
      obj.processScript(script);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    assertTrue(true);
  }

  @org.junit.Test
  public void processScript() {
    String[] script = {"load MFAImage.jpeg MFAImage",
        "red-component MFAImage MFAImage-red",
        "load testing.ppm testing",
        "red-component testing testing-red",
        "save MFAImage-red.jpeg MFAImage-red",
        "save testing-red.ppm testing-red"};
    FileController obj = new FileControllerImpl();
    try {
      obj.processScript(script);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    assertTrue(true);
  }

  @org.junit.Test
  public void processScriptBrighten() {
    String[] script = {"load MFAImage.jpeg MFAImage",
        "brighten 50 MFAImage MFAImage-brighter",
        "save resImages/MFAImage-brighter.jpeg MFAImage-brighter"};
    FileController obj = new FileControllerImpl();
    try {
      obj.processScript(script);
      assertTrue(true);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @org.junit.Test
  public void processScriptDarker() {
    String[] script = {"load MFAImage.jpeg MFAImage",
        "brighten -50 MFAImage MFAImage-darker",
        "save resImages/MFAImage-darker.jpeg MFAImage-darker"};
    FileController obj = new FileControllerImpl();
    try {
      obj.processScript(script);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    assertTrue(true);
  }

  @org.junit.Test
  public void processScriptBrightenAndDarken() {
    String[] script = {"load MFAImage.jpeg MFAImage",
        "brighten 50 MFAImage MFAImage-brighter",
        "brighten -50 MFAImage-brighter MFAImage-bright-dark",
        "save resImages/MFAImage-bright-dark.png MFAImage-bright-dark"};
    FileController obj = new FileControllerImpl();
    try {
      obj.processScript(script);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    assertTrue(true);
  }

  @org.junit.Test
  public void processScriptBlur() {
    String[] script = {"load MFAImage.jpeg MFAImage",
        "blur MFAImage MFAImage-blur",
        "save resImages/MFAImage-blur.jpeg MFAImage-blur"};
    FileController obj = new FileControllerImpl();
    try {
      obj.processScript(script);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    assertTrue(true);
  }

  @org.junit.Test
  public void processScriptBlurtwice() {
    String[] script = {"load MFAImage.jpeg MFAImage",
        "blur MFAImage MFAImage-blur",
        "blur MFAImage-blur MFAImage-blurtwice",
        "save resImages/MFAImage-blurtwice.jpeg MFAImage-blurtwice"};
    FileController obj = new FileControllerImpl();
    try {
      obj.processScript(script);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    assertTrue(true);
  }

  @org.junit.Test
  public void processScriptSharpen() {
    String[] script = {"load MFAImage.jpeg MFAImage",
        "sharpen MFAImage MFAImage-sharpen",
        "save resImages/MFAImage-sharpen.jpeg MFAImage-sharpen"};
    FileController obj = new FileControllerImpl();
    try {
      obj.processScript(script);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    assertTrue(true);
  }

  @org.junit.Test
  public void processScriptSepia() {
    String[] script = {"load MFAImage.jpeg MFAImage",
        "sepia MFAImage MFAImage-sepia",
        "save resImages/MFAImage-sepia.jpeg MFAImage-sepia"};
    FileController obj = new FileControllerImpl();
    try {
      obj.processScript(script);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    assertTrue(true);
  }

  @org.junit.Test
  public void processScriptFlipHorizontal() {
    String[] script = {"load MFAImage.jpeg MFAImage",
        "horizontal-flip MFAImage MFAImage-flipHorizontal",
        "save resImages/MFAImage-flipHorizontal.jpeg MFAImage-flipHorizontal"};
    FileController obj = new FileControllerImpl();
    try {
      obj.processScript(script);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    assertTrue(true);
  }

  @org.junit.Test
  public void processScriptFlipHorizontalWrongScript() {
    try {
      String[] script = {"load testing.png testing",
          "horizontal-flip",
          "save testing-flipHorizontal.png testing-flipHorizontal"};
      FileController obj = new FileControllerImpl();
      obj.processScript(script);
    } catch (IllegalArgumentException | IOException e) {
      assertEquals("flipHorizontal command requires an image name and output", e.getMessage());
    }
  }

  @org.junit.Test
  public void processScriptFlipVertical() {
    String[] script = {"load MFAImage.jpeg MFAImage",
        "vertical-flip MFAImage MFAImage-flipVertical",
        "save resImages/MFAImage-flipVertical.jpeg MFAImage-flipVertical"};
    FileController obj = new FileControllerImpl();
    try {
      obj.processScript(script);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    assertTrue(true);
  }

  @org.junit.Test
  public void processScriptFlipHorizontalVertical() {
    String[] script = {"load MFAImage.jpeg MFAImage",
        "horizontal-flip MFAImage MFAImage-flipHorizontal",
        "vertical-flip MFAImage-flipHorizontal MFAImage-flipHorizontalVertical",
        "save resImages/MFAImage-flipHorizontalVertical.jpeg MFAImage-flipHorizontalVertical"};
    FileController obj = new FileControllerImpl();
    try {
      obj.processScript(script);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    assertTrue(true);
  }

  @org.junit.Test
  public void blueSplit() {
    String[] script = {"load manhattan-small.png man",
        "blue-component man man-blue-split split 50",
        "save man-blue-split.png man-blue-split"};
    FileControllerImpl obj = new FileControllerImpl();
    try {
      obj.processScript(script);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    assertTrue(true);
  }

  @org.junit.Test
  public void redSplit() {
    String[] script = {"load manhattan-small.png man",
        "red-component man man-blue-split split 50",
        "save man-red-split.png man-blue-split"};
    FileControllerImpl obj = new FileControllerImpl();
    try {
      obj.processScript(script);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    assertTrue(true);
  }


  @org.junit.Test
  public void redMask() throws IOException {
    String[] script = {
        "load res/MFAImage.jpeg MFAImage",
        "value-component MFAImage MFAImage-value",
        "red-component MFAImage MFAImage-value MFAImage-red-mask",
        "save res/MFAImage-red-mask.jpeg MFAImage-red-mask",

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
  public void blurMask() throws IOException {
    String[] script = {
        "load res/MFAImage.jpeg MFAImage",
        "load res/MFAImageLuma.jpeg MFAImageLuma",
        "blur MFAImage MFAImageLuma MFAImage-blur-mask",
        "save res/MFAImage-blur-mask.jpeg MFAImage-blur-mask"

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
  public void sepiaMask() throws IOException {
    String[] script = {
        "load res/MFAImage.jpeg MFAImage",
        "load res/MFAImageLuma.jpeg MFAImageLuma",
        "sepia MFAImage MFAImageLuma MFAImage-sepia-mask",
        "save res/MFAImage-sepia-mask.jpeg MFAImage-sepia-mask"

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
