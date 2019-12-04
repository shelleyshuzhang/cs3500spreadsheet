package edu.cs3500.spreadsheets.controller;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;


import edu.cs3500.spreadsheets.model.BasicWorkSheetBuilder;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.WorksheetReader;
import edu.cs3500.spreadsheets.model.content.Blank;
import edu.cs3500.spreadsheets.model.content.Contents;
import edu.cs3500.spreadsheets.model.worksheet.Worksheet;
import edu.cs3500.spreadsheets.view.EditableView;
import edu.cs3500.spreadsheets.view.IView;
import edu.cs3500.spreadsheets.view.TextualView;

/**
 * A basic controller for worksheet, implements features interface and has some features which can
 * deal with some specific situations.
 */
public class BasicController implements Features {
  Worksheet model;
  IView view;

  /**
   * Construct a BasicController take in a model and a view.
   *
   * @param model provided model for this controller
   * @param view  provided view for this controller
   */
  public BasicController(Worksheet model, IView view) {
    this.model = model;
    this.view = view;
    view.addFeatures(this);
  }

  @Override
  public void saveAndChange() {
    if (view.getEditable()) {
      String contentS = view.getTextFieldInput();
      int col = view.getSelectedCellCol() + 1;
      int row = view.getSelectedCellRow() + 1;
      Contents contentsC = stringToContent(contentS, col, row, model);
      try {
        List<Coord> lo = model.editCellContent(col, row, contentsC);
        this.editWorksheetCells(lo);
      } catch (IllegalArgumentException e) {
        view.removeFocus();
      }
      view.removeFocus();
      view.setEditable(false);
      view.storeTextFieldInput();
    }
  }


  /**
   * Convert a string to a Contents, if the string is empty, then the content is blank.
   *
   * @param s         the string to be converted
   * @param col       the col of the cell that the content should fit in
   * @param row       the row of the cell that the content should fit in
   * @param worksheet the worksheet, which is the model
   * @return the contents that the string converts to
   */
  public static Contents stringToContent(String s, int col, int row, Worksheet worksheet) {
    Contents contentsC;
    if (s.isEmpty()) {
      contentsC = new Blank();
    } else {
      contentsC = BasicWorkSheetBuilder.createContent(
              col, row, s, worksheet.getAllRawCell());
    }
    return contentsC;
  }

  @Override
  public void refuseAndReset() {
    if (view.getEditable()) {
      view.resetTextField();
      view.removeFocus();
      view.setEditable(false);
    }
  }

  @Override
  public void getFocusAction() {
    view.getFocus();
    view.setEditable(true);
    view.storeTextFieldInput();
  }

  @Override
  public void focusAndShow() {
    this.showContentAbove();
    this.getFocusAction();
  }

  @Override
  public void showContentAbove() {
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
    view.setEditable(false);
  }

  @Override
  public void deleteAll() {
    int col = view.getSelectedCellCol() + 1;
    int row = view.getSelectedCellRow() + 1;
    try {
      List<Coord> lo = model.editCellContent(col, row, new Blank());
      this.editWorksheetCells(lo);
    } catch (IllegalArgumentException e) {
      view.removeFocus();
    }
    view.removeFocus();
    view.setTextFieldInput("");
  }

  @Override
  public void makeVisible() {
    this.view.render();
  }

  @Override
  public void saveFile() {
    File f = view.setSaveFileChooser();
    if (f != null) {
      try {
        FileWriter writer = new FileWriter(f);
        IView viewToWrite = new TextualView(model, writer);
        viewToWrite.render();
        writer.close();
      } catch (IOException e) {
        System.out.println("Found the following exception. File can not be read or write");
        e.printStackTrace();
      }
    }
    view.setEditable(false);
  }

  @Override
  public void openFile() {
    File f = view.setOpenFileChooser();
    if (f != null) {
      Worksheet openedModel;
      try {
        FileReader read = new FileReader(f);
        WorksheetReader.WorksheetBuilder<Worksheet> builder = new BasicWorkSheetBuilder();
        openedModel = WorksheetReader.read(builder, read);
        openedModel.evaluateAll();
        IView newView = new EditableView("evaluated and editable", openedModel);
        Features c = new BasicController(openedModel, newView);
        c.makeVisible();
      } catch (IOException e) {
        System.out.println("The given file can not be opened. Please try another file");
        e.printStackTrace();
      }
    }
    view.setEditable(false);
  }

  private void editWorksheetCells(List<Coord> lo) {
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
  }

  @Override
  public String toString() {
    return "Basic Controller with features: saveAndChange, RefuseAndReset, " +
            "getFocusAction, focusAndShow, showContentAbove, deleteAll";
  }

}
