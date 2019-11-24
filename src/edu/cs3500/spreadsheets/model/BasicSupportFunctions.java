package edu.cs3500.spreadsheets.model;

import java.util.HashMap;

/**
 * Represents all function type that support by BasicWorkSheetModel now.
 */
public enum BasicSupportFunctions {
  /**
   * Sum function.
   */
  SUM,
  /**
   * product function.
   */
  PRODUCT,
  /**
   * less than function.
   */
  LESSTHAN,
  /**
   * String Append function.
   */
  SAPPEND;

  /**
   * get a HashMap which contain all functions support by basic worksheet model.
   *
   * @return the function map
   */
  public static HashMap<String, BasicSupportFunctions> getAvailableFunctions() {
    HashMap<String, BasicSupportFunctions> functionMap = new HashMap<>();
    functionMap.put("SUM", SUM);
    functionMap.put("PRODUCT", PRODUCT);
    functionMap.put("<", LESSTHAN);
    functionMap.put("SAPPEND", SAPPEND);
    return functionMap;
  }
}

