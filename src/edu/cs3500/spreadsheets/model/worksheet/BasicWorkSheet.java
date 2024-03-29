package edu.cs3500.spreadsheets.model.worksheet;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.cell.Cell;
import edu.cs3500.spreadsheets.model.cell.CellGeneral;
import edu.cs3500.spreadsheets.model.content.Blank;
import edu.cs3500.spreadsheets.model.content.Contents;
import edu.cs3500.spreadsheets.model.content.formula.Formula;
import edu.cs3500.spreadsheets.model.content.value.Value;
import edu.cs3500.spreadsheets.model.content.value.ValueString;

/**
 * Represents a basic worksheet which now support three kinds of value: boolean, string, double.
 * Four Functions are available now: SUM, PRODUCT, SAPPEND, and <.
 */
// class invariant: if evaluated field is false, allEvaCell should be empty.
public class BasicWorkSheet implements Worksheet {
  private HashMap<Coord, CellGeneral> allRawCell;
  private HashMap<Coord, Value> allEvaCell;
  private boolean evaluated;

  /**
   * Constructs an BasicWorkSheet, which input a hashmap contain all the raw contents of cells and
   * the coordinate they are in.
   *
   * @param allRawCell hashmap of all raw contents and coordinates of cells
   */
  public BasicWorkSheet(HashMap<Coord, CellGeneral> allRawCell) {
    this.allRawCell = allRawCell;
    this.allEvaCell = new HashMap<Coord, Value>();
    this.evaluated = false;
  }

  @Override
  public void evaluateAll() {
    // the key, coord, of two map point to the same object
    for (Map.Entry<Coord, CellGeneral> entry : this.allRawCell.entrySet()) {
      HashMap<Formula, Value> formulaValueMap = new HashMap<>();
      try {
        Value result = entry.getValue().evaluate(formulaValueMap);
        this.allEvaCell.put(entry.getKey(), result);
      } catch (IllegalArgumentException e) {
        this.allEvaCell.put(entry.getKey(), new ValueString(e.getMessage()));
      }
    }
    this.evaluated = true;
  }

  @Override
  public Value getOneCellResult(int col, int row) {
    if (col < 1 || row < 1) {
      throw new IllegalArgumentException("this is not a valid coordinate");
    }
    if (evaluated) {
      Coord coord = new Coord(col, row);
      if (allEvaCell.containsKey(coord)) {
        try {
          return allEvaCell.get(coord).getInstance();
        } catch (NullPointerException e) {
          return new ValueString("");
        }
      } else {
        throw new IllegalArgumentException("cannot find the cell you want");
      }
    } else {
      throw new IllegalStateException("the worksheet has not been evaluated now");
    }
  }

  @Override
  public int getWorkSheetSize() {
    return this.allRawCell.size();
  }

  @Override
  public Contents getOneCellRawContents(int col, int row) {
    if (col < 1 || row < 1) {
      throw new IllegalArgumentException("this is not a valid coordinate");
    }
    Coord coord = new Coord(col, row);
    if (allRawCell.containsKey(coord)) {
      return allRawCell.get(coord).getContents();
    } else {
      throw new IllegalArgumentException("no such cell");
    }
  }

  @Override
  public Set<String> getAllCellCoordinates() {
    Set<String> coords = new HashSet<>();
    for (Coord coord : allEvaCell.keySet()) {
      coords.add(coord.toString());
    }
    return coords;
  }

  @Override
  public HashMap<Coord, CellGeneral> getAllRawCell() {
    return allRawCell;
  }

  @Override
  public int getCellRowHeight(int col, int row) {
    Coord coord = new Coord(col, row);
    if (allRawCell.containsKey(coord)) {
      return allRawCell.get(coord).getRowHeight();
    } else {
      throw new IllegalArgumentException("There is no cell yet to get this cell height");
    }
  }

  @Override
  public int getCellColWidth(int col, int row) {
    Coord coord = new Coord(col, row);
    if (allRawCell.containsKey(coord)) {
      return allRawCell.get(coord).getColWidth();
    } else {
      throw new IllegalArgumentException("There is no cell yet to get this cell width");
    }
  }

  @Override
  public List<Coord> editCellContent(int col, int row, Contents contents) {
    Coord c = new Coord(col, row);
    if (allRawCell.get(c) == null) {
      allRawCell.put(c, new Cell(c, new Blank()));
    }
    return allRawCell.get(c).setContents(contents, allEvaCell);
  }

  @Override
  public void setCellRowHeight(int col, int row, int height) {
    Coord coord = new Coord(col, row);
    if (allRawCell.containsKey(coord)) {
      allRawCell.get(coord).setRowHeight(height);
    } else {
      throw new IllegalArgumentException("There is no cell yet to set this cell height");
    }
  }

  @Override
  public void setCellColWidth(int col, int row, int width) {
    Coord coord = new Coord(col, row);
    if (allRawCell.containsKey(coord)) {
      allRawCell.get(coord).setColumnWidth(width);
    } else {
      throw new IllegalArgumentException("There is no cell yet to set this cell height");
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BasicWorkSheet bws = (BasicWorkSheet) o;
    if (allRawCell == null) {
      return bws.allRawCell == null;
    }
    if (allEvaCell == null) {
      return bws.allEvaCell == null;
    }
    return allRawCell.toString().equals(bws.allRawCell.toString())
            && allEvaCell.toString().equals(bws.allEvaCell.toString())
            && evaluated == bws.evaluated;
  }

  @Override
  public int hashCode() {
    return Objects.hash(allRawCell.toString(), allEvaCell.toString(), evaluated);
  }

  @Override
  public String toString() {
    StringBuilder s = new StringBuilder();
    for (CellGeneral c : this.allRawCell.values()) {
      s.append("\n");
      s.append(c.toString());
    }
    if (s.length() == 0) {
      return s.toString();
    } else {
      return s.substring(1);
    }
  }
}
