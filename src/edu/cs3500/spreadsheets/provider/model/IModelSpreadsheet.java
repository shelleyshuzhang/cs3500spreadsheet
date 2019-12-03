package edu.cs3500.spreadsheets.provider.model;

import java.util.Map;

import edu.cs3500.spreadsheets.model.Coord;

/**
 * create a interface of all type of worksheet.
 */

public interface IModelSpreadsheet {

  /**
   * get the copy of current worksheet.
   *
   * @return the worksheet
   */
  Map getWorksheet();

  /**
   * mutate a ICellContent by use operation in the current worksheet.
   *
   * @param col the select coordinate
   * @param row the select coordinate
   * @param in  the operation
   */
  void addAndUpdate(int col, int row, String in);

  /**
   * return the the Evaluate date in a string type or return the textfield data in string type.
   *
   * @param col the select coordinate
   * @param row the select coordinate
   * @param show whether is show the textfield part
   * @return a string of that Evaluate date
   */
  String getContent(int col, int row, boolean show);

  /**
   * check if two worksheets is the same.
   *
   * @param w the other worksheet
   * @return a boolean of whether two worksheets is the same
   */
  boolean sameModel(IModelSpreadsheet w);

  /**
   * remove a all the thing about the given key in the hashMap in model.
   *
   * @param c a coord is the key of the cell in the map
   */
  void removeCell(Coord c);

}
