package edu.cs3500.spreadsheets.model.cell;

import java.util.HashMap;
import java.util.List;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.content.formula.Formula;
import edu.cs3500.spreadsheets.model.content.value.Value;

/**
 * A Cell Observer which let all cells depend on the target cell observe the change of the target
 * cell, if the target cell changes, then all cells depend on it should change.
 */
public interface ICellObserver {
  /**
   * Update the result of the observer in the all evaluated cell results map, given a formula map
   * recording add the formulas that have been evaluated since now. The method will call the
   * executeUpdate method in the cell and update all the observers in that cell.
   *
   * @param allEvaCell          the results map for all the cells in the spreadsheet
   * @param formulaValueHashMap the results of the formulas that have been evaluated since now
   * @return all the coordinates of the cells that need to be updated in the view
   */
  List<Coord> update(HashMap<Coord, Value> allEvaCell, HashMap<Formula, Value> formulaValueHashMap);

  /**
   * Get the coordinate of the target cell.
   *
   * @return the coordinate of the target cell
   */
  public Coord getCoordinate();

}
