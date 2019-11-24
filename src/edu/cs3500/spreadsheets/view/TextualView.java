package edu.cs3500.spreadsheets.view;

import java.io.IOException;

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

  private void appendToOut(String s) {
    try {
      out.append(s);
    } catch (IOException e) {
      throw new IllegalStateException("Can't append the new world state to the given appendable.");
    }
  }
}