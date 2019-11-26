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
 * Represents a function calculate all the numeric arguments' sum. Default value (if no numeric
 * arguments) is 0.
 */
public class FunctionSum extends FormulaFunction {

  /**
   * Construct a FunctionSum with arguments inherit from super class.
   *
   * @param arguments list of formula as the input arguments
   */
  public FunctionSum(List<Formula> arguments) {
    super(arguments);
  }

  @Override
  public Value evaluate(HashMap<Formula, Value> formulaValueMap) {
    double total = 0.0;
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
          v1 = new ValueDouble(0.0);
        }
        if (v1.accept(new ValueVisitorDouble()) != null) {
          total += v1.accept(new ValueVisitorDouble());
        }
      } catch (IllegalArgumentException e1) {
        if (e1.getMessage().equals("We can't evaluate a single cell that references " +
                "multiple cells without a function")) {
          FormulaReference toEval = (FormulaReference) i;
          List<CellGeneral> loc = toEval.getLoc();
          for (CellGeneral c : loc) {
            try {
              if (c.getCellValue() == null) {
                double toAdd = c.evaluate(formulaValueMap).accept(new ValueVisitorDouble());
                total += toAdd;
              } else {
                total += c.getCellValue().accept(new ValueVisitorDouble());
              }
            } catch (IllegalArgumentException e2) {
              total += 0.0;
            }
          }
        } else {
          total += 0.0;
        }
      }
    }
    return new ValueDouble(total);
  }

  @Override
  public Contents getInstance() {
    return new FunctionSum(this.arguments);
  }

  @Override
  public String toString() {
    return this.toStringHelper("(SUM");
  }

}
