package view;

/**
 * Interface for the view that supports getting all the panels present in the frame.
 */
public interface IView {

  /**
   * Returns Image Display Panel.
   *
   * @return Image Display Panel.
   */
  ImagePanel getImageDisplayPanel();

  /**
   * Returns Image Display Panel for the histogram.
   *
   * @return Image Display Panel for the historgram.
   */
  ImagePanel getHistogramDisplayPanel();


  /**
   * Returns Tool Panel.
   *
   * @return Tool Panel.
   */
  ITool getToolPanel();
}
