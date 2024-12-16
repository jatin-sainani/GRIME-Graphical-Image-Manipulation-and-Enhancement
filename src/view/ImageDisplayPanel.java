package view;


import java.awt.Dimension;
import java.awt.BorderLayout;

import java.awt.image.BufferedImage;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.ImageIcon;


//import javax.swing.*;

/**
 * Image display Panel that shows the Image and the histogram.
 */
public class ImageDisplayPanel extends JPanel implements ImagePanel {
  private JLabel imageLabel;

  /**
   * Constructor method.
   */
  public ImageDisplayPanel() {
    imageLabel = new JLabel();
    imageLabel.setHorizontalAlignment(JLabel.CENTER);
    JScrollPane imageScrollPane = new JScrollPane(imageLabel);
    imageScrollPane.setPreferredSize(new Dimension(300, 300));
    setLayout(new BorderLayout()); // Ensure the scroll pane takes up the full space
    add(imageScrollPane, BorderLayout.CENTER);
  }

  /**
   * Setter method to update the image on the panel.
   *
   * @param image Buffered image.
   */
  public void setImage(BufferedImage image) {
    if (image != null) {
      ImageIcon imageIcon = new ImageIcon(image);
      imageLabel.setIcon(imageIcon);
      revalidate(); // Revalidate the JScrollPane
      repaint(); // Trigger repainting
    } else {
      imageLabel.setIcon(null); // Clear the image if null
    }
  }

}
