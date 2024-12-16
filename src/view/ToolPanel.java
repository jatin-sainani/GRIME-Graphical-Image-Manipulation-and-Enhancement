package view;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JCheckBox;
import javax.swing.JTextField;
import javax.swing.JFileChooser;
import javax.swing.JSlider;
import javax.swing.JDialog;
import javax.swing.filechooser.FileNameExtensionFilter;

//import java.awt.*;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Component;
import java.awt.Frame;

import java.awt.GridLayout;
import java.io.IOException;

import controller.Features;

/**
 * Class to define the toolpanel of the frame where all the buttons will be stored.
 */
public class ToolPanel extends JPanel implements ITool {
  private final JButton loadButton;
  private final JButton saveButton;
  private final JButton flipHorizontalButton;
  private final JButton flipVerticalButton;
  private final JButton redButton;
  private final JButton blueButton;
  private final JButton greenButton;
  private final JButton grayscaleButton;
  private final JButton blurButtonB;
  private final JButton sharpenButton;
  private final JButton sepiaButton;
  private final JButton compressionButton;
  private final JButton colorCorrectButton;
  private final JButton adjustColorButton;
  private final JButton downscaleButton;
  private final JCheckBox splitCheckBox;
  //private JSlider brightnessSlider;


  /**
   * Constructor class of the ToolPanel.
   */
  public ToolPanel() {
    // Initialize buttons and slider
    loadButton = new JButton("Load");
    saveButton = new JButton("Save");
    flipHorizontalButton = new JButton("Flip Horizontal");
    flipVerticalButton = new JButton("Flip Vertical");
    redButton = new JButton("Red");
    blueButton = new JButton("Blue");
    greenButton = new JButton("Green");
    grayscaleButton = new JButton("Grayscale");
    blurButtonB = new JButton("Blur");
    sharpenButton = new JButton("Sharpen");
    sepiaButton = new JButton("Sepia");
    compressionButton = new JButton("Compression");
    colorCorrectButton = new JButton("Color Correct");
    adjustColorButton = new JButton("Adjust Color");
    downscaleButton = new JButton("Downscale");
    splitCheckBox = new JCheckBox("Split");
    //brightnessSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 0);


    // Set layout for the panel
    setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(5, 5, 5, 5); // Add padding
    gbc.fill = GridBagConstraints.HORIZONTAL;

    // Add components in a structured layout
    gbc.gridx = 0;
    gbc.gridy = 0;
    add(loadButton, gbc);
    gbc.gridx = 1;
    gbc.gridy = 0;
    add(saveButton, gbc);

    gbc.gridx = 2;
    gbc.gridy = 0;
    add(flipHorizontalButton, gbc);
    gbc.gridx = 3;
    gbc.gridy = 0;
    add(flipVerticalButton, gbc);

    gbc.gridx = 4;
    gbc.gridy = 0;
    add(redButton, gbc);
    gbc.gridx = 5;
    gbc.gridy = 0;
    add(blueButton, gbc);
    gbc.gridx = 6;
    gbc.gridy = 0;
    add(greenButton, gbc);
    gbc.gridx = 7;
    gbc.gridy = 0;
    add(grayscaleButton, gbc);

    gbc.gridx = 8;
    gbc.gridy = 0;
    add(blurButtonB, gbc);
    gbc.gridx = 9;
    gbc.gridy = 0;
    add(sharpenButton, gbc);
    gbc.gridx = 10;
    gbc.gridy = 0;
    add(sepiaButton, gbc);

    gbc.gridx = 0;
    gbc.gridy = 1;
    add(colorCorrectButton, gbc);
    gbc.gridx = 1;
    gbc.gridy = 1;
    add(adjustColorButton, gbc);
    gbc.gridx = 2;
    gbc.gridy = 1;
    add(compressionButton, gbc);
    gbc.gridx = 3;
    gbc.gridy = 1;
    add(downscaleButton, gbc);

    gbc.gridx = 4;
    gbc.gridy = 1;
    gbc.gridwidth = 1;
    add(splitCheckBox, gbc);
  }

