package edu.cs3500.spreadsheets.model.cell;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.content.formula.Formula;
import edu.cs3500.spreadsheets.model.content.value.Value;

/**
 * A Cell Observer which let all cells depend on the target cell observe the change of the target
 * cell, if the target cell changes, then all cells depend on it should change.
 */
public class CellObserver {
  protected CellGeneral observer;

  /**
   * Construct a CellObserver take in a target cell.
   *
   * @param cell the given target cell
   */
  public CellObserver(CellGeneral cell) {
    this.observer = cell;
  }

  /**
   * Update the result of the observer in the all evaluated cell results map, given a formula map
   * recording add the formulas that have been evaluated since now. The method will call the
   * executeUpdate method in the cell and update all the observers in that cell.
   *
   * @param allEvaCell the results map for all the cells in the spreadsheet
   * @param formulaValueHashMap the results of the formulas that have been evaluated since now
   * @return all the coordinates of the cells that need to be updated in the view
   */
  public List<Coord> update(HashMap<Coord, Value> allEvaCell,
                            HashMap<Formula, Value> formulaValueHashMap) {
    return observer.executeUpdate(allEvaCell, formulaValueHashMap);
  }

  /**
   * Get the coordinate of the target cell.
   * @return the coordinate of the target cell
   */
  public Coord getCoordinate() {
    return observer.getCoordinate();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CellObserver cellObserver = (CellObserver) o;
    if (observer == null) {
      return cellObserver.observer == null;
    }
    return observer.toString().equals(cellObserver.observer.toString());
  }

  @Override
  public int hashCode() {
    return Objects.hash(observer);
  }

}
