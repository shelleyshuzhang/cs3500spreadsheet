package edu.cs3500.spreadsheets.model.content.value;

import java.util.HashMap;
import java.util.Objects;

import edu.cs3500.spreadsheets.model.ValueVisitor;
import edu.cs3500.spreadsheets.model.content.formula.Formula;

// Why I choose this representation:
// We don't think try to let primitive type boolean to extends Value is a good idea (and we can not
// do this), so we made this class which has boolean as a field.

/**
 * Represents a boolean value, which is a kind of Value.
 */
public class ValueBoolean extends Value {
  private final boolean value;

  /**
   * Construct a ValueBoolean with the take in boolean.
   *
   * @param value the given boolean which this value should contain
   */
  public ValueBoolean(boolean value) {
    this.value = value;
  }

  @Override
  public <R> R accept(ValueVisitor<R> visitor) {
    return visitor.visitBoolean(this.value);
  }

  @Override
  public Value evaluate(HashMap<Formula, Value> formulaValueMap) {
    return new ValueBoolean(this.value);
  }

  @Override
  public Value getInstance() {
    return new ValueBoolean(this.value);
  }

  @Override
  public String print() {
    return Boolean.toString(value);
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
    ValueBoolean b = (ValueBoolean) o;
    return value == b.value;
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
