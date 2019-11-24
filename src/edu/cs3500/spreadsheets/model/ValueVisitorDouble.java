package edu.cs3500.spreadsheets.model;

/**
 * Represents a ValueVisitor which visit String, boolean, double and the method inside return Double
 * type.
 */
public class ValueVisitorDouble implements ValueVisitor<Double> {

  @Override
  public Double visitString(String value) {
    throw new IllegalArgumentException("String value can't be converted to a double");
  }

  @Override
  public Double visitBoolean(boolean value) {
    throw new IllegalArgumentException("Boolean value can't be converted to a double");
  }

  @Override
  public Double visitDouble(double value) {
    return value;
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
    return ValueVisitorDouble.class.hashCode();
  }
}
