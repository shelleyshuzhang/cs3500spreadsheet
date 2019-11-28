package edu.cs3500.spreadsheets.model.content.value;

//Why I choose this representation:
//We need to support three types of value: String, boolean, and double. So We made this Value as
//abstract class which is a form of Contents, and has three sub type: ValueString, ValueBoolean,
//ValueDouble.

import edu.cs3500.spreadsheets.model.ValueVisitor;
import edu.cs3500.spreadsheets.model.content.Contents;

/**
 * Represents a value which can be String, boolean, or double.
 */
public abstract class Value implements Contents {
  /**
   * An accept method accept a ValueVisitor with an parameter R.
   *
   * @return a result with the type decide by the input visitor
   */
  public abstract <R> R accept(ValueVisitor<R> visitor);

  /**
   * Determine if this is a formula reference.
   *
   * @return false because a value is not a formula reference
   */
  public boolean isFormulaReference() {
    return false;
  }

  /**
   * Get an instance of this value.
   *
   * @return a instance of this value
   */
  public abstract Value getInstance();

  /**
   * Print out a String representation of this value (not do the same thing with toString).
   *
   * @return the String representation
   */
  public abstract String print();

  /**
   * determines if this is a formula or not.
   *
   * @return false since the value class is not a formula
   */
  public boolean isFormula() {
    return false;
  }

  /**
   * determines if this is a string value.
   *
   * @return if this class is a string value
   */
  public abstract boolean isStringValue();

  /**
   * determines if this is a formula function class.
   *
   * @return false since the value class is not a function
   */
  public boolean isFormulaFunction() {
    return false;
  }

}
