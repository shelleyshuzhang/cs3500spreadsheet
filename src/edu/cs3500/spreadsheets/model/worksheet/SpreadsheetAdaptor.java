package edu.cs3500.spreadsheets.model.worksheet;

import java.util.Map;
import java.util.Objects;

import edu.cs3500.spreadsheets.controller.BasicController;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.content.Blank;
import edu.cs3500.spreadsheets.model.content.Contents;
import edu.cs3500.spreadsheets.provider.model.IModelSpreadsheet;

public class SpreadsheetAdaptor implements IModelSpreadsheet {
  private Worksheet spreadsheet;

  public SpreadsheetAdaptor(Worksheet w) {
    this.spreadsheet = w;
  }

  @Override
  public Map getWorksheet() {
    return spreadsheet.getAllRawCell();
  }

  @Override
  public void addAndUpdate(int col, int row, String in) {
    Contents contents = BasicController.stringToContent(in, col, row, spreadsheet);
    spreadsheet.editCellContent(col, row, contents);
  }

  @Override
  public String getContent(int col, int row, boolean show) {
    String value;
    try {
      value = spreadsheet.getOneCellResult(col, row).print();
    } catch (IllegalArgumentException e) {
      if (e.getMessage().equals("cannot find the cell you want")) {
        value = "";
      } else {
        value = e.getMessage();
      }
    }
    return value;
  }

  @Override
  public boolean sameModel(IModelSpreadsheet w) {
    return this.equals(w);
  }

  @Override
  public void removeCell(Coord c) {
    this.spreadsheet.editCellContent(c.col, c.row, new Blank());
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SpreadsheetAdaptor sa = (SpreadsheetAdaptor) o;
    if (spreadsheet == null) {
      return sa.spreadsheet == null;
    }
    return spreadsheet.equals(sa.spreadsheet);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.spreadsheet.toString());
  }

  @Override
  public String toString() {
    return this.spreadsheet.toString();
  }
}
