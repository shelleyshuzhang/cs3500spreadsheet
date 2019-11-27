package edu.cs3500.spreadsheets.controller;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;


import javax.imageio.IIOException;

import edu.cs3500.spreadsheets.model.BasicWorkSheetBuilder;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.content.Blank;
import edu.cs3500.spreadsheets.model.content.Contents;
import edu.cs3500.spreadsheets.model.worksheet.Worksheet;
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
    String contentS = view.getTextFieldInput();
    int col = view.getSelectedCellCol() + 1;
    int row = view.getSelectedCellRow() + 1;
    Contents contentsC;
    if (contentS.isEmpty()) {
      contentsC = new Blank();
    } else {
      contentsC = BasicWorkSheetBuilder.createContent(
              col, row, contentS, model.getAllRawCell());
    }
    try {
      List<Coord> lo = model.editCellContent(col, row, contentsC);
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
  }

  @Override
  public void RefuseAndReset() {
    view.resetTextField();
    view.removeFocus();
  }

  @Override
  public void getFocusAction() {
    view.getFocus();
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
  }

  @Override
  public void deleteAll() {
    int col = view.getSelectedCellCol() + 1;
    int row = view.getSelectedCellRow() + 1;
    try {
      List<Coord> lo = model.editCellContent(col, row, new Blank());
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
    view.setTextFieldInput("");
  }

  @Override
  public void go() {
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
  }

}
