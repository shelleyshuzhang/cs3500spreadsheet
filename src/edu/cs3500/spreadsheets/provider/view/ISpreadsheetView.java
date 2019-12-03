package edu.cs3500.spreadsheets.provider.view;

import java.io.IOException;

import edu.cs3500.spreadsheets.model.Coord;

/**
 * The interface of view to render the model.
 */
public interface ISpreadsheetView {

  /**
   * render the model.
   *
   * @throws IOException throw exception if fail to append to Appendable
   */
  void render() throws IOException;

  /**
   * get the current text field button's text.
   *
   * @return a string in the current text field button
   */
  String getTextString();

  /**
   * reset listener focus when we finish one listener.
   */
  void resetFocus();

  /**
   * clean the text in the current text field button.
   */
  void cleanTextString();

  /**
   * updateTextField's text in the given position.
   *
   * @param c the selected coord
   */
  void updateTextField(Coord c);

  /**
   * get the selected coord for mouse.
   *
   * @return the selected coord for mouse
   */
  Coord getCoord();

  /**
   * add the mouse Listener to the view.
   */
  void addMouse();

  /**
   * repaint the gridPanel.
   */
  void reDraw();

  /**
   * add ActionListener to the button.
   */
  void addButton();
}
