package edu.cs3500.spreadsheets.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.cs3500.spreadsheets.model.BasicWorkSheetBuilder;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.content.Contents;
import edu.cs3500.spreadsheets.model.worksheet.Worksheet;
import edu.cs3500.spreadsheets.view.IView;

public class MapController {
  Worksheet model;
  IView view;

  public MapController(Worksheet model, IView view) {
    this.model = model;
    this.view = view;
    setButtonListener();
    setMouseListener();
  }

  private void setButtonListener() {
    Map<String, Runnable> buttonClickedMap = new HashMap<String, Runnable>();
    ButtonListener bListener = new ButtonListener();

    buttonClickedMap.put("accept edit", new TickButtonAction());
    buttonClickedMap.put("refuse edit", new CrossButtonAction());

    bListener.setButtonActionMap(buttonClickedMap);
    this.view.addActionListener(bListener);
  }


  class TickButtonAction implements Runnable {

    @Override
    public void run() {
      String content = view.getTextFieldInput();
      int col = view.getSelectedCellCol() + 1;
      int row = view.getSelectedCellRow() + 1;
      Contents contents = BasicWorkSheetBuilder.createContent(
              col, row, content, model.getAllRawCell());
      try {
        List<Coord> lo = model.editCellContent(col, row, contents);
        for (Coord c : lo) {
          int affectedCol = c.col;
          int affectedRow = c.row;
          String value;
          try {
            value = model.getOneCellResult(affectedCol, affectedRow).print();
          } catch (IllegalArgumentException e) {
            value = e.getMessage();
          }
          view.editCell(affectedCol, affectedRow, value);
        }
      } catch (IllegalArgumentException e) {
        view.removeFocus();
      }
      view.removeFocus();
      view.storeTextFieldInput();
      //??? not sure here, belong to controller or view
    }
  }

  class CrossButtonAction implements Runnable {

    @Override
    public void run() {
      view.resetTextField();
      view.removeFocus();
    }
  }

  public void go() {
    this.view.render();
  }

  private void setMouseListener() {
    Map<Integer, Runnable> MouseMapTextField = new HashMap<Integer, Runnable>();
    Map<Integer, Runnable> MouseMapCells = new HashMap<Integer, Runnable>();
    MouseEventListener mListenerTextField = new MouseEventListener();
    MouseEventListener mListenerCells = new MouseEventListener();

    MouseMapTextField.put(1, new GetFocusAction());
    MouseMapCells.put(2, new FocusAndShow());
    MouseMapCells.put(1, new ShowContentAbove());

    mListenerTextField.setMouseActionMap(MouseMapTextField);
    mListenerCells.setMouseActionMap(MouseMapCells);
    this.view.addMouseEventListener(mListenerTextField, mListenerCells);
  }

  // also store the textField for reset
  class GetFocusAction implements Runnable {

    @Override
    public void run() {
      view.getFocus();
      view.storeTextFieldInput();
    }
  }

  class FocusAndShow implements Runnable {

    @Override
    public void run() {
      ShowContentAbove s = new ShowContentAbove();
      GetFocusAction g = new GetFocusAction();
      s.run();
      g.run();
    }
  }

  class ShowContentAbove implements Runnable {

    @Override
    public void run() {
      int col = view.getSelectedCellCol() + 1;
      int row = view.getSelectedCellRow() + 1;
      try {
        Contents c = model.getOneCellRawContents(col, row);
        String s;
        if (c.isFormula()) {
          s = "=" + model.getOneCellRawContents(col, row).toString();
        } else {
          s = model.getOneCellRawContents(col, row).toString();
        }
        view.setTextFieldInput(s);
      } catch (IllegalArgumentException e) {
        view.setTextFieldInput("");
      }
    }
  }
}
