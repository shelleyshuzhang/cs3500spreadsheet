package edu.cs3500.spreadsheets.view.editableView;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

import javax.swing.*;

import edu.cs3500.spreadsheets.view.IView;
import edu.cs3500.spreadsheets.view.WorksheetScrollablePanel;

public class EditableViewBlank extends JFrame implements IView {
  protected WorksheetScrollablePanel panel;
  private JToolBar toolBar;
  private TextField textField;
  JButton tick;
  JButton cross;
  private String store;
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
    this.tick = new JButton(SIGN_TICK);
    this.tick.setActionCommand("accept edit");
    this.cross = new JButton(SIGN_CROSS);
    this.cross.setActionCommand("refuse edit");
    this.toolBar.add(this.tick);
    this.toolBar.add(this.cross);
    this.textField = new TextField();
    this.toolBar.add(textField);
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

  @Override
  public String getTextFieldInput() {
    return this.textField.getText();
  }

  @Override
  public void clearTextField() {
    this.textField.setText("");
  }

  @Override
  public void resetFocus() {
    this.setFocusable(true);
    this.requestFocus();
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
    this.textField.setFocusable(false);
  }

  @Override
  public void getFocus() {
    this.textField.setFocusable(true);
    this.textField.requestFocus();
  }

  @Override
  public void addActionListener(ActionListener ac) {
    this.tick.addActionListener(ac);
    this.cross.addActionListener(ac);
  }

  @Override
  public void addMouseEventListener(MouseListener textField, MouseListener cells) {
    this.textField.addMouseListener(textField);
    this.panel.addMouseEventListener(cells);
  }

  @Override
  public void setTextFieldInput(String s) {
    this.textField.setText(s);
  }

  @Override
  public void storeTextFieldInput() {
    this.store = this.getTextFieldInput();
  }

  @Override
  public void resetTextField() {
    this.textField.setText(this.store);
  }

}
