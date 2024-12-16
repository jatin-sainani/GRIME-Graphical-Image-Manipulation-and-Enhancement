package view;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JSplitPane;
import javax.swing.JScrollPane;

/**
 * View class that implements IView and acts as the main view class.
 */
public class View extends JFrame implements IView {

  private ToolPanel toolPanel;
  private ImageDisplayPanel imageDisplayPanel;
  private ImageDisplayPanel histogramDisplayPanel;

  public View() {
    setupUIComponents();
  }

  private void setupUIComponents() {
    setTitle("Image Manipulation Application");
    setSize(1200, 800);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new BorderLayout());

    // Initialize components
    toolPanel = new ToolPanel();
    imageDisplayPanel = new ImageDisplayPanel();
    histogramDisplayPanel = new ImageDisplayPanel();

    // Wrap ToolPanel in a JScrollPane
    JScrollPane toolPanelScrollPane = new JScrollPane(toolPanel);
    toolPanelScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    toolPanelScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    toolPanelScrollPane.setPreferredSize(new Dimension(getWidth(), 100));

    // SplitPane configuration
    JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
    splitPane.setLeftComponent(imageDisplayPanel); // Left for the main image
    splitPane.setRightComponent(histogramDisplayPanel); // Right for the histogram
    splitPane.setResizeWeight(0.98); // Allocate more space to ImageDisplayPanel

    // Add SplitPane and StatusPanel
    add(toolPanelScrollPane, BorderLayout.NORTH);
    add(splitPane, BorderLayout.CENTER);

    setVisible(true);
  }

  public ImagePanel getImageDisplayPanel() {
    return imageDisplayPanel;
  }

  public ImageDisplayPanel getHistogramDisplayPanel() {
    return histogramDisplayPanel;
  }


  public ITool getToolPanel() {
    return toolPanel;
  }

}
