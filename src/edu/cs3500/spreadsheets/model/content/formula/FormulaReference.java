package edu.cs3500.spreadsheets.model.content.formula;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import edu.cs3500.spreadsheets.model.cell.CellGeneral;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.content.Contents;
import edu.cs3500.spreadsheets.model.content.value.Value;

//Why I choose this representation:
//A reference can contain single cell or multiple cell, and we think represent it as a normal class
//and use List of Cells as it field make more sense.

/**
 * Represents a formula reference which is a reference to some cells and is a kind of function.
 */
public class FormulaReference extends Formula {
  private final List<CellGeneral> loc;

  /**
   * Construct a formula reference with a list of cells and the beginning coordinate.
   *
   * @param loc   the given list of cells which contained in the reference
   * @param coord the start coordinate which is the position of this formula reference
   * @throws IllegalArgumentException if given null for loc or the reference points back to itself
   */
  public FormulaReference(List<CellGeneral> loc, Coord coord) {
    if (loc == null) {
      throw new IllegalArgumentException("The reference cells can't be null");
    } else if (!valid(loc, coord)) {
      throw new IllegalArgumentException("The reference points to the given cell itself");
    } else {
      this.loc = loc;
    }
  }

  private FormulaReference(List<CellGeneral> loc) {
    this.loc = loc;
  }

  private static boolean valid(List<CellGeneral> loc, Coord coord) {
    boolean isValid = true;
    List<CellGeneral> hasVisited = new ArrayList<CellGeneral>();
    for (CellGeneral cg : loc) {
      if (cg.getCoordinate().equals(coord)) {
        return false;
      } else if (!hasVisited.contains(cg) && cg.containsReference()) {
        FormulaReference reference = (FormulaReference) cg.getContents();
        List<CellGeneral> insideLoc = reference.getLoc();
        isValid = valid(insideLoc, coord);
        if (!isValid) {
          return false;
        }
        hasVisited.add(cg);
      }
    }
    return isValid;
  }

  @Override
  public Value evaluate(HashMap<Formula, Value> formulaValueMap) {
    if (loc.size() == 1) {
      Value toAdd;
      if (formulaValueMap.containsKey(this)) {
        toAdd = formulaValueMap.get(this);
      } else {
        toAdd = loc.get(0).evaluate(formulaValueMap);
        formulaValueMap.put(this, toAdd);
      }
      return toAdd;
    } else {
      throw new IllegalArgumentException("We can't evaluate a single cell that references " +
              "multiple cells without a function");
    }
  }

  @Override
  public Contents getInstance() {
    return new FormulaReference(this.loc);
  }

  @Override
  public boolean isFormulaReference() {
    return true;
  }

  @Override
  public boolean isFormulaFunction() {
    return false;
  }

  public List<CellGeneral> getLoc() {
    return this.loc;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    FormulaReference fr = (FormulaReference) o;
    if (loc == null) {
      return fr.loc == null;
    }
    return loc.containsAll(fr.loc) && fr.loc.containsAll(loc);
  }

  @Override
  public int hashCode() {
    return Objects.hash(loc);
  }

  @Override
  public String toString() {
    StringBuilder s = new StringBuilder();
    s.append(loc.get(0).getCoordinate().toString());
    int len = loc.size();
    if (len != 1) {
      s.append(":");
      s.append(loc.get(len - 1).getCoordinate().toString());
    }
    return s.toString();
  }

}
