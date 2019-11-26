package edu.cs3500.spreadsheets.sexp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.cs3500.spreadsheets.model.BasicSupportFunctions;
import edu.cs3500.spreadsheets.model.content.Blank;
import edu.cs3500.spreadsheets.model.cell.Cell;
import edu.cs3500.spreadsheets.model.cell.CellGeneral;
import edu.cs3500.spreadsheets.model.content.Contents;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.content.formula.Formula;
import edu.cs3500.spreadsheets.model.content.formula.FormulaReference;
import edu.cs3500.spreadsheets.model.content.formula.FormulaValue;
import edu.cs3500.spreadsheets.model.content.formula.FunctionLessThan;
import edu.cs3500.spreadsheets.model.content.formula.FunctionProduct;
import edu.cs3500.spreadsheets.model.content.formula.FunctionStringAppend;
import edu.cs3500.spreadsheets.model.content.formula.FunctionSum;
import edu.cs3500.spreadsheets.model.content.value.ValueBoolean;
import edu.cs3500.spreadsheets.model.content.value.ValueDouble;
import edu.cs3500.spreadsheets.model.content.value.ValueString;

import static edu.cs3500.spreadsheets.model.BasicSupportFunctions.getAvailableFunctions;

/**
 * Represents a s-expression visitor which visit each type of s-expression and the method inside
 * return Formula type.
 */
public class SexpVisitorFormula implements SexpVisitor<Formula> {
  private final HashMap<Coord, CellGeneral> workSheet;
  private BasicSupportFunctions function;
  private List<Formula> funcArguments;
  private final int col;
  private final int row;

  /**
   * Construct a SexpVisitorFormula with the take in a coordinate where this s-expression coming
   * from and allCell in a worksheet.
   *
   * @param col     column of the coordinate
   * @param row     row of the coordinate
   * @param allCell all cells in a worksheet
   */
  public SexpVisitorFormula(int col, int row, HashMap<Coord, CellGeneral> allCell) {
    this.workSheet = allCell;
    this.function = null;
    this.funcArguments = new ArrayList<Formula>();
    this.col = col;
    this.row = row;
  }

  @Override
  public Formula visitBoolean(boolean b) {
    return new FormulaValue(new ValueBoolean(b));
  }

  @Override
  public Formula visitNumber(double d) {
    return new FormulaValue(new ValueDouble(d));
  }

  @Override
  public Formula visitSList(List<Sexp> losp) throws IllegalArgumentException {
    int listSize = losp.size();
    if (listSize == 0) {
      throw new IllegalArgumentException("The given cell has a wrong formula inputs");
    } else if (listSize == 1) {
      Sexp firstElement = losp.get(0);
      return firstElement.accept(new SexpVisitorFormula(col, row, workSheet));
    } else {
      Sexp firstElement = losp.get(0);
      if (getAvailableFunctions().containsKey(firstElement.toString())) {
        function = getAvailableFunctions().get(firstElement.toString());
        for (int i = 1; i < losp.size(); i++) {
          Sexp current = losp.get(i);
          funcArguments.add(current.accept(new SexpVisitorFormula(col, row, workSheet)));
        }
        if (function == BasicSupportFunctions.SUM) {
          return new FunctionSum(funcArguments);
        } else if (function == BasicSupportFunctions.PRODUCT) {
          return new FunctionProduct(funcArguments);
        } else if (function == BasicSupportFunctions.LESSTHAN) {
          return new FunctionLessThan(funcArguments);
        } else if (function == BasicSupportFunctions.SAPPEND) {
          return new FunctionStringAppend(funcArguments);
        }
      }
      throw new IllegalArgumentException("The given cell content value is in the wrong format");
    }
  }

  @Override
  public Formula visitSymbol(String s) {
    try {
      List<Coord> referCoords = getCoords(s);
      List<CellGeneral> references = new ArrayList<>();
      for (Coord c : referCoords) {
        if (this.workSheet.containsKey(c)) {
          references.add(this.workSheet.get(c));
        } else {
          Contents blank = new Blank();
          CellGeneral toAdd = new Cell(c, blank);
          this.workSheet.put(c, toAdd);
          references.add(toAdd);
        }
      }
      return new FormulaReference(references, new Coord(this.col, this.row));
    } catch (IllegalArgumentException e) {
      throw e;
    }
  }

  @Override
  public Formula visitString(String s) {
    return new FormulaValue(new ValueString(s));
  }

  private List<Coord> getCoords(String reference) {
    if (!reference.contains(":")) {
      int[] coords = getSingleRefer(reference);
      return this.createCoords(coords[0], coords[1], coords[0], coords[1]);
    } else {
      String[] los = reference.split(":");
      int[] coordStart = getSingleRefer(los[0]);
      int[] coordEnd = getSingleRefer(los[1]);
      return this.createCoords(coordStart[0], coordStart[1], coordEnd[0], coordEnd[1]);
    }
  }

  private List<Coord> createCoords(int startCol, int startRow, int endCol, int endRow) {
    if (endCol < startCol || endRow < startRow) {
      throw new IllegalArgumentException("The end cell index can't be smaller than the start one");
    } else {
      List<Coord> locd = new ArrayList<Coord>();
      for (int i = startCol; i <= endCol; i++) {
        for (int m = startRow; m <= endRow; m++) {
          locd.add(new Coord(i, m));
        }
      }
      return locd;
    }
  }

  /**
   * A static method to get a int array represents a positions.
   * @param single given String that may be transfer into a position representations.
   * @return the position representation.
   * @throws IllegalArgumentException if the given String cannot been transfer to a position
   */
  public static int[] getSingleRefer(String single) {
    final Pattern cellRef = Pattern.compile("([A-Za-z]+)([1-9][0-9]*)");
    Matcher m = cellRef.matcher(single);
    if (m.matches()) {
      int col = Coord.colNameToIndex(m.group(1));
      int row = Integer.parseInt(m.group(2));
      int[] a = new int[]{col, row};
      return a;
    } else {
      throw new IllegalArgumentException("Expected cell ref");
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
    SexpVisitorFormula svf = (SexpVisitorFormula) o;
    if (workSheet == null) {
      return svf.workSheet == null;
    }
    if (function == null) {
      return svf.function == null;
    }
    if (funcArguments == null) {
      return svf.funcArguments == null;
    }
    return workSheet.equals(svf.workSheet) && function.equals(svf.function)
            && funcArguments.equals(svf.funcArguments) && col == svf.col && row == svf.row;
  }

  @Override
  public int hashCode() {
    return Objects.hash(workSheet, function, funcArguments, col, row);
  }

}
