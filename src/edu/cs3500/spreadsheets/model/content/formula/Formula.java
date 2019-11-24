package edu.cs3500.spreadsheets.model.content.formula;

//Why I choose this representation:
//A formula can has different sub kind, so we use abstract formula to represents it.

import edu.cs3500.spreadsheets.model.content.Contents;

/**
 * Represents a formula which is a kind of content.
 */
public abstract class Formula implements Contents {

  public boolean isFormula() {
    return true;
  }

}
