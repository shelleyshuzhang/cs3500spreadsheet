package edu.cs3500.spreadsheets.model.content.formula;

import java.util.HashMap;
import java.util.Objects;

import edu.cs3500.spreadsheets.model.content.Contents;
import edu.cs3500.spreadsheets.model.content.value.Value;

//Why I choose this representation:
//A FormulaValue is different from Value, because it is a formula (after an "="), so we make
//this FormulaValue class which perform like a Value, but its structure is: use Value as the field.

/**
 * Represents a formula value which is a value in formula form.
 */
public class FormulaValue extends Formula {
  private final Value value;

  /**
   * Construct a formula value with a value.
   *
   * @param value the given value which this formula value should contain
   */
  public FormulaValue(Value value) {
    this.value = value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    FormulaValue fv = (FormulaValue) o;
    if (value == null) {
      return fv.value == null;
    }
    return value.equals(fv.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }

  @Override
  public Value evaluate(HashMap<Formula, Value> formulaValueMap) {
    Value toAdd;
    if (formulaValueMap.containsKey(this)) {
      toAdd = formulaValueMap.get(this);
    } else {
      toAdd = value.evaluate(formulaValueMap);
      formulaValueMap.put(this, toAdd);
    }
    return toAdd;
  }

  @Override
  public Contents getInstance() {
    return new FormulaValue(this.value);
  }

  @Override
  public boolean isFormulaReference() {
    return false;
  }

  @Override
  public boolean isFormulaFunction() {
    return false;
  }

  @Override
  public String toString() {
    if (value.isStringValue()) {
      return "\"" + value.toString() + "\"";
    } else {
      return value.toString();
    }
  }
}
