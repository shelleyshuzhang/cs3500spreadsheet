package edu.cs3500.spreadsheets.model;

/**
 * Represents a ValueVisitor which visit String, boolean, double and the method inside return
 * Boolean type.
 */
public class ValueVisitorBoolean implements ValueVisitor<Boolean> {

  @Override
  public Boolean visitString(String value) {
    throw new IllegalArgumentException("String value can't be converted to a boolean");
  }

  @Override
  public Boolean visitBoolean(boolean value) {
    return value;
  }

  @Override
  public Boolean visitDouble(double value) {
    throw new IllegalArgumentException("Double value can't be converted to a boolean");
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
    return ValueVisitorBoolean.class.hashCode();
  }
}
