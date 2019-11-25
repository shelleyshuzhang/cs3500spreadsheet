package edu.cs3500.spreadsheets.controller;

import java.util.List;

import edu.cs3500.spreadsheets.model.BasicWorkSheetBuilder;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.content.Contents;
import edu.cs3500.spreadsheets.model.worksheet.Worksheet;
import edu.cs3500.spreadsheets.view.IView;

public class BasicController implements Features {
  private Worksheet model;
  private IView view;

  public BasicController(Worksheet model, IView view) {
    this.model = model;
    this.view = view;
  }


  @Override
  public void acceptEdit(int col, int row) {
    String content = view.getTextFieldInput();
    Contents contents = BasicWorkSheetBuilder.createContent(
            col, row, content, model.getAllRawCell());
    List<Coord> lo = model.editCellContent(col, row, contents);
    for (Coord c : lo) {
      String value;
      try {
        value = model.getOneCellResult(col, row).print();
      } catch (IllegalArgumentException e) {
        value = e.getMessage();
      }
      view.editCell(col, row, value);
    }
    view.clearTextField();
    view.resetFocus();
    //??? not sure here, belong to controller or view
  }

  @Override
  public void refuseEdit(int col, int row) {
    view.clearTextField();
    view.resetFocus();
  }

  @Override
  public void invokeEdit(int col, int row) {

  }
}
