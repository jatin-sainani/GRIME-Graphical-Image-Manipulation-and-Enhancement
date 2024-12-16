package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import controller.Image;
import controller.Pixel;
import controller.PixelImpl;

import static java.lang.Math.round;

/**
 * This class takes the image and compresses it as per the requirement.
 */
public class Compression implements ImageProcessing {
  private double rootTwo = Math.sqrt(2);


  /**
   * Processes images based on the provided arguments and a map of images.
   *
   * @param args   an array of strings representing the processing commands and parameters
   * @param images a map where keys are image identifiers and values are {@code Image} objects
   * @return an array of processed {@code Image} objects resulting from the processing commands
   */
  //compress percentage image-name dest-image-name
  @Override
  public Image[] process(String[] args, ImageDB images)
      throws IllegalArgumentException {
    if (args.length != 4) {
      throw new IllegalArgumentException();
    }
    int loss = Integer.parseInt(args[1]);
    Image obj = images.get(args[2]);
    int width = obj.getWidth();
    int height = obj.getHeight();
    double[][] r = new double[height][width];
    double[][] g = new double[height][width];
    double[][] b = new double[height][width];
    Image compressedImage = obj.createImage(width, height);
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {

        r[i][j] = obj.getPixel(j, i).getRed();
        g[i][j] = obj.getPixel(j, i).getGreen();
        b[i][j] = obj.getPixel(j, i).getBlue();

      }
    }

    double[][] transformedR = haar(r);
    double[][] transformedG = haar(g);
    double[][] transformedB = haar(b);

    double[][] lossyR = lossFunction(transformedR, loss);
    double[][] lossyG = lossFunction(transformedG, loss);
    double[][] lossyB = lossFunction(transformedB, loss);


