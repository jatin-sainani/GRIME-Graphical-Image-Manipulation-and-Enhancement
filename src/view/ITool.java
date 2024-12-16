package view;

import controller.Features;

/**
 * This interface represents the tools available on the GUI.
 */
public interface ITool {

  /**
   * Adding listeners to all the buttons.
   *
   * @param features feature interface that contains all the features.
   */
  void addFeatures(Features features);
}
