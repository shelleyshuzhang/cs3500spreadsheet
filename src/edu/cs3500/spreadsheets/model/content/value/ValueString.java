package edu.cs3500.spreadsheets.model.content.value;

import java.util.HashMap;
import java.util.Objects;

import edu.cs3500.spreadsheets.model.ValueVisitor;
import edu.cs3500.spreadsheets.model.content.formula.Formula;

// Why I choose this representation:
// We don't think try to let primitive type boolean to extends Value is a good idea (and we can not
// do this), so we made this class which has String as a field.

/**
 * Represents a String value, which is a kind of Value.
 */
public class ValueString extends Value {
  private final String value;

  /**
   * Construct a ValueString with the take in double.
   *
   * @param value the given String which this value should contain
   */
  public ValueString(String value) {
    this.value = value;
  }

  @Override
  public <R> R accept(ValueVisitor<R> visitor) {
    return visitor.visitString(this.value);
  }

  @Override
  public Value evaluate(HashMap<Formula, Value> formulaValueMap) {
    return new ValueString(this.value);
  }

  @Override
  public Value getInstance() {
    return new ValueString(this.value);
  }

  @Override
  public String print() {
    StringBuilder s = new StringBuilder();
    for (int i = 0; i < this.value.length(); i++) {
      char c = this.value.charAt(i);
      if (c == '"') {
        s.append('\"');
      } else if (c == '\\') {
        s.append('\\');
        s.append(c);
      } else {
        s.append(c);
      }
    }
    if (s.toString().isEmpty()) {
      return "";
    } else {
      return "\"" + s.toString() + "\"";
    }
  }

  @Override
  public boolean isStringValue() {
    return true;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ValueString s = (ValueString) o;
    if (value == null) {
      return s.value == null;
    }
    return value.equals(s.value);
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