    double[][] invertedR = haarInverse(lossyR);
    double[][] invertedG = haarInverse(lossyG);
    double[][] invertedB = haarInverse(lossyB);

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int clampedR = (int) Math.max(0, Math.min(255, round(invertedR[i][j])));
        int clampedG = (int) Math.max(0, Math.min(255, round(invertedG[i][j])));
        int clampedB = (int) Math.max(0, Math.min(255, round(invertedB[i][j])));
        Pixel pixel = new PixelImpl(clampedR, clampedG, clampedB);
        compressedImage.setPixel(j, i, pixel);
      }
    }

    return new Image[]{compressedImage};
  }

  /**
   * Extracts and returns the names of the commands from the provided arguments.
   *
   * @param args an array of strings representing the processing commands and parameters
   * @return an array of strings containing the names of the commands found in the arguments
   */
  @Override
  public String[] getName(String[] args) {
    return new String[]{args[3]};
  }


  //Transform helper function

  private double[] transform(double[] input) {
    int l = input.length;
    double[] output = new double[l];
    double[] avg = new double[l / 2];
    double[] diff = new double[l / 2];
    for (int i = 0; i < l; i += 2) { //adding i+2 to iterate over 2 terms in one loop iteration
      avg[i / 2] = (input[i] + input[i + 1]) / rootTwo;
      diff[i / 2] = (input[i] - input[i + 1]) / rootTwo;
    }
    System.arraycopy(avg, 0, output, 0, l / 2);
    System.arraycopy(diff, 0, output, l / 2, l / 2);
    return output;
  }

  //Inverse helper function
  private double[] invert(double[] input) {
    int l = input.length;
    double[] output = new double[l];
    double[] avg = new double[l / 2];
    double[] diff = new double[l / 2];
    int j = l / 2;
    for (int i = 0; i < l / 2; i++) {
      double a = input[i];
      double b = input[j];
      avg[i] = (a + b) / rootTwo;
      diff[i] = (a - b) / rootTwo;
      j++;
      //Adding alternate avg and diff
      output[i * 2] = avg[i];
      output[i * 2 + 1] = diff[i];
    }
    return output;
  }


  private boolean isPowerOfTwo(int n) {
    // A power of 2 has only one bit set, so n & (n - 1) should be 0
    // Also, exclude 0 and negative numbers
    return n > 0 && (n & (n - 1)) == 0;
  }


  //  private double[] transformSequence(double[] input, int threshold) {
  // check if you need to take size as an input
  //    int l = input.length;
  //    if (!isPowerOfTwo(l)) {
  //      int power = 1;
  //      while (power < l) {
  //        power *= 2;
  //      }
  //      input = Arrays.copyOf(input, power);
  //      l = power;
  //    }
  //    double[] output = input.clone();
  //    double[] sublist;
  //    double[] transformSublist;
  //    while (l > 1) {
  //      sublist = Arrays.copyOfRange(output, 0, l);
  //      transformSublist = transform(sublist);
  //      System.arraycopy(transformSublist, 0, output, 0, l);
  //      l = l / 2;
  //    }
  //    for (int i = 0; i < output.length; i++) {
  //      if ((output[i] <= threshold) && (output[i] > 0)) {
  //        output[i] = 0;
  //      }
  //    }
  //    return output;
  //  }


  //  private double[] invertSequence(double[] input) { //check if you need to take size as an input
  //    int l = input.length;
  //    if (!isPowerOfTwo(l)) {
  //      int power = 1;
  //      while (power < l) {
  //        power *= 2;
  //      }
  //      input = Arrays.copyOf(input, power);
  //      l = power;
  //    }
  //    double[] output = input.clone();
  //    double[] sublist;
  //    double[] invertSublist;
  //    int m = 2;
  //    while (m <= l) {
  //      sublist = Arrays.copyOfRange(output, 0, m);
  //      invertSublist = invert(sublist);
  //      System.arraycopy(invertSublist, 0, output, 0, m);
  //      m = m * 2;
  //    }
  //    return output;
  //  }


  private double[][] haar(double[][] input) {
    int l = input.length;
    int w = input[0].length;
    int max = Math.max(l, w);
    if (!isPowerOfTwo(max)) {
      int power = 1;
      while (power < max) {
        power *= 2;
      }
      input = Arrays.copyOf(input, power);
      for (int i = 0; i < power; i++) {
        if (input[i] == null) {
          input[i] = new double[power];
        } else {
          input[i] = Arrays.copyOf(input[i], power);
        }
      }
      l = power;

    }


    double[][] output = input.clone();
    double[] sublistRow;
    double[] sublistColumn;

    while (l > 1) {

      //Transforming rows
      for (int i = 0; i < l; i++) {
        // Copy a sublist (partial row) up to length `l`
        sublistRow = Arrays.copyOfRange(output[i], 0, l);

        // Apply transformation to the row sublist
        double[] transformedRow = transform(sublistRow);

        // Update the row in the original array with the transformed values
        System.arraycopy(transformedRow, 0, output[i], 0, l);
      }

      //transforming columns of transformed matrix
      for (int j = 0; j < l; j++) {
        double[] column = new double[l];
        for (int k = 0; k < l; k++) {
          column[k] = output[k][j];
        }

        // create a sublist
        sublistColumn = Arrays.copyOfRange(column, 0, l);

        // Apply transformation to the column sublist
        double[] transformedColumn = transform(sublistColumn);

        for (int i = 0; i < l; i++) {
          output[i][j] = transformedColumn[i];
        }
      }
      l = l / 2;

    }
    return output;
  }

  private double[][] haarInverse(double[][] input) {
    int l = input.length;
    if (!isPowerOfTwo(l)) {
      int power = 1;
      while (power < l) {
        power *= 2;
      }
      input = Arrays.copyOf(input, power);
      l = power;
    }
    double[][] output = input.clone();
    double[] sublistRow;
    double[] sublistColumn;
    int m = 2;
    while (m <= l) {
      //Inverting columns first
      //transforming columns of transformed matrix
      for (int j = 0; j < m; j++) {
        double[] column = new double[m];
        for (int k = 0; k < m; k++) {
          column[k] = output[k][j];
          if (k == 136 && j == 442) {
            //System.out.println(column[k]);
          }
        }

        // Copy a sublist (partial column) up to length `l`
        sublistColumn = Arrays.copyOfRange(column, 0, m);

        // Apply transformation to the column sublist
        double[] invertedColumn = invert(sublistColumn);

        for (int i = 0; i < m; i++) {
          output[i][j] = invertedColumn[i];
        }
      }
      //Transforming rows
      for (int i = 0; i < m; i++) {
        // Copy a sublist (partial row) up to length `l`
        sublistRow = Arrays.copyOfRange(output[i], 0, m);

        if (i == 136) {
          double lol = 1.0f;
        }
        // Apply transformation to the row sublist
        double[] inversedRow = invert(sublistRow);

        // Update the row in the original array with the transformed values
        System.arraycopy(inversedRow, 0, output[i], 0, m);

      }
      m = m * 2;

    }
    return output;
  }


  private double[][] lossFunction(double[][] input, int threshold) throws IllegalArgumentException {
    if ((threshold < 0) || (threshold > 100)) {
      throw new IllegalArgumentException("Threshold must be between 0 and 100");
    }


    Set<Double> values = new TreeSet<>();

    // Add absolute values to the set to ensure they are unique and sorted
    for (double[] row : input) {
      for (double val : row) {
        values.add(Math.abs(val));
      }
    }

    // Step 2: Determine the threshold based on the sorted unique values
    int numToRemove = (int) (values.size() * threshold / 100.0);

    // Convert the TreeSet to a list to access by index
    List<Double> sortedUniqueValues = new ArrayList<>(values);

    // handling the case when the number to remove is the first number
    if (numToRemove != 0) {
      numToRemove--;
    }
    double thresholdValue = sortedUniqueValues.get(numToRemove);

    //Replace values in the original array based on the threshold
    for (int i = 0; i < input.length; i++) {
      for (int j = 0; j < input[i].length; j++) {
        if (Math.abs(input[i][j]) <= thresholdValue) {
          input[i][j] = 0;
        }
      }
    }

    return input;
  }


}

