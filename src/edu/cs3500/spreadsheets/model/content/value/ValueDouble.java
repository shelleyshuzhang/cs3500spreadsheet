package edu.cs3500.spreadsheets.model.content.value;

import java.util.HashMap;
import java.util.Objects;

import edu.cs3500.spreadsheets.model.ValueVisitor;
import edu.cs3500.spreadsheets.model.content.formula.Formula;

// Why I choose this representation:
// We don't think try to let primitive type boolean to extends Value is a good idea (and we can not
// do this), so we made this class which has double as a field.

/**
 * Represents a double value, which is a kind of Value.
 */
public class ValueDouble extends Value {
  private final double value;

  /**
   * Construct a ValueDouble with the take in double.
   *
   * @param value the given double which this value should contain
   */
  public ValueDouble(double value) {
    this.value = value;
  }

  @Override
  public <R> R accept(ValueVisitor<R> visitor) {
    return visitor.visitDouble(this.value);
  }

  @Override
  public Value evaluate(HashMap<Formula, Value> formulaValueMap) {
    return new ValueDouble(this.value);
  }

  @Override
  public Value getInstance() {
    return new ValueDouble(this.value);
  }

  @Override
  public String print() {
    return String.format("%f", this.value);
  }

  @Override
  public boolean isStringValue() {
    return false;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ValueDouble d = (ValueDouble) o;
    return Math.abs(d.value - value) < 0.000000000001;
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }

  @Override
  public String toString() {
    return String.valueOf(value);
  }
}
