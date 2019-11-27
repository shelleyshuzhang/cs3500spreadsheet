package edu.cs3500.spreadsheets.model.content.formula;

import java.util.HashMap;
import java.util.List;

import edu.cs3500.spreadsheets.model.cell.CellGeneral;
import edu.cs3500.spreadsheets.model.ValueVisitorString;
import edu.cs3500.spreadsheets.model.content.Contents;
import edu.cs3500.spreadsheets.model.content.value.Value;
import edu.cs3500.spreadsheets.model.content.value.ValueString;

//Why I choose this representation:
//This is a function which is a sub kind of formula function, and can be represent as a class.

/**
 * Represents a function append all arguments. If a argument is not String, it will be convert to
 * String form and been append.
 */
public class FunctionStringAppend extends FormulaFunction {

  /**
   * Construct a FunctionStringAppend with arguments inherit from super class.
   *
   * @param arguments list of formula as the input arguments
   */
  public FunctionStringAppend(List<Formula> arguments) {
    super(arguments);
  }

  @Override
  public Value evaluate(HashMap<Formula, Value> formulaValueMap) {
    StringBuilder acc = new StringBuilder();
    for (Formula i : arguments) {
      try {
        Value v1;
        if (formulaValueMap.containsKey(i)) {
          v1 = formulaValueMap.get(i);
        } else {
          v1 = i.evaluate(formulaValueMap);
          formulaValueMap.put(i, v1);
        }
        if (v1 == null) {
          v1 = new ValueString("");
        }
        String toAppend = v1.accept(new ValueVisitorString());
        if (toAppend != null) {
          acc.append(toAppend);
        }
      } catch (IllegalArgumentException e1) {
        FormulaReference toEval = (FormulaReference) i;
        List<CellGeneral> loc = toEval.getLoc();
        for (CellGeneral c : loc) {
          String toAdd;
          if (c.evaluate(formulaValueMap) == null) {
            toAdd = "";
          } else {
            toAdd = c.evaluate(formulaValueMap).accept(new ValueVisitorString());
          }
          if (toAdd == null) {
            toAdd = "";
          }
          acc.append(toAdd);
        }
      }
    }
    return new ValueString(acc.toString());
  }

  @Override
  public Contents getInstance() {
    return new FunctionStringAppend(this.arguments);
  }

  @Override
  public String toString() {
    return this.toStringHelper("(SAPPEND");
  }

}
