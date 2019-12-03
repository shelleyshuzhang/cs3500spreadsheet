package edu.cs3500.spreadsheets.provider.controller;

/**
 * a interface shows all type of Controller's method.
 */
public interface IController {

  /**
   * add action command to the Button.
   */
  void configureButtonListener();

  /**
   * add mouse command to the editView.
   */
  void configureMouseListener();

}
