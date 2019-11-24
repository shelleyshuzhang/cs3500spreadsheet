package edu.cs3500.spreadsheets.model.content.formula;

import java.util.List;
import java.util.Objects;

//Why I choose this representation:
//A Formula Function has different sub kinds, so we use an abstract class to represents it.

/**
 * Represents a formula function which is a function and is a kind of function.
 */
public abstract class FormulaFunction extends Formula {
  protected List<Formula> arguments;

  /**
   * Construct a formula function with a list of arguments.
   *
   * @param arguments the given arguments for this function
   * @throws IllegalArgumentException if given null for arguments
   */
  public FormulaFunction(List<Formula> arguments) {
    if (arguments != null) {
      this.arguments = arguments;
    } else {
      throw new IllegalArgumentException("The given list of formula can not be null");
    }
  }

  @Override
  public boolean isFormulaReference() {
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
    FormulaFunction fFunc = (FormulaFunction) o;
    if (arguments == null) {
      return fFunc.arguments == null;
    }
    return arguments.equals(fFunc.arguments);
  }

  @Override
  public int hashCode() {
    return Objects.hash(arguments);
  }

  protected String toStringHelper(String funcName) {
    StringBuilder s = new StringBuilder();
    s.append(funcName);
    int len = this.arguments.size();
    for (int i = 0; i < len; i++) {
      s.append(" ");
      s.append(this.arguments.get(i).toString());
    }
    s.append(")");
    return s.toString();
  }

}