  /**
   * Adding listeners to all the buttons.
   *
   * @param features feature interface that contains all the features.
   */
  public void addFeatures(Features features) {
    Runnable enableSplitCheckBox = () -> splitCheckBox.setEnabled(true);
    Runnable disableSplitCheckBox = () -> splitCheckBox.setEnabled(false);
    loadButton.addActionListener(evt -> {
      try {
        features.load(openFile());
      } catch (IOException | IllegalArgumentException e) {
        JOptionPane.showMessageDialog(this,
            "An error occurred while loading the file: " + e.getMessage(),
            "Error",
            JOptionPane.ERROR_MESSAGE);
      }
      disableSplitCheckBox.run();
    });
    saveButton.addActionListener(evt -> {
      try {
        features.save(saveFile());
      } catch (IOException | IllegalArgumentException e) {
        JOptionPane.showMessageDialog(this,
            "An error occurred while saving the file: " + e.getMessage(),
            "Error",
            JOptionPane.ERROR_MESSAGE);
      }
      disableSplitCheckBox.run();
    });
    flipHorizontalButton.addActionListener(evt -> {
      features.horizontalFlip();
      disableSplitCheckBox.run();
    });
    flipVerticalButton.addActionListener(evt -> {
      features.verticalFlip();
      disableSplitCheckBox.run();
    });
    redButton.addActionListener(evt -> {
      features.red();
      enableSplitCheckBox.run();
    });
    blueButton.addActionListener(evt -> {
      features.blue();
      enableSplitCheckBox.run();
    });
    greenButton.addActionListener(evt -> {
      features.green();
      enableSplitCheckBox.run();
    });
    grayscaleButton.addActionListener(evt -> {
      features.grayscale();
      enableSplitCheckBox.run();
    });
    blurButtonB.addActionListener(evt -> {
      features.blur();
      enableSplitCheckBox.run();
    });
    sharpenButton.addActionListener(evt -> {
      features.sharpen();
      enableSplitCheckBox.run();
    });
    sepiaButton.addActionListener(evt -> {
      features.sepia();
      enableSplitCheckBox.run();
    });
    //brightnessSlider.addChangeListener(evt -> features.brighten(brightnessSlider.getValue()));
    colorCorrectButton.addActionListener(e -> {
      features.colorCorrection();
      enableSplitCheckBox.run();
    });
    compressionButton.addActionListener(e -> {
      String input = JOptionPane.showInputDialog(this,
          "Enter compression level (e.g., 1-100):",
          "Compression Input",
          JOptionPane.PLAIN_MESSAGE);
      if (input != null) {
        try {
          //int compressionLevel = Integer.parseInt(input);
          features.compress(input);
        } catch (IllegalArgumentException ex) {
          JOptionPane.showMessageDialog(this,
              "Invalid input! Please enter a valid integer.",
              "Error",
              JOptionPane.ERROR_MESSAGE);
        }
      }
    });
    adjustColorButton.addActionListener(evt -> {
      enableSplitCheckBox.run();
      JPanel panel = new JPanel(new GridLayout(4, 2));
      JTextField bField = new JTextField();
      JTextField mField = new JTextField();
      JTextField wField = new JTextField();
      panel.add(new JLabel("Enter value for black point:"));
      panel.add(bField);
      panel.add(new JLabel("Enter value for middle black point:"));
      panel.add(mField);
      panel.add(new JLabel("Enter value for white point:"));
      panel.add(wField);

      panel.add(new JLabel("Enter integer values in ascending order"));

      int result = JOptionPane.showConfirmDialog(this, panel,
          "Adjust Color Input", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

      if (result == JOptionPane.OK_OPTION) {
        try {
          features.levelAdjust(bField.getText(), mField.getText(), wField.getText());
        } catch (IllegalArgumentException e) {
          JOptionPane.showMessageDialog(this,
              "An error occurred while saving the file: " + e.getMessage(),
              "Error",
              JOptionPane.ERROR_MESSAGE);
        }
      }
    });
    splitCheckBox.addActionListener(e -> {
      setSplitCheckBox(features);
    });
    downscaleButton.addActionListener(e -> {
      disableSplitCheckBox.run();
      JPanel panel = new JPanel(new GridLayout(2, 2));
      JTextField width = new JTextField();
      JTextField height = new JTextField();

      panel.add(new JLabel("Enter integer value for width:"));
      panel.add(width);
      panel.add(new JLabel("Enter integer value for height:"));
      panel.add(height);

      int result = JOptionPane.showConfirmDialog(this, panel,
          "Adjust Color Input", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

      if (result == JOptionPane.OK_OPTION) {
        try {
          features.downscale(width.getText(), height.getText());
        } catch (IllegalArgumentException ex) {
          JOptionPane.showMessageDialog(this,
              "Invalid input! Please enter valid integers for height and width.",
              "Error",
              JOptionPane.ERROR_MESSAGE);
        }
      }
    });
  }

  private String openFile() {
    JFileChooser fchooser = new JFileChooser(".");
    FileNameExtensionFilter filter = new FileNameExtensionFilter(
        "JPG/JPEG/PNG/PPM Images", "jpg", "jpeg", "png", "ppm");
    fchooser.setFileFilter(filter);
    int retvalue = fchooser.showOpenDialog(this);
    if (retvalue == JFileChooser.APPROVE_OPTION) {
      String filepath = fchooser.getSelectedFile().getAbsolutePath();
      return filepath;
    }
    return null;
  }

  private void setSplitCheckBox(Features features) {
    Component[] components = this.getComponents();
    for (Component component : components) {
      if (component instanceof JButton && component != splitCheckBox) {
        component.setEnabled(false);
      }
    }

    // Create a panel with GridBagLayout to hold the slider and buttons
    JPanel sliderPanel = new JPanel(new GridBagLayout());
    sliderPanel.setSize(600, 50);
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(5, 5, 5, 5); // Add padding

    JSlider splitSlider = new JSlider(JSlider.HORIZONTAL, 1, 100, 50);
    JPanel buttonPanel = new JPanel(new GridLayout());
    JButton okButton = new JButton("Apply");
    JButton cancelButton = new JButton("Cancel");
    buttonPanel.add(okButton);
    buttonPanel.add(cancelButton);

    // Configure the slider
    splitSlider.setMajorTickSpacing(10);
    splitSlider.setPaintTicks(true);
    splitSlider.setPaintLabels(true);

    // Add the label
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.gridwidth = 2; // Span across two columns
    gbc.fill = GridBagConstraints.HORIZONTAL;
    sliderPanel.add(new JLabel("Adjust Split Width:"), gbc);

    // Add the slider
    gbc.gridx = 0;
    gbc.gridy = 1;
    gbc.gridwidth = 2; // Span across two columns
    gbc.weightx = 1.0; // Allow horizontal expansion
    gbc.fill = GridBagConstraints.HORIZONTAL;
    sliderPanel.add(splitSlider, gbc);

    gbc.gridx = 0;
    gbc.gridy = 2;
    gbc.gridwidth = 2;
    gbc.weightx = 1.0;
    sliderPanel.add(buttonPanel, gbc);


    // Show the slider and buttons in a dialog
    JDialog dialog = new JDialog((Frame) null, "Split Adjustment", true);
    dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
    dialog.add(sliderPanel);

    // Set dialog width to match parent
    int dialogWidth = this.getParent().getWidth();
    //int dialogWidth
    dialog.setSize(400, dialog.getPreferredSize().height + 50);
    dialog.setLocationRelativeTo(this);

    // Listener to dynamically adjust the splitCheckBox image
    splitSlider.addChangeListener(evt -> {
      int splitValue = splitSlider.getValue();
      features.split(splitValue); // Call the split method with the slider's current value
    });

    // Listener for the OK button
    okButton.addActionListener(okEvent -> {
      features.split(100);
      for (Component component : components) {
        if (component instanceof JButton) {
          component.setEnabled(true);
        }
      }
      dialog.dispose();
      splitCheckBox.setSelected(false);
      splitCheckBox.setEnabled(false);
    });

    // Listener for the cancel button
    cancelButton.addActionListener(evt -> {
      features.split(0);
      for (Component component : components) {
        if (component instanceof JButton) {
          component.setEnabled(true);
        }
      }
      dialog.dispose();
      splitCheckBox.setSelected(false);
      splitCheckBox.setEnabled(false);
    });


    dialog.setVisible(true);
  }

  private String saveFile() {
    JFileChooser fchooser = new JFileChooser(".");
    int retvalue = fchooser.showSaveDialog(this);
    if (retvalue == JFileChooser.APPROVE_OPTION) {
      String filepath = fchooser.getSelectedFile().getAbsolutePath();
      return filepath;
    }
    return null;
  }
}

