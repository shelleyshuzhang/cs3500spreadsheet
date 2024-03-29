package edu.cs3500.spreadsheets.model.content.formula;

import java.util.HashMap;
import java.util.List;

import edu.cs3500.spreadsheets.model.cell.CellGeneral;
import edu.cs3500.spreadsheets.model.ValueVisitorDouble;
import edu.cs3500.spreadsheets.model.content.Contents;
import edu.cs3500.spreadsheets.model.content.value.Value;
import edu.cs3500.spreadsheets.model.content.value.ValueDouble;

//Why I choose this representation:
//This is a function which is a sub kind of formula function, and can be represent as a class.

/**
 * Represents a function calculate all the numeric arguments' product. Default value (if no numeric
 * arguments) is 0.
 */
public class FunctionProduct extends FormulaFunction {

  /**
   * Construct a FunctionProduct with arguments inherit from super class.
   *
   * @param arguments list of formula as the input arguments
   */
  public FunctionProduct(List<Formula> arguments) {
    super(arguments);
  }

  @Override
  public Value evaluate(HashMap<Formula, Value> formulaValueMap) {
    double total = 0.0;
    boolean allBlank = true;
    for (Formula i : this.arguments) {
      try {
        Value v1;
        if (formulaValueMap.containsKey(i)) {
          v1 = formulaValueMap.get(i);
        } else {
          v1 = i.evaluate(formulaValueMap);
          formulaValueMap.put(i, v1);
        }
        if (v1 != null && v1.accept(new ValueVisitorDouble()) != null) {
          double toMultiply = v1.accept(new ValueVisitorDouble());
          if (allBlank) {
            allBlank = false;
            total = 1.0;
          }
          total = total * toMultiply;
        }
      } catch (IllegalArgumentException e1) {
        if (e1.getMessage().equals("We can't evaluate a single cell that references " +
                "multiple cells without a function")) {
          FormulaReference toEval = (FormulaReference) i;
          List<CellGeneral> loc = toEval.getLoc();
          for (CellGeneral c : loc) {
            try {
              double toMultiply;
              if (c.evaluate(formulaValueMap) == null) {
                toMultiply = 1.0;
              } else {
                toMultiply = c.evaluate(formulaValueMap).accept(new ValueVisitorDouble());
                if (allBlank) {
                  allBlank = false;
                  total = 1.0;
                }
              }
              total = total * toMultiply;
            } catch (IllegalArgumentException e2) {
              total = total * 1.0;
            }
          }
        } else {
          total = total * 1.0;
        }
      }
    }
    return new ValueDouble(total);
  }

  @Override
  public Contents getInstance() {
    return new FunctionProduct(this.arguments);
  }

  @Override
  public String toString() {
    return this.toStringHelper("(PRODUCT");
  }
}
