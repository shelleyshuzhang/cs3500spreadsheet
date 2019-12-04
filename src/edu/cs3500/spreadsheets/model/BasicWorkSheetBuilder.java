package edu.cs3500.spreadsheets.model;

import java.util.HashMap;
import java.util.Objects;

import edu.cs3500.spreadsheets.model.cell.Cell;
import edu.cs3500.spreadsheets.model.cell.CellGeneral;
import edu.cs3500.spreadsheets.model.content.Blank;
import edu.cs3500.spreadsheets.model.content.Contents;
import edu.cs3500.spreadsheets.model.content.formula.FormulaValue;
import edu.cs3500.spreadsheets.model.content.value.Value;
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
    Contents c = createContent(col, row, contents, this.allRawCell);
    Coord coord = new Coord(col, row);
    if (allRawCell.containsKey(coord)) {
      CellGeneral toChange = allRawCell.get(coord);
      toChange.setContents(c, new HashMap<Coord, Value>());
    } else {
      CellGeneral cell = new Cell(coord, c);
      this.allRawCell.put(coord, cell);
    }
    return this;
  }

  /**
   * Create a content of a cell with coordinate of the cell which the contents belong, contents
   * described in string, and a allRawCell map as a references to help detected self-references.
   *
   * @param col        the given column for this contents
   * @param row        the given row for this contents
   * @param contents   the given string representation for this content
   * @param allRawCell the given reference cell map
   * @return the final content create by given information
   * @throws IllegalArgumentException there are IllegalArgumentException other than self-reference
   *                                  exception been throw in the process of creating contents
   */
  public static Contents createContent(int col, int row, String contents,
                                       HashMap<Coord, CellGeneral> allRawCell) {
    Contents c;
    try {
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
            Coord coord = new Coord(col, row);
            c = new ValueString("The cell content " + contents.substring(1) + " points to this " +
                    "cell " + coord.toString() + " itself");
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
    } catch (IllegalArgumentException e) {
      if (e.getMessage().equals("Expected cell ref")) {
        c = new FormulaValue(new ValueString(contents));
      } else {
        String error = "Cell has content: " + contents + " which is error: " + e.getMessage();
        c = new FormulaValue(new ValueString(error));
      }
    }
    return c;
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
