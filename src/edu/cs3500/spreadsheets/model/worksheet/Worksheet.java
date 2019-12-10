package edu.cs3500.spreadsheets.model.worksheet;

import java.util.List;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.content.Contents;

/**
 * The model for a work sheet. The work sheet is a grid which all raw contents inside it can be
 * evaluate (or throw an error if some contents are not appropriate).
 */
public interface Worksheet extends WorksheetReadOnly {
  /**
   * Evaluate all cell inside a worksheet. After evaluation, the raw contents inside the worksheet
   * will not be throw away.
   * New changes: The model will not throw an error if encounter cells that can not be evaluated
   * properly, instead it will set the value of that cell as a value string, which contains the
   * error message that should be shown to the user.
   */
  void evaluateAll();

  /**
   * Edit A Cell's Content.
   *
   * @param col      the col of the cell
   * @param row      the row of the cell
   * @param contents the contents that should change
   */
  List<Coord> editCellContent(int col, int row, Contents contents);

  void setCellRowHeight(int col, int row, int height);

  void setCellColWidth(int col, int row, int width);
}
