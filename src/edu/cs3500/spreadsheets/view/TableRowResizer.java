package edu.cs3500.spreadsheets.view;

import javax.swing.JTable;
import javax.swing.event.MouseInputAdapter;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Rectangle;
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

  public void mousePressed(MouseEvent e) {
    Point p = e.getPoint();
    resizingRow = getResizingRow(p);
    mouseYOffset = p.y - table.getRowHeight(resizingRow);
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
