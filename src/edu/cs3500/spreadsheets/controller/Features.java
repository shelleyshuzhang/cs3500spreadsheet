package edu.cs3500.spreadsheets.controller;

/**
 * features interface represents a features listener, which can response for specific situation.
 */
public interface Features {
  /**
   * save the change and execute it.
   */
  void saveAndChange();

  /**
   * refuse the revise and reset the initial state.
   */
  void RefuseAndReset();

  /**
   * get focus on specific place and store data in this place.
   */
  void getFocusAction();

  /**
   * show the wanted contents in a place, and focus on it.
   */
  void focusAndShow();

  /**
   * show the wanted contents in a place.
   */
  void showContentAbove();

  /**
   * delete all data in a specific place.
   */
  void deleteAll();

  /**
   * to make a graphic representation visible.
   */
  void go();

  /**
   * save the current spreadsheet to a file on disk
   */
  void saveFile();

  /**
   * open a file on disk and show the spreadsheet in it
   */
  void openFile();

}
