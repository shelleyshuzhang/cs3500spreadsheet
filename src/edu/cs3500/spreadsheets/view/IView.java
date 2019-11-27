package edu.cs3500.spreadsheets.view;

import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.io.IOException;

import edu.cs3500.spreadsheets.controller.KeybroadListener;


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

  void editCell(int col, int row, String value);

  String getTextFieldInput();

  void clearTextField();

  void resetFocus();

  int getSelectedCellRow();

  int getSelectedCellCol();

  void removeFocus();

  void getFocus();

  void addActionListener(ActionListener ac);

  void addMouseEventListener(MouseListener textField, MouseListener cells);

  void setTextFieldInput(String s);

  void storeTextFieldInput();

  void resetTextField();

  void addKeyboardListener(KeyListener k);

  int[] getSelectedColumns();

  int[] getSelectedRows();
}
