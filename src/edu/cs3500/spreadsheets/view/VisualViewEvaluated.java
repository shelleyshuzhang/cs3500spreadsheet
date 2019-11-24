package edu.cs3500.spreadsheets.view;

import java.util.Set;

import edu.cs3500.spreadsheets.model.worksheet.WorksheetReadOnly;
import edu.cs3500.spreadsheets.sexp.SexpVisitorFormula;

/**
 * A visual view class for worksheet which can produce an worksheet with evaluated values in it, the
 * evaluated worksheet can scrolling. It extends JFrame class in java swing library and implements
 * IVew interface (interface for all views for worksheet).
 */
public class VisualViewEvaluated extends VisualViewBlank {
  private static int DEFAULT_ROW = 1000;
  private static int DEFAULT_COL = 1000;

  /**
   * A constructor for VisualViewEvaluated, construct a visual worksheet with evaluated values in
   * it.
   *
   * @param caption           the given title for JFrame
   * @param worksheetReadOnly a read only model for worksheet, visual view use this model to get
   *                          evaluated values which should be filled in to the visual worksheet
   */
  public VisualViewEvaluated(String caption, WorksheetReadOnly worksheetReadOnly) {
    super(caption, DEFAULT_ROW, DEFAULT_COL);
    Set<String> coords = worksheetReadOnly.getAllCellCoordinates();
    for (String s : coords) {
      int[] coord = SexpVisitorFormula.getSingleRefer(s);
      int col = coord[0];
      int row = coord[1];
      if (col >= 1000 || row >= 1000) {
        while (col >= 1000) {
          this.panel.addColumn();
          col--;
        }
        while (row >= 1000) {
          this.panel.addRow();
          row--;
        }
      }
      String value;
      try {
        value = worksheetReadOnly.getOneCellResult(col, row).print();
      } catch (IllegalArgumentException e) {
        value = e.getMessage();
      }
      this.editTableCell(col, row, value);
    }
  }

  private void editTableCell(int col, int row, String value) {
    this.panel.editCellValue(col, row, value);
  }
}
