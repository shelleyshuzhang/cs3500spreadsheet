package edu.cs3500.spreadsheets.model.worksheet;

import java.util.HashMap;
import java.util.Set;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.cell.CellGeneral;
import edu.cs3500.spreadsheets.model.content.Contents;
import edu.cs3500.spreadsheets.model.content.value.Value;

/**
 * Interface for worksheet which can only been read and get but cannot been revise.
 */
public interface WorksheetReadOnly {
  /**
   * Get the cell result value after evaluation in given coordinate.
   *
   * @param col the column of the cell which want to get
   * @param row the row of the cell which want to get
   * @return the value after evaluation of the wanted cell
   * @throws IllegalArgumentException if the given position do not point to a cell which we can get
   *                                  it's value
   * @throws IllegalStateException    if the worksheet has not been evaluate yet
   */
  Value getOneCellResult(int col, int row);

  /**
   * Get the size of the worksheet (how many cell has been created in a worksheet).
   *
   * @return the size of the worksheet
   */
  int getWorkSheetSize();

  /**
   * Get the cell's raw content.
   *
   * @param col the column of the cell which want to get
   * @param row the row of the cell which want to get
   * @return the raw contents of the wanted cell
   * @throws IllegalArgumentException if the given position do not point to a cell which we can get
   *                                  it's raw contents
   */
  Contents getOneCellRawContents(int col, int row);

  /**
   * Get all cells (which exist in the worksheet)'s coordinate.
   *
   * @return all cells' coordinate as a set.
   */
  Set<String> getAllCellCoordinates();

  HashMap<Coord, CellGeneral> getAllRawCell();
}
