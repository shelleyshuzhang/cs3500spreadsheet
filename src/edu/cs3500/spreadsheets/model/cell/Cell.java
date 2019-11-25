package edu.cs3500.spreadsheets.model.cell;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.content.Contents;
import edu.cs3500.spreadsheets.model.content.formula.Formula;
import edu.cs3500.spreadsheets.model.content.value.Value;

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
  private List<CellObserver> observers = null;

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
      throw new IllegalArgumentException("Error in cell " + coordinate.toString() + ": "
              + e.getMessage());
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
    this.contents = contents;
    HashMap<Formula, Value> formulaValueHashMap = new HashMap<>();
    return this.executeUpdate(allEvaCell, formulaValueHashMap);
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
  public List<Coord> executeUpdate(HashMap<Coord, Value> allEvaCell,
                                   HashMap<Formula, Value> formulaValueHashMap) {
    List<Coord> acc = new ArrayList<>();
    acc.add(this.coordinate);
    Value newValue = this.evaluate(formulaValueHashMap);
    allEvaCell.replace(this.coordinate, newValue);
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

}
