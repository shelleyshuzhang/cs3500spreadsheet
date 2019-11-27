package edu.cs3500.spreadsheets.view;

import java.awt.Dimension;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.io.File;

import javax.swing.*;

import edu.cs3500.spreadsheets.controller.Features;
import edu.cs3500.spreadsheets.model.worksheet.WorksheetReadOnly;


/**
 * A visual view class for worksheet which can produce an empty worksheet, the blank worksheet can
 * scrolling. It extends JFrame class in java swing library and implements IVew interface (interface
 * for all views for worksheet).
 */
public class VisualView extends JFrame implements IView {
  protected WorksheetScrollablePanel panel;
  private static Color FRAME_BACKGROUND = new Color(233, 233, 243);
  private static int VIEW_LOCATION_X = 500;
  private static int VIEW_LOCATION_Y = 500;
  private static Dimension VIEW_MIN_SIZE = new Dimension(500, 500);
  private static int DEFAULT_ROW = 1000;
  private static int DEFAULT_COL = 1000;

  /**
   * A constructor for VisualViewBlank, construct a blank visual worksheet with desired size.
   *
   * @param caption the given title for JFrame
   */
  public VisualView(String caption, WorksheetReadOnly worksheetReadOnly) {
    super(caption);
    this.setLocation(VIEW_LOCATION_X, VIEW_LOCATION_Y);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setResizable(true);
    this.setMinimumSize(VIEW_MIN_SIZE);
    this.panel = new WorksheetScrollablePanel(new JTable(DEFAULT_ROW, DEFAULT_COL) {
      private static final long serialVersionUID = 1L;

      public boolean isCellEditable(int row, int column) {
        return false;
      }
    });
    this.add(this.panel);
    this.setBackground(FRAME_BACKGROUND);
    EditableView.setTableValues(worksheetReadOnly, this.panel, this);
    pack();
  }

  @Override
  public void addColumn() {
    this.panel.addColumn();
    this.repaint();
  }

  @Override
  public void addRow() {
    this.panel.addRow();
    this.repaint();
  }

  @Override
  public void increaseRowHeaderWidth() {
    this.panel.increaseWorksheetHeaderSize();
  }

  @Override
  public void editCell(int col, int row, String value) {
    //do nothing because this is not the job for a uneditable view
    this.editTableCell(col, row, value);
  }

  @Override
  public String getTextFieldInput() {
    // should never return anything because there is no text field in an uneditable cell
    return null;
  }

  @Override
  public void clearTextField() {
    // nothing here because an uneditable cell should not have a text field
  }

  @Override
  public void resetFocus() {
    // nothing here because an uneditable cell should not have this functionality
  }

  @Override
  public int getSelectedCellRow() {
    return this.panel.getSelectedCellRow();
  }

  @Override
  public int getSelectedCellCol() {
    return this.panel.getSelectedCellColumn();
  }

  @Override
  public void removeFocus() {
    // nothing here because an uneditable cell should not have this functionality
  }

  @Override
  public void getFocus() {
    // nothing here because an uneditable cell should not have this functionality
  }

  @Override
  public void addActionListener(ActionListener ac) {
    // nothing here because an uneditable cell should not have this functionality
  }

  @Override
  public void addMouseEventListener(MouseListener textField, MouseListener cells) {
    // nothing here because an uneditable cell should not have this functionality
  }

  @Override
  public void setTextFieldInput(String s) {
    // nothing here because an uneditable cell should not have this functionality
  }

  @Override
  public void storeTextFieldInput() {
    // nothing here because an uneditable cell should not have this functionality
  }

  @Override
  public void resetTextField() {
    // nothing here because an uneditable cell should not have this functionality
  }

  @Override
  public void addKeyboardListener(KeyListener k) {
    // nothing here because an uneditable cell should not have this functionality
  }

  @Override
  public int[] getSelectedColumns() {
    // nothing here because an uneditable cell should not have this functionality
    return new int[]{};
  }

  @Override
  public int[] getSelectedRows() {
    // nothing here because an uneditable cell should not have this functionality
    return new int[]{};
  }

  @Override
  public void addFeatures(Features features) {
    // nothing here because an uneditable cell should not have this functionality
  }

  @Override
  public void setSelectedCell(int col, int row) {
    // nothing here because an uneditable cell should not have this functionality
  }

  @Override
  public File setSaveFileChooser() {
    // nothing here because an uneditable cell should not have this functionality
    return null;
  }

  @Override
  public void render() {
    setVisible(true);
  }

  private void editTableCell(int col, int row, String value) {
    this.panel.editCellValue(col, row, value);
  }

}
