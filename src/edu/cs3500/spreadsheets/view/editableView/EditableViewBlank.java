package edu.cs3500.spreadsheets.view.editableView;

import java.awt.*;

import javax.swing.*;

import edu.cs3500.spreadsheets.view.IView;
import edu.cs3500.spreadsheets.view.WorksheetScrollablePanel;

public class EditableViewBlank extends JFrame implements IView {
  protected WorksheetScrollablePanel panel;
  private JToolBar toolBar;
  private static Color FRAME_BACKGROUND = new Color(233, 233, 243);
  private static int VIEW_LOCATION_X = 500;
  private static int VIEW_LOCATION_Y = 500;
  private static Dimension VIEW_MIN_SIZE = new Dimension(500, 500);
  private static int TICK_INDEX = 10004;
  private static int CROSS_INDEX = 10006;
  private static String SIGN_TICK = Character.toString((char) TICK_INDEX);
  private static String SIGN_CROSS = Character.toString((char) CROSS_INDEX);

  public EditableViewBlank(String caption, int row, int col) {
    super(caption);
    this.setLocation(VIEW_LOCATION_X, VIEW_LOCATION_Y);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setResizable(true);
    this.setMinimumSize(VIEW_MIN_SIZE);
    this.setLayout(new BorderLayout());
    this.toolBar = new JToolBar();
    this.toolBar.add(new JButton(SIGN_TICK));
    this.toolBar.add(new JButton(SIGN_CROSS));
    this.toolBar.add(new TextField());
    this.toolBar.setLayout(new BoxLayout(this.toolBar, BoxLayout.X_AXIS));
    this.add(toolBar, BorderLayout.NORTH);
    this.panel = new WorksheetScrollablePanel(new JTable(row, col) {
      private static final long serialVersionUID = 1L;

      public boolean isCellEditable(int row, int column) {
        return false;
      }
    });
    this.add(this.panel, BorderLayout.CENTER);
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
  public void render() {
    setVisible(true);
  }

  @Override
  public void editCell(int col, int row, String value) {
    this.panel.editCellValue(col, row, value);
  }
}
