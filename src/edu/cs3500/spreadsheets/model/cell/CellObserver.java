package edu.cs3500.spreadsheets.model.cell;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.content.formula.Formula;
import edu.cs3500.spreadsheets.model.content.value.Value;

/**
 * A Cell Observer implements ICellObserver, which let all cells depend on the target cell observe
 * the change of the target cell, if the target cell changes, then all cells depend on it should
 * change.
 */
public class CellObserver implements ICellObserver {
  protected CellGeneral observer;

  /**
   * Construct a CellObserver take in a target cell.
   *
   * @param cell the given target cell
   */
  public CellObserver(CellGeneral cell) {
    this.observer = cell;
  }

  @Override
  public List<Coord> update(HashMap<Coord, Value> allEvaCell,
                            HashMap<Formula, Value> formulaValueHashMap) {
    return observer.executeUpdate(allEvaCell, formulaValueHashMap);
  }

  @Override
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
