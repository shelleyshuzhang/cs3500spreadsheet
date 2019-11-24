package edu.cs3500.spreadsheets.model;

/**
 * Represents a ValueVisitor which visit String, boolean, double and the method inside return String
 * type.
 */
public class ValueVisitorString implements ValueVisitor<String> {
  @Override
  public String visitString(String value) {
    return value;
  }

  @Override
  public String visitBoolean(boolean value) {
    return Boolean.toString(value);
  }

  @Override
  public String visitDouble(double value) {
    return Double.toString(value);
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
    return ValueVisitorString.class.hashCode();
  }
}
