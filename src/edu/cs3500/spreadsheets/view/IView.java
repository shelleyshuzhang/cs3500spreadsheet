package edu.cs3500.spreadsheets.view;

import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.io.IOException;


/**
 * The view for worksheet. It show a worksheet to user as text or graphics or some other form.
 */
public interface IView {
  /**
   * Renders a model in some manner (e.g. as text, or as graphics, etc.).
   *
   * @throws IOException if the rendering fails for some reason
   */
  void render();

  /**
   * Add a new column to the worksheet if needed.
   */
  void addColumn();

  /**
   * Add a new row to the worksheet if needed.
   */
  void addRow();

  /**
   * Increase the width of the row header to 5/4 of its original size.
   */
  void increaseRowHeaderWidth();

  /**
   * edit a cell's display with the assign row and col and string.
   * @param col the col of the cell
   * @param row the row of the cell
   * @param value the string value to be assigned to the cell
   */
  void editCell(int col, int row, String value);

  /**
   * get the string inside the text field.
   * @return the text field input string
   */
  String getTextFieldInput();

  /**
   * clear the text field to empty.
   */
  void clearTextField();

  /**
   * reset the focus of the view to appropriate place for whole view.
   */
  void resetFocus();

  /**
   * get the row of the cell which has been selected.
   * @return the row of the selected cell
   */
  int getSelectedCellRow();

  /**
   * get the col of the cell which has been selected.
   * @return the col of the selected cell
   */
  int getSelectedCellCol();

  /**
   * remove the focus from where it initially be.
   */
  void removeFocus();

  /**
   * get focus for a specific place.
   */
  void getFocus();

  void addActionListener(ActionListener ac);

  void addMouseEventListener(MouseListener textField, MouseListener cells);

  /**
   * set a input to a specific text field.
   * @param s the string to set
   */
  void setTextFieldInput(String s);

  /**
   * store the input of a specific text field.
   */
  void storeTextFieldInput();

  /**
   * reset the contents of a specific text field.
   */
  void resetTextField();

  void addKeyboardListener(KeyListener k);


  void getSelectedColumns();

  void getSelectedRows();
}
