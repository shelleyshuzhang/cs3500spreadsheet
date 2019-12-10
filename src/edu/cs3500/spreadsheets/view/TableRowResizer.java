package edu.cs3500.spreadsheets.view;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;

import java.awt.*;
import java.awt.event.MouseEvent;

public class TableRowResizer extends MouseInputAdapter {
  private static Cursor resizeCursor = Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR);
  private int mouseYOffset, resizingRow;
  private Cursor otherCursor = resizeCursor;
  private JTable table;
  private JTable table1;

  public TableRowResizer(JTable table, JTable table1) {
    this.table = table;
    table.addMouseListener(this);
    table.addMouseMotionListener(this);
    this.table1 = table1;
    table1.addMouseListener(this);
    table1.addMouseMotionListener(this);
  }

  private int getResizingRow(Point p) {
    return getResizingRow(p, table.rowAtPoint(p));
  }

  private int getResizingRow(Point p, int row) {
    if (row == -1) {
      return -1;
    }
    int col = table.columnAtPoint(p);
    if (col == -1)
      return -1;
    Rectangle r = table.getCellRect(row, col, true);
    r.grow(0, -3);
    if (r.contains(p)) {
      return -1;
    }
    int midPoint = r.y + r.height / 2;
    return (p.y < midPoint) ? row - 1 : row;
  }

  public void mousePressed(MouseEvent e) {
    Point p = e.getPoint();
    resizingRow = getResizingRow(p);
    mouseYOffset = p.y - table.getRowHeight(resizingRow);
  }

  private void swapCursor() {
    Cursor tmp = table.getCursor();
    table.setCursor(otherCursor);
    otherCursor = tmp;
  }

  public void mouseMoved(MouseEvent e) {
    if ((getResizingRow(e.getPoint()) >= 0)
            != (table.getCursor() == resizeCursor)) {
      swapCursor();
    }
  }

  public void mouseDragged(MouseEvent e) {
    int mouseY = e.getY();
    if (resizingRow >= 0) {
      int newHeight = mouseY - mouseYOffset;
      if (newHeight > 0) {
        table.setRowHeight(resizingRow, newHeight);
        table1.setRowHeight(resizingRow, newHeight);
      }
    }
  }
}
