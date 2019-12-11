package edu.cs3500.spreadsheets.view;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;

/**
 * a mouse event listener extends mouse adapter (so also implements mouse listener), contain a map
 * which has information about what to do when some specific events happened.
 */
public class MouseEventListener extends MouseAdapter {
  private Map<Integer, Runnable> actionMap;

  /**
   * An Empty Default constructor for mouse event listener.
   */
  public MouseEventListener() {
    //empty default constructor
  }

  /**
   * A setter to set a event-whatToDo map for mouse action event.
   *
   * @param map a event-whatToDo map
   */
  public void setMouseActionMap(Map<Integer, Runnable> map) {
    actionMap = map;
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    if (actionMap.containsKey(e.getClickCount())) {
      actionMap.get(e.getClickCount()).run();
    }
  }

  @Override
  public void mousePressed(MouseEvent e) {
    if (actionMap.containsKey(3)) {
      actionMap.get(3).run();
    }
  }

  @Override
  public void mouseMoved(MouseEvent e) {
    if (actionMap.containsKey(4)) {
      actionMap.get(4).run();
    }
  }

  @Override
  public void mouseDragged(MouseEvent e) {
    if (actionMap.containsKey(5)) {
      actionMap.get(5).run();
    }
  }

}
