package edu.cs3500.spreadsheets.view;

import java.awt.*;

import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import static javax.swing.BorderFactory.createEmptyBorder;

/**
 * A worksheet scroll panel which can make a worksheet scrollable. It extends the JScrollPane in
 * java swing library and has two new public functions that can extend its columns and rows when
 * needed. The colors and size of the panel is personalized to make it look good.
 */
public class WorksheetScrollablePanel extends JScrollPane {
  /*
  The header table is a JTable and is used as the row header.
   */
  private JTable headerTable;
  private JTable worksheet;
  private Cursor otherCursor = DEFAULT_RESIZE_CURSOR;
  private Dimension viewportSize = new Dimension(40, 0);
  private static Color BACK_GROUND_COLOR = new Color(250, 250, 250);
  private static Color HEADER_GRID_COLOR = new Color(233, 233, 243);
  private static Color WORKSHEET_GRID_COLOR = Color.GRAY;
  private static Color WORKSHEET_FOREGROUND = Color.BLACK;
  private static Color WORKSHEET_SELECTED_BACKGROUND = new Color(182, 213, 212);
  private static Cursor DEFAULT_RESIZE_CURSOR = Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR);

  /**
   * Construct a WorksheetScrollablePanel with a table in it.
   *
   * @param table a JTable inside the scrollable panel, which contain contexts we want it to
   *              scroll.
   */
  public WorksheetScrollablePanel(JTable table) {
    super(table);
    worksheet = table;
    this.setWorksheet();
    this.setHeadTable();
    this.setBorder(createEmptyBorder());
    this.setRowHeaderView(headerTable);
    this.setBackground(BACK_GROUND_COLOR);
  }

  /**
   * Add a row to the existing table at the end of the table rows.
   */
  public void addRow() {
    DefaultTableModel d = (DefaultTableModel) this.worksheet.getModel();
    Object[] o = new Object[d.getColumnCount()];
    d.insertRow(d.getRowCount(), o);
  }

  /**
   * Add a column to the existing table at the end of the table columns.
   */
  public void addColumn() {
    DefaultTableModel d = (DefaultTableModel) this.worksheet.getModel();
    d.addColumn(d.getColumnName(d.getColumnCount()));
  }

  /**
   * Increase the width of the worksheet header viewport to 5/4 of the original size.
   */
  public void increaseWorksheetHeaderSize() {
    int width = this.viewportSize.width * 5 / 4;
    this.viewportSize = new Dimension(width, this.viewportSize.height);
  }

  /**
   * Edit the value of one existing cell in the table based on the given position in the table.
   *
   * @param col the column index for the cell in the table
   * @param row the row index for the cell in the table
   * @param value the new value of the cell
   */
  public void editCellValue(int col, int row, String value) {
    worksheet.setValueAt(value, row - 1, col - 1);
  }

  /**
   * get the row of the cell that is selected.
   *
   * @return the row of the cell being selected
   */
  public int getSelectedCellRow() {
    return worksheet.getSelectedRow();
  }

  /**
   * get the column of the cell that is selected.
   *
   * @return the column of the cell being selected
   */
  public int getSelectedCellColumn() {
    return worksheet.getSelectedColumn();
  }

  /**
   * get the list of columns that are selected.
   *
   * @return the array of the index of the columns that are selected
   */
  public int[] getSelectedColumns() {
    return worksheet.getSelectedColumns();
  }

  /**
   * get the list of rows that are selected.
   *
   * @return the array of the index of the rows that are selected
   */
  public int[] getSelectedRows() {
    return worksheet.getSelectedRows();
  }

  /**
   * add the mouse event listener to the panel.
   *
   * @param m the mouse event listener
   */
  public void addMouseEventListener(MouseListener m) {
    this.worksheet.addMouseListener(m);
  }

  /**
   * add the keyboard event listener to the panel.
   *
   * @param k the keyboard event listener
   */
  public void addKeyboardEventListener(KeyListener k) {
    this.worksheet.addKeyListener(k);
  }

  /**
   * set the selection of cell to the given row index and column index.
   *
   * @param col the column of the cell to be selected
   * @param row the row of the cell to be selected
   */
  public void setSelectedCell(int col, int row) {
    this.worksheet.setColumnSelectionInterval(col, col);
    this.worksheet.setRowSelectionInterval(row, row);
  }

  /**
   * get the row header of the worksheet.
   *
   * @return JTable represents the header of the worksheet
   */
  public JTable getRowHeaderTable() {
    return headerTable;
  }

  /**
   * get the width of a given cell.
   *
   * @param col the column of the cell
   * @return the width of the cell
   */
  public int getCellWidth(int col) {
    return worksheet.getTableHeader().getColumnModel().getColumn(col).getWidth();
  }

  /**
   * get the height of a cell.
   *
   * @param row the row of the cell
   * @return the height of the cell
   */
  public int getCellHeight(int row) {
    return worksheet.getRowHeight(row);
  }

  /**
   * set the width for a cell.
   *
   * @param col   the column of the cell
   * @param width the width of the cell to set to
   */
  public void setCellWidth(int col, int width) {
    worksheet.getTableHeader().getColumnModel().getColumn(col).setPreferredWidth(width);
  }

  /**
   * set the height of a cell.
   *
   * @param row the row of the cell
   * @param height the height of the cell
   */
  public void setCellHeight(int row, int height) {
    headerTable.setRowHeight(row, height);
    worksheet.setRowHeight(row, height);
  }

  /**
   * add mouse listen for the header table
   *
   * @param m the mouse event listener
   * @param l the mouse movement listener
   */
  public void addMouseListenerToRowHeader(MouseListener m, MouseMotionListener l) {
    this.headerTable.addMouseListener(m);
    this.headerTable.addMouseMotionListener(l);
  }

  /**
   * swap the cursor between resize and move.
   */
  public void swapRowHeaderCursor() {
    Cursor temporary = headerTable.getCursor();
    headerTable.setCursor(otherCursor);
    otherCursor = temporary;
  }

  /**
   * get the cursor from the row header table.
   *
   * @return the cursor of the row header table
   */
  public Cursor getRowHeaderCursor() {
    return headerTable.getCursor();
  }

  /**
   * get the row number of the given position.
   *
   * @param p the given position to get a row number
   * @return the row number
   */
  public int getRowAtResizePoint(Point p) {
    return this.getRowAtPointHelper(p, headerTable.rowAtPoint(p));
  }

  private int getRowAtPointHelper(Point p, int row) {
    int col = headerTable.columnAtPoint(p);
    if (row == -1 || col == -1) {
      return -1;
    }
    Rectangle r = headerTable.getCellRect(row, col, true);
    r.grow(0, -3);
    if (r.contains(p)) {
      return -1;
    }
    int midPoint = r.y + r.height / 2;
    if (p.y < midPoint) {
      return row - 1;
    } else {
      return row;
    }
  }

  private void setWorksheet() {
    worksheet.setGridColor(WORKSHEET_GRID_COLOR);
    worksheet.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    worksheet.setRowSelectionAllowed(true);
    worksheet.setColumnSelectionAllowed(true);
    worksheet.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    worksheet.setSelectionForeground(WORKSHEET_FOREGROUND);
    worksheet.setSelectionBackground(WORKSHEET_SELECTED_BACKGROUND);
  }

  private void setHeadTable() {
    headerTable = new JTable(worksheet.getRowCount(), 1) {
      private static final long serialVersionUID = 1L;

      public boolean isCellEditable(int row, int column) {
        return false;
      }
    };
    for (int i = 0; i < worksheet.getRowCount(); i++) {
      headerTable.setValueAt(i + 1, i, 0);
    }
    headerTable.setGridColor(HEADER_GRID_COLOR);
    headerTable.setBackground(BACK_GROUND_COLOR);
    headerTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    headerTable.setPreferredScrollableViewportSize(this.viewportSize);
    headerTable.setFocusable(false);
    headerTable.setRowSelectionAllowed(false);
  }
}