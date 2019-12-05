package edu.cs3500.spreadsheets.view;

import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import edu.cs3500.spreadsheets.controller.Features;
import edu.cs3500.spreadsheets.provider.view.ISpreadsheetView;

/**
 * A adapter let the provider's view to work as our view. Because the big difference between our
 * design (their view do everything by itself and talk to the model directly, our view let the
 * controller to control everything and send command to the model and view), most of methods in our
 * IView interface are unsupported (because their view do everything so they don't need to support
 * many functions in the interface which can be called by outside (controller)). Three are three
 * types: 1. for methods which totally unrelated to their view, throw UnsupportedOperationException.
 * 2. for methods which they do similar things in their implementation, but choose not make them as
 * public methods in the interface, we wrote comment and make it default. 3. for methods which its
 * make sense to transform theirs to ours, we call appropriate method(s) use delegation.
 */
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
    // they won't need to call this
  }

  @Override
  public void addRow() {
    throw new UnsupportedOperationException("provider's view can not do this");
    // they won't need to call this
  }

  @Override
  public void increaseRowHeaderWidth() {
    throw new UnsupportedOperationException("provider's view can not do this");
    // they won't need to call this
  }

  @Override
  public void editCell(int col, int row, String value) {
    // do nothing now, because their view directly redraw and do everything inside
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
    // their method to get selected cell row is inside their implementation, they
    // did not exposed it as public, because they don't need controller to call
    // this method and do related thing.
  }

  @Override
  public int getSelectedCellCol() {
    return 0;
    // same as getSelectedCellRow
  }

  @Override
  public void removeFocus() {
    // do nothing now
  }

  @Override
  public void getFocus() {
    view.resetFocus();
    // do the same thing as reset focus
  }

  @Override
  public void addActionListener(ActionListener ac) {
    view.addButton();
    // it is similar to their addButton, however, we call it in different order. So after thinking,
    // we think to call their addButton in addFeature method make more sense.
  }

  @Override
  public void addMouseEventListener(MouseListener textField, MouseListener cells) {
    view.addMouse();
    // provider didn't use different listener
    // else same as above
  }

  @Override
  public void setTextFieldInput(String s) {
    // they do it inside their implementation, because they don't need this method to be exposed
    // base on their design
  }

  @Override
  public void storeTextFieldInput() {
    // they never store it, because they just "clean" never "reset"
    // edit: we ask our provider to change their implementation to fix this bug, but again,
    // this method is inside their implementation and not public for the controller to call
  }

  @Override
  public void resetTextField() {
    // same as above, inside
  }

  @Override
  public void addKeyboardListener(KeyListener k) {
    // they don't have a keyboard listener
  }

  @Override
  public int[] getSelectedColumns() {
    return new int[0];
    // they won't need this, this is our preparation for lines graph (not required)
  }

  @Override
  public int[] getSelectedRows() {
    return new int[0];
    // same as above
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
    // they also don't need this...similar function is inside their implementation
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
  public void setEditable(boolean editable) {
    // they do not support this
  }

  @Override
  public boolean getEditable() {
    return false;
    // they do not support this
  }
}
