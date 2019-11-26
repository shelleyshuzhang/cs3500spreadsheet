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
      int col = view.getSelectedCellCol();
      int row = view.getSelectedCellRow();
      Contents contents = BasicWorkSheetBuilder.createContent(
              col, row, content, model.getAllRawCell());
      try {
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
      } catch (IllegalArgumentException e) {
        view.removeFocus();
      }
      view.clearTextField();
      view.removeFocus();
      //??? not sure here, belong to controller or view
    }
  }

  class CrossButtonAction implements Runnable {

    @Override
    public void run() {
      view.clearTextField();
      view.removeFocus();
    }
  }

  public void go() {
    this.view.render();
  }

  private void setMouseListener() {
    Map<Integer, Runnable> MouseMap = new HashMap<Integer, Runnable>();
    MouseEventListener mListener = new MouseEventListener();

    MouseMap.put(2, new GetFocusAction());

    mListener.setMouseActionMap(MouseMap);
    this.view.addMouseEventListener(mListener);
  }

  class GetFocusAction implements Runnable {

    @Override
    public void run() {
      view.getFocus();
    }
  }

}
