package edu.cs3500.spreadsheets.model;

import java.util.HashMap;
import java.util.Objects;

import edu.cs3500.spreadsheets.model.cell.Cell;
import edu.cs3500.spreadsheets.model.cell.CellGeneral;
import edu.cs3500.spreadsheets.model.content.Blank;
import edu.cs3500.spreadsheets.model.content.Contents;
import edu.cs3500.spreadsheets.model.content.value.ValueBoolean;
import edu.cs3500.spreadsheets.model.content.value.ValueDouble;
import edu.cs3500.spreadsheets.model.content.value.ValueString;
import edu.cs3500.spreadsheets.model.worksheet.BasicWorkSheet;
import edu.cs3500.spreadsheets.model.worksheet.Worksheet;
import edu.cs3500.spreadsheets.sexp.Parser;
import edu.cs3500.spreadsheets.sexp.Sexp;
import edu.cs3500.spreadsheets.sexp.SexpVisitorFormula;

/**
 * A builder pattern for producing BasicWorksheets.
 */
public class BasicWorkSheetBuilder implements WorksheetReader.WorksheetBuilder<Worksheet> {
  private HashMap<Coord, CellGeneral> allRawCell;

  /**
   * Construct a BasicWorkSheetBuilder which initialize the map of all cells with raw contents as an
   * empty map.
   */
  public BasicWorkSheetBuilder() {
    this.allRawCell = new HashMap<Coord, CellGeneral>();
  }

  @Override
  public WorksheetReader.WorksheetBuilder<Worksheet> createCell(int col, int row, String contents) {
    Contents c;
    if (contents == null) {
      c = new Blank();
    } else if (contents.length() >= 1 && contents.charAt(0) == '=') {
      Sexp s = Parser.parse(contents.substring(1));
      try {
        c = s.accept(new SexpVisitorFormula(col, row, allRawCell));
      } catch (IllegalArgumentException e) {
        // if the cell contains any self-reference, the model will create the cell with an error
        // message indicating that this is not allowed
        if (e.getMessage().equals("The reference points to the given cell itself")) {
          c = new ValueString("The reference points to the given cell itself");
        } else {
          throw e;
        }
      }
    } else {
      try {
        double d = Double.parseDouble(contents);
        c = new ValueDouble(d);
      } catch (NumberFormatException e) {
        if (contents.equalsIgnoreCase("true")) {
          c = new ValueBoolean(true);
        } else if (contents.equalsIgnoreCase("false")) {
          c = new ValueBoolean(false);
        } else {
          c = new ValueString(contents);
        }
      }
    }
    Coord coord = new Coord(col, row);
    if (allRawCell.containsKey(coord)) {
      CellGeneral toChange = allRawCell.get(coord);
      toChange.setContents(c);
    } else {
      CellGeneral cell = new Cell(coord, c);
      this.allRawCell.put(coord, cell);
    }
    return this;
  }

  @Override
  public Worksheet createWorksheet() {
    return new BasicWorkSheet(this.allRawCell);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BasicWorkSheetBuilder bwsb = (BasicWorkSheetBuilder) o;
    if (allRawCell == null) {
      return bwsb.allRawCell == null;
    }
    return allRawCell.equals(bwsb.allRawCell);
  }

  @Override
  public int hashCode() {
    return Objects.hash(allRawCell);
  }
}