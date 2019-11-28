package edu.cs3500.spreadsheets.model.content;

import java.util.HashMap;

import edu.cs3500.spreadsheets.model.content.formula.Formula;
import edu.cs3500.spreadsheets.model.content.value.Value;

//Why I choose this representation:
//A Cell has coordinate and Contents, and Contents can be different form which described in
//the assignment (blank, value, formula), so it also should be an interface.

/**
 * Represents a supported content. For example, a formula, a value, a blank.
 */
public interface Contents {
  /**
   * Get the result value of a contents after evaluate.
   *
   * @param formulaValueMap a formula value map record all formula that has been evaluate before so
   *                        do not need to evaluate it again
   * @return the result value of the evaluation
   */
  Value evaluate(HashMap<Formula, Value> formulaValueMap);

  /**
   * Get a instance of the contents.
   *
   * @return the copy of the content.
   */
  Contents getInstance();

  /**
   * Determine if it is a reference.
   *
   * @return true if it is, false if it is not
   */
  boolean isFormulaReference();

  /**
   * determines if this content is a formula or not.
   *
   * @return a boolean indicating if this content is a formula or not
   */
  boolean isFormula();

  /**
   * determines if this content is a formula function or not.
   *
   * @return a boolean indicating if this content is a formula function or not
   */
  boolean isFormulaFunction();
}
