package edu.cs3500.spreadsheets.view;

import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import edu.cs3500.spreadsheets.controller.Features;
import edu.cs3500.spreadsheets.provider.view.ISpreadsheetView;

// the mean problem is, their view take the role of controller, they even don't need a controller
public class ProviderViewAdapter implements IView {
  ISpreadsheetView view;

  public ProviderViewAdapter(ISpreadsheetView view) {
    this.view = view;
  }

  @Override
  public void render() {
    try {
      view.render();
    } catch (IOException e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    }
  }

  @Override
  public void addColumn() {
    throw new UnsupportedOperationException("provider's view can not do this");
  }

  @Override
  public void addRow() {
    throw new UnsupportedOperationException("provider's view can not do this");
  }

  @Override
  public void increaseRowHeaderWidth() {
    throw new UnsupportedOperationException("provider's view can not do this");
  }

  @Override
  public void editCell(int col, int row, String value) {
    // do nothing now, because their view directly redraw
    // or do: redraw?
  }

  @Override
  public String getTextFieldInput() {
    return view.getTextString();
  }

  @Override
  public void clearTextField() {
    view.cleanTextString();
  }

  @Override
  public void resetFocus() {
    view.resetFocus();
  }

  @Override
  public int getSelectedCellRow() {
    return 0;
  }

  @Override
  public int getSelectedCellCol() {
    return 0;
  }

  @Override
  public void removeFocus() {
    // do nothing now...don't need it?
  }

  @Override
  public void getFocus() {
    view.resetFocus();
    // do the same thing as reset focus
  }

  @Override
  public void addActionListener(ActionListener ac) {
    view.addButton();
  }

  @Override
  public void addMouseEventListener(MouseListener textField, MouseListener cells) {
    view.addMouse();
    // provider didn't use different listener
  }

  @Override
  public void setTextFieldInput(String s) {
    // they do it inside...
  }

  @Override
  public void storeTextFieldInput() {
    // they never store it, because they just "clean" never "reset"
  }

  @Override
  public void resetTextField() {
    // same as above
  }

  @Override
  public void addKeyboardListener(KeyListener k) {
    // they don't have a keyboard listener
  }

  @Override
  public int[] getSelectedColumns() {
    return new int[0];
    // they won't need this
  }

  @Override
  public int[] getSelectedRows() {
    return new int[0];
    // they won't need this
  }

  @Override
  public void addFeatures(Features features) {
    view.addMouse();
    view.addButton();
    // yes, they don't have a feature, everything feature (controller) should do have been include
    // in the view
  }

  @Override
  public void setSelectedCell(int col, int row) {

  }

  @Override
  public File setSaveFileChooser() {
    return null;
    // extra credit, they don't support
  }

  @Override
  public File setOpenFileChooser() {
    return null;
    // extra credit, they don't support
  }

  @Override
  public boolean isTextFieldFocused() {
    // they do not support this
    return false;
  }
}
