import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.Objects;

import edu.cs3500.spreadsheets.controller.Features;
import edu.cs3500.spreadsheets.view.IView;

/**
 * A mock view for work sheet IView, which contain a log, when methods in it have been called, the
 * view append corresponding message on the log, which show what should have been done by the view.
 */
public class MockView implements IView {
  StringBuilder log;

  /**
   * Construct a MockView with a StringBuilder.
   *
   * @param log the given String builder
   */
  public MockView(StringBuilder log) {
    this.log = Objects.requireNonNull(log);
  }

  @Override
  public void render() {
    log.append("view has been called to render itself\n");
  }

  @Override
  public void addColumn() {
    log.append("view has been called to add a column on itself\n");
  }

  @Override
  public void addRow() {
    log.append("view has been called to add a row on itself\n");
  }

  @Override
  public void increaseRowHeaderWidth() {
    log.append("view has been called to increase the row header width of itself\n");
  }

  @Override
  public void editCell(int col, int row, String value) {
    log.append("view has been called to edit the cell display on col: " + col + ", row: " +
            row + " by the string value " + value + "\n");
  }

  @Override
  public String getTextFieldInput() {
    log.append("view has been called to give it current text field input \n");
    return "";
  }

  @Override
  public void clearTextField() {
    log.append("view has been called to clear the text field to empty\n");
  }

  @Override
  public void resetFocus() {
    log.append("view has been called to reset the focus on specific place\n");
  }

  @Override
  public int getSelectedCellRow() {
    log.append("view has been called to get the selected cell's row\n");
    return 0;
  }

  @Override
  public int getSelectedCellCol() {
    log.append("view has been called to get the selected cell's col\n");
    return 0;
  }

  @Override
  public void removeFocus() {
    log.append("view has been called to remove the focus from a specific place\n");
  }

  @Override
  public void getFocus() {
    log.append("view has been called to get the focus to a specific place\n");
  }

  @Override
  public void addActionListener(ActionListener ac) {
    log.append("view has been called to add a action listener:\n" + ac.toString() + "\n");
  }

  @Override
  public void addMouseEventListener(MouseListener textField, MouseListener cells) {
    log.append("view has been called to add two mouse listener:\n" + textField.toString() +
            " for text field,\n" + cells + " for cells" + "\n");
  }

  @Override
  public void setTextFieldInput(String s) {
    log.append("view has been called to set " + s + " as it text field input" + "\n");
  }

  @Override
  public void storeTextFieldInput() {
    log.append("view has been called to store it's text field input\n");
  }

  @Override
  public void resetTextField() {
    log.append("view has been called to set the text field input as what is inside the store\n");
  }

  @Override
  public void addKeyboardListener(KeyListener k) {
    log.append("view has been called to add a key board listener:\n" + k.toString() + "\n");
  }

  @Override
  public int[] getSelectedColumns() {
    log.append("view has been called to get all selected cells' col\n");
    return new int[0];
  }

  @Override
  public int[] getSelectedRows() {
    log.append("view has been called to get all selected cells' row\n");
    return new int[0];
  }

  @Override
  public void addFeatures(Features features) {
    log.append("view has been called to add a features object:\n" + features.toString() + "\n");
  }

  @Override
  public void setSelectedCell(int col, int row) {
    log.append("view has been called to set col: " + col + ", row: " + row + " as it selected " +
            "cell position" + "\n");
  }

  @Override
  public File setSaveFileChooser() {
    log.append("view has been called to save file" + "\n");
    return null;
  }

  @Override
  public File setOpenFileChooser() {
    log.append("view has been called to open file" + "\n");
    return null;
  }

  @Override
  public void setEditable(boolean editable) {
    log.append("view has been called to set editable boolean as" + editable + "\n");
  }

  @Override
  public boolean getEditable() {
    log.append("view has been called to get editable boolean" + "\n");
    return false;
  }

  @Override
  public int getCellWidth(int col, int row) {
    log.append("view has been called to get cell width" + "\n");
    return 0;
  }

  @Override
  public int getCellHeight(int col, int row) {
    log.append("view has been called to get cell height" + "\n");
    return 0;
  }

  @Override
  public void setCellWidth(int col, int width) {
    log.append("view has been called to set cell's width in col: " + col + " as " + width + "\n");
  }

  @Override
  public void setCellHeight(int row, int height) {
    log.append("view has been called to set cell's height in row: " + row + " as " + height + "\n");
  }
}
