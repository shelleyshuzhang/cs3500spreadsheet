package edu.cs3500.spreadsheets.model.cell;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.content.formula.Formula;
import edu.cs3500.spreadsheets.model.content.value.Value;

public class CellObserver {
  protected CellGeneral observer;

  public CellObserver(CellGeneral cell) {
    this.observer = cell;
  }

  public List<Coord> update(HashMap<Coord, Value> allEvaCell,
                            HashMap<Formula, Value> formulaValueHashMap) {
    return observer.executeUpdate(allEvaCell, formulaValueHashMap);
  }

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
    return observer.equals(cellObserver.observer);
  }

  @Override
  public int hashCode() {
    return Objects.hash(observer);
  }

}
