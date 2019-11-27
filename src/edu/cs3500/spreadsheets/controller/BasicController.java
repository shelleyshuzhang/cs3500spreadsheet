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
    //this.setView(view);
  }

  /*
  // This attaches this controller as our features listener
  public void setView(IView v) {
    this.view = v;
    view.addFeature(this);

    // Choose the keys we want
    view.setHotKey(KeyStroke.getKeyStroke("pressed a"), "acceptCellInput");
    view.setHotKey(KeyStroke.getKeyStroke("pressed b"), "rejectCellInput");
    view.setHotKey(KeyStroke.getKeyStroke("pressed c"), "evokeCellEdit");
  }
   */

  @Override
  public void acceptEdit() {
    String content = view.getTextFieldInput();
    int col = view.getSelectedCellCol();
    int row = view.getSelectedCellRow();
    System.out.println(col);
    System.out.println(row);
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
    view.removeFocus();
    //??? not sure here, belong to controller or view
  }

  @Override
  public void refuseEdit() {
    view.clearTextField();
    view.removeFocus();
  }

  @Override
  public void invokeEdit() {
    view.getFocus();
  }

}
