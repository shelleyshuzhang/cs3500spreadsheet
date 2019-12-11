package edu.cs3500.spreadsheets.view;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * A mouse listener for the row header of the worksheet to get any resizable movement. The listener
 * will catch the move and drag events to decide when to change the cursor and how to resize a given
 * column or row, only save the information to model when user click save button.
 */
public class ResizeRowMouseListener extends MouseAdapter {
  IView view;
  private int yOffset;
  private int rowToResize;
  private static Cursor DEFAULT_RESIZE_CURSOR = Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR);

  /**
   * The constructor for this mouse listener to set a view inside it.
   *
   * @param view the view to take in
   */
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
