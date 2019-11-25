package edu.cs3500.spreadsheets.model.cell;

import java.util.HashMap;
import java.util.List;

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
}
