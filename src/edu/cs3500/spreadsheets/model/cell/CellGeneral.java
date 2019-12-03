package edu.cs3500.spreadsheets.model.cell;

import java.util.HashMap;
import java.util.List;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.content.Contents;
import edu.cs3500.spreadsheets.model.content.formula.Formula;
import edu.cs3500.spreadsheets.model.content.value.Value;

// Why I choose this representation:
// A WorkSheet are formed by cells, so we need to have a representation for cell,
// It better to be an interface because Encapsulation.

/**
 * Represents a general cell type.
 */
public interface CellGeneral {

  /**
   * Get the result value of a cell after evaluate.
   *
   * @param formulaValueMap a formula value map record all formula that has been evaluate before so
   *                        do not need to evaluate it again
   * @return the result value of the evaluation
   */
  Value evaluate(HashMap<Formula, Value> formulaValueMap);

  /**
   * Get coordinate of the cell.
   *
   * @return the coordinate of the cell
   */
  Coord getCoordinate();

  /**
   * Get the contents of the cell.
   *
   * @return the content of the cell
   */
  Contents getContents();

  /**
   * Set the content of the cell to the given one. Will return a list of coordinates of all the
   * cells that are affected by this change and need to reevaluate and update in the view. Will
   * remove the observers that no longer belong to this cell after updates, and add the new ones
   * that should be here after the update.
   *
   * @param contents the contents to set in the cell
   * @param allEvaCell the results of all the cells that have been evaluated and saved in the model
   * @return a list of coordinates that will be affected by the content change and need to change
   */
  List<Coord> setContents(Contents contents, HashMap<Coord, Value> allEvaCell);

  /**
   * Determine if the cell contain reference.
   *
   * @return true if there are, false if there are not.
   */
  boolean containsReference();

  /**
   * Get the cell's value after evaluate if we stored it before.
   *
   * @return the value if we stored before, null if we do not
   */
  Value getCellValue();

  /**
   * Add an given observer with the target cell.
   * @param o the given observer
   */
  void addObserver(ICellObserver o);

  /**
   * determine if the target cell already contain the given observer.
   *
   * @param o the given observer
   * @return true if it contains this observer, false if it not
   */
  boolean containObserver(ICellObserver o);

  /**
   * clear all the observers in the target cell.
   */
  void clearObserver();

  /**
   * delete the given observer in the target cell.
   *
   * @param observer the given observer to be deleted
   * @throws IllegalArgumentException if the observer do not exist before delete
   */
  void deleteObserver(ICellObserver observer);

  /**
   * Execute updates for all the observers in the cell. Make sure that all the observer cells and
   * their observers will be updated together with this cell.
   *
   * @param allEvaCell the results of all the cells that have been evaluated
   * @param formulaValueHashMap the results of the formulas that have been evaluated in this update
   * @return a list of the coordinates of the observers of the cell and their observers that need to
   *         be changed after this update
   */
  List<Coord> executeUpdate(HashMap<Coord, Value> allEvaCell,
                            HashMap<Formula, Value> formulaValueHashMap);

}
