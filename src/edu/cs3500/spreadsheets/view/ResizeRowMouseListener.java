package edu.cs3500.spreadsheets.view;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ResizeRowMouseListener extends MouseAdapter {
  IView view;
  private int yOffset;
  private int rowToResize;
  private static Cursor DEFAULT_RESIZE_CURSOR = Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR);

  public ResizeRowMouseListener(IView view) {
    this.view = view;
  }

  @Override
  public void mousePressed(MouseEvent e) {
    Point p = e.getPoint();
    rowToResize = view.getRowToResize(p);
    yOffset = p.y - view.getCellHeight(0, rowToResize);
  }

  @Override
  public void mouseMoved(MouseEvent e) {
    if ((view.getRowToResize(e.getPoint()) >= 0)
            != (view.getRowHeaderCursor() == DEFAULT_RESIZE_CURSOR)) {
      view.swapRowHeaderCursor();
    }
  }

  @Override
  public void mouseDragged(MouseEvent e) {
    if (rowToResize >= 0) {
      int heightToChange = e.getY() - yOffset;
      if (heightToChange > 0) {
        view.setCellHeight(rowToResize, heightToChange);
      }
    }
  }
}
