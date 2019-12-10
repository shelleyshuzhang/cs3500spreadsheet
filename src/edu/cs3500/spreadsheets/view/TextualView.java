package edu.cs3500.spreadsheets.view;

import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import edu.cs3500.spreadsheets.controller.Features;
import edu.cs3500.spreadsheets.model.worksheet.WorksheetReadOnly;

/**
 * A textual view class for worksheet. It can read in a worksheet read only model and produce an new
 * file which contain a text representation for all raw contents in the model. It implements IView,
 * which is an interface for all worksheet view.
 */
public class TextualView implements IView {
  private final WorksheetReadOnly worksheetReadOnly;
  private final Appendable out;

  /**
   * Construct a TextualView with an worksheet model and an appendable.
   *
   * @param w worksheet read only model that need to be read
   * @param a appendable use to append the output
   * @throws IllegalArgumentException if any input is null
   */
  public TextualView(WorksheetReadOnly w, Appendable a) {
    if (w == null || a == null) {
      throw new IllegalArgumentException("The given model or appendable is null.");
    } else {
      this.worksheetReadOnly = w;
      this.out = a;
    }
  }

  @Override
  public void render() {
    this.appendToOut(worksheetReadOnly.toString());
  }

  @Override
  public void addColumn() {
    // do nothing because this is not the job for a textual view
  }

  @Override
  public void addRow() {
    // do nothing because this is not the job for a textual view
  }

  @Override
  public void increaseRowHeaderWidth() {
    // do nothing because this is not the job for a textual view
  }

  @Override
  public void editCell(int col, int row, String value) {
    // do nothing because this is not the job for a textual view
  }

  @Override
  public String getTextFieldInput() {
    return null;
    // do nothing because this is not the job for a textual view
  }

  @Override
  public void clearTextField() {
    // do nothing because this is not the job for a textual view
  }

  @Override
  public void resetFocus() {
    // do nothing because this is not the job for a textual view
  }

  @Override
  public void removeFocus() {
    // do nothing because this is not the job for a textual view
  }

  @Override
  public void getFocus() {
    // do nothing because this is not the job for a textual view
  }

  @Override
  public void addActionListener(ActionListener ac) {
    // do nothing because this is not the job for a textual view
  }

  @Override
  public void addMouseEventListener(MouseListener textField, MouseListener cells) {
    // do nothing because this is not the job for a textual view
  }

  @Override
  public void setTextFieldInput(String s) {
    // do nothing because this is not the job for a textual view
  }

  @Override
  public void storeTextFieldInput() {
    // do nothing because this is not the job for a textual view
  }

  @Override
  public void resetTextField() {
    // do nothing because this is not the job for a textual view
  }

  @Override
  public void addKeyboardListener(KeyListener k) {
    // do nothing because this is not the job for a textual view
  }

  @Override
  public int[] getSelectedColumns() {
    // do nothing because this is not the job for a textual view
    return new int[]{};
  }

  @Override
  public int[] getSelectedRows() {
    // do nothing because this is not the job for a textual view
    return new int[]{};
  }

  @Override
  public void addFeatures(Features features) {
    // do nothing because this is not the job for a textual view
  }

  @Override
  public void setSelectedCell(int col, int row) {
    // do nothing because this is not the job for a textual view
  }

  @Override
  public File setSaveFileChooser() {
    // do nothing because this is not the job for a textual view
    return null;
  }

  @Override
  public File setOpenFileChooser() {
    // do nothing because this is not the job for a textual view
    return null;
  }

  @Override
  public void setEditable(boolean editable) {
    // do nothing because this is not the job for a textual view
  }

  @Override
  public boolean getEditable() {
    return false;
    // do nothing because this is not the job for a textual view
  }

  @Override
  public int getCellWidth(int col, int row) {
    // should never be called because there won't be a cell in the text view
    return 0;
  }

  @Override
  public int getCellHeight(int col, int row) {
    // should never be called because there won't be a cell in the text view
    return 0;
  }

  @Override
  public void setCellWidth(int col, int width) {
    // should never be called because there won't be a cell in the text view
  }

  @Override
  public void setCellHeight(int row, int height) {
    // should never be called because there won't be a cell in the text view
  }

  @Override
  public int getSelectedCellRow() {
    // should never be called because there won't be a selected cell in the text view
    return 0;
  }

  @Override
  public int getSelectedCellCol() {
    // should never be called because there won't be a selected cell in the text view
    return 0;
  }

  private void appendToOut(String s) {
    try {
      out.append(s);
    } catch (IOException e) {
      throw new IllegalStateException("Can't append the new world state to the given appendable.");
    }
  }
}
