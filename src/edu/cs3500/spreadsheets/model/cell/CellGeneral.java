package edu.cs3500.spreadsheets.model.cell;

import java.util.HashMap;
import java.util.List;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.content.Contents;
import edu.cs3500.spreadsheets.model.content.formula.Formula;
import edu.cs3500.spreadsheets.model.content.value.Value;

//Why I choose this representation:
//A WorkSheet are formed by cells, so we need to have a representation for cell,
//It better to be an interface because Encapsulation.

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

  public void addObserver(CellObserver o);

  public boolean containObserver(CellObserver o);

  public void clearObserver();

  public void deleteObserver(CellObserver observer);

  public List<Coord> executeUpdate(HashMap<Coord, Value> allEvaCell,
                                   HashMap<Formula, Value> formulaValueHashMap);

}
