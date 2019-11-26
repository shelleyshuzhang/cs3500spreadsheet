package edu.cs3500.spreadsheets.model.cell;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import edu.cs3500.spreadsheets.model.BasicWorkSheetBuilder;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.content.Contents;
import edu.cs3500.spreadsheets.model.content.formula.Formula;
import edu.cs3500.spreadsheets.model.content.formula.FormulaFunction;
import edu.cs3500.spreadsheets.model.content.formula.FormulaReference;
import edu.cs3500.spreadsheets.model.content.value.Value;
import edu.cs3500.spreadsheets.model.content.value.ValueString;

// Why I choose this representation:
// Cell should be an implementation of general cell, and because we made contents an interface
// (which can be different kinds of contents), we can make cell as a class.

/**
 * Represents a cell which is a kind of CellGeneral, has it's coordinate and it's contents.
 */
public class Cell implements CellGeneral {
  private final Coord coordinate;
  private Contents contents;
  private Value evaluatedValue = null;
  private List<CellObserver> observers = new ArrayList<>();

  /**
   * Construct a Cell with given coordinate and contents.
   *
   * @param coordinate coordinate represents where is the cell
   * @param c          contents represents what is in the cell
   */
  public Cell(Coord coordinate, Contents c) {
    this.coordinate = coordinate;
    this.contents = c;
  }

  @Override
  public Value evaluate(HashMap<Formula, Value> formulaValueMap) {
    try {
      evaluatedValue = contents.evaluate(formulaValueMap);
      return evaluatedValue;
    } catch (IllegalArgumentException e) {
      String error = "Error in cell " + coordinate.toString() + ": " + e.getMessage();
      this.evaluatedValue = new ValueString(error);
      throw new IllegalArgumentException(error);
    }
  }

  @Override
  public Coord getCoordinate() {
    return this.coordinate;
  }

  @Override
  public Contents getContents() {
    return this.contents.getInstance();
  }

  @Override
  public List<Coord> setContents(Contents contents, HashMap<Coord, Value> allEvaCell) {
    deleteObserverContent(this.contents, this);
    this.contents = contents;
    HashMap<Formula, Value> formulaValueHashMap = new HashMap<>();
    List<Coord> toReturn = this.executeUpdate(allEvaCell, formulaValueHashMap);
    BasicWorkSheetBuilder.registerObserverContent(contents, this);
    return toReturn;
  }

  @Override
  public boolean containsReference() {
    return this.contents.isFormulaReference();
  }

  @Override
  public Value getCellValue() {
    if (evaluatedValue != null) {
      return this.evaluatedValue.getInstance();
    } else {
      return null;
    }
  }

  @Override
  public void addObserver(CellObserver o) {
    this.observers.add(o);
  }

  @Override
  public boolean containObserver(CellObserver o) {
    return this.observers.contains(o);
  }

  @Override
  public void clearObserver() {
    this.observers.clear();
  }

  @Override
  public void deleteObserver(CellObserver observer) {
    if (observers.contains(observer)) {
      observers.remove(observer);
    } else {
      throw new IllegalArgumentException("This cell does not contain the given observer that we " +
              "want to delete.");
    }
  }

  @Override
  public List<Coord> executeUpdate(HashMap<Coord, Value> allEvaCell,
                                   HashMap<Formula, Value> formulaValueHashMap) {
    List<Coord> acc = new ArrayList<>();
    acc.add(this.coordinate);
    try {
      Value newValue = this.evaluate(formulaValueHashMap);
      System.out.println(newValue);
      if (allEvaCell.containsKey(this.coordinate)) {
        allEvaCell.replace(this.coordinate, newValue);
      } else {
        allEvaCell.put(this.coordinate, newValue);
      }
    } catch (IllegalArgumentException e) {
      if (allEvaCell.containsKey(this.coordinate)) {
        allEvaCell.replace(this.coordinate, new ValueString(e.getMessage()));
      } else {
        allEvaCell.put(this.coordinate, new ValueString(e.getMessage()));
      }
    }
    for (CellObserver o : this.observers) {
      if (!acc.contains(o.getCoordinate())) {
        acc.addAll(o.update(allEvaCell, formulaValueHashMap));
      }
    }
    return acc;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Cell c = (Cell) o;
    if (coordinate == null) {
      return c.coordinate == null;
    }
    if (contents == null) {
      return c.contents == null;
    }
    if (evaluatedValue == null) {
      return c.evaluatedValue == null;
    }
    return coordinate.equals(c.coordinate)
            && contents.equals(c.contents)
            && evaluatedValue.equals(c.evaluatedValue);
  }

  @Override
  public int hashCode() {
    return Objects.hash(coordinate, evaluatedValue);
  }

  @Override
  public String toString() {
    StringBuilder s = new StringBuilder();
    s.append(this.coordinate.toString());
    s.append(" ");
    if (contents.isFormula()) {
      s.append("=");
    }
    s.append(contents.toString());
    return s.toString();
  }

  private static void deleteObserverContent(Contents c, CellGeneral cell) {
    if (c.isFormulaReference()) {
      FormulaReference reference = (FormulaReference) c;
      deleteObserverReference(reference, cell);
    } else if (c.isFormulaFunction()) {
      FormulaFunction func = (FormulaFunction) c;
      deleteObserverFunction(func, cell);
    }
  }

  private static void deleteObserverFormula(Formula f, CellGeneral cell) {
    if (f.isFormulaFunction()) {
      FormulaFunction func = (FormulaFunction) f;
      deleteObserverFunction(func, cell);
    } else if (f.isFormulaReference()) {
      FormulaReference reference = (FormulaReference) f;
      deleteObserverReference(reference, cell);
    }
  }

  private static void deleteObserverReference(FormulaReference reference, CellGeneral cell) {
    List<CellGeneral> references = reference.getLoc();
    CellObserver toChangeObserver = new CellObserver(cell);
    for (CellGeneral cg : references) {
      cg.deleteObserver(toChangeObserver);
    }
  }

  private static void deleteObserverFunction(FormulaFunction func, CellGeneral cell) {
    List<Formula> lof = func.getArguments();
    for (Formula formula : lof) {
      deleteObserverFormula(formula, cell);
    }
  }

}
