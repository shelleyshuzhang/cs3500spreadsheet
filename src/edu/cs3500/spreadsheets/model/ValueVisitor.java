package edu.cs3500.spreadsheets.model;


/**
 * An abstracted function object for processing a value, which can be String, boolean, double.
 *
 * @param <R> The return type of this function
 */
public interface ValueVisitor<R> {
  /**
   * Visit a String and return a type R result.
   *
   * @return a type R result
   */
  R visitString(String value);

  /**
   * Visit a boolean and return a type R result.
   *
   * @return a type R result
   */
  R visitBoolean(boolean value);

  /**
   * Visit a double and return a type R result.
   *
   * @return a type R result
   */
  R visitDouble(double value);
}
