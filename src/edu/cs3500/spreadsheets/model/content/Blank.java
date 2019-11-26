package edu.cs3500.spreadsheets.model.content;

import java.util.HashMap;

import edu.cs3500.spreadsheets.model.content.formula.Formula;
import edu.cs3500.spreadsheets.model.content.value.Value;

//Why I choose this representation:
//Blank is just simple blank, and it is a kind of contents,
//so it can be a simple class implements Contents.

/**
 * Represents a blank content (nothing here).
 */
public class Blank implements Contents {

  @Override
  public Value evaluate(HashMap<Formula, Value> formulaValueMap) {
    return null;
  }

  @Override
  public Contents getInstance() {
    return new Blank();
  }

  @Override
  public boolean isFormulaReference() {
    return false;
  }

  @Override
  public boolean isFormula() {
    return false;
  }

  @Override
  public boolean isFormulaFunction() {
    return false;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    return o != null && getClass() == o.getClass();
  }

  @Override
  public int hashCode() {
    return Blank.class.hashCode();
  }

  @Override
  public String toString() {
    return "";
  }

}
