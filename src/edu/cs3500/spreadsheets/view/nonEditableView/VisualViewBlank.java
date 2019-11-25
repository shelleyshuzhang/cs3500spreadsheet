package edu.cs3500.spreadsheets.view.nonEditableView;

import java.awt.Dimension;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JTable;

import edu.cs3500.spreadsheets.view.IView;
import edu.cs3500.spreadsheets.view.WorksheetScrollablePanel;


/**
 * A visual view class for worksheet which can produce an empty worksheet, the blank worksheet can
 * scrolling. It extends JFrame class in java swing library and implements IVew interface (interface
 * for all views for worksheet).
 */
public class VisualViewBlank extends JFrame implements IView {
  protected WorksheetScrollablePanel panel;
  private static Color FRAME_BACKGROUND = new Color(233, 233, 243);
  private static int VIEW_LOCATION_X = 500;
  private static int VIEW_LOCATION_Y = 500;
  private static Dimension VIEW_MIN_SIZE = new Dimension(500, 500);

  /**
   * A constructor for VisualViewBlank, construct a blank visual worksheet with desired size.
   *
   * @param caption the given title for JFrame
   * @param row     given row for how many rows are wanted
   * @param col     given column for how many columns are wanted
   */
  public VisualViewBlank(String caption, int row, int col) {
    super(caption);
    this.setLocation(VIEW_LOCATION_X, VIEW_LOCATION_Y);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setResizable(true);
    this.setMinimumSize(VIEW_MIN_SIZE);
    this.panel = new WorksheetScrollablePanel(new JTable(row, col) {
      private static final long serialVersionUID = 1L;

      public boolean isCellEditable(int row, int column) {
        return false;
      }
    });
    this.add(this.panel);
    this.setBackground(FRAME_BACKGROUND);
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
  public void render() {
    setVisible(true);
  }

}
