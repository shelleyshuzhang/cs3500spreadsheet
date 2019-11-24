package edu.cs3500.spreadsheets.view.editableView;

import java.util.Set;

import edu.cs3500.spreadsheets.model.worksheet.WorksheetReadOnly;
import edu.cs3500.spreadsheets.sexp.SexpVisitorFormula;
import edu.cs3500.spreadsheets.view.IView;
import edu.cs3500.spreadsheets.view.editableView.EditableViewBlank;

public class EditableViewEval extends EditableViewBlank implements IView {
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
  public EditableViewEval(String caption, WorksheetReadOnly worksheetReadOnly) {
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
      this.editCell(col, row, value);
    }
  }

}
