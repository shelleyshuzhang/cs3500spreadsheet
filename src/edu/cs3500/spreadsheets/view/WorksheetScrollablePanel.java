package edu.cs3500.spreadsheets.view;

import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import java.awt.Dimension;

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
  private Dimension viewportSize = new Dimension(40, 0);
  private static Color BACK_GROUND_COLOR = new Color(250, 250, 250);
  private static Color HEADER_GRID_COLOR = new Color(233, 233, 243);
  private static Color WORKSHEET_GRID_COLOR = Color.GRAY;
  private static Color WORKSHEET_FOREGROUND = Color.BLACK;
  private static Color WORKSHEET_SELECTED_BACKGROUND = new Color(187, 187, 187);

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
    headerTable.setCellSelectionEnabled(false);
  }
}