package edu.cs3500.spreadsheets.model.content.formula;

import java.util.HashMap;
import java.util.List;

import edu.cs3500.spreadsheets.model.ValueVisitorDouble;
import edu.cs3500.spreadsheets.model.content.Contents;
import edu.cs3500.spreadsheets.model.content.value.Value;
import edu.cs3500.spreadsheets.model.content.value.ValueBoolean;

//Why I choose this representation:
//This is a function which is a sub kind of formula function, and can be represent as a class.

/**
 * Represents a function to compare exactly two number, if the first one is smaller than second. It
 * is a kind of formula function.
 */
public class FunctionLessThan extends FormulaFunction {

  /**
   * Construct a FunctionLessThan which inherit arguments from the supper class.
   *
   * @param arguments arguments given for this function
   */
  public FunctionLessThan(List<Formula> arguments) {
    super(arguments);
  }

  @Override
  public Value evaluate(HashMap<Formula, Value> formulaValueMap) {
    if (arguments.size() != 2) {
      throw new IllegalArgumentException("Please give exactly two arguments to compare");
    } else {
      Formula f1 = arguments.get(0);
      Formula f2 = arguments.get(1);
      Value v1;
      Value v2;
      if (formulaValueMap.containsKey(f1)) {
        v1 = formulaValueMap.get(f1);
      } else {
        v1 = f1.evaluate(formulaValueMap);
        formulaValueMap.put(f1, v1);
      }
      if (formulaValueMap.containsKey(f2)) {
        v2 = formulaValueMap.get(f2);
      } else {
        v2 = f2.evaluate(formulaValueMap);
        formulaValueMap.put(f2, v2);
      }
      if (v1.accept(new ValueVisitorDouble()) == null
              || v2.accept(new ValueVisitorDouble()) == null) {
        throw new IllegalArgumentException("Please give exactly two arguments to compare");
      } else {
        double first;
        double second;
        first = v1.accept(new ValueVisitorDouble());
        second = v2.accept(new ValueVisitorDouble());
        return new ValueBoolean(first < second);
      }
    }
  }

  @Override
  public Contents getInstance() {
    return new FunctionLessThan(this.arguments);
  }

  @Override
  public String toString() {
    return this.toStringHelper("(<");
  }
}
