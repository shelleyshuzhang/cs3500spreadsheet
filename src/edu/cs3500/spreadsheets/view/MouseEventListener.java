package edu.cs3500.spreadsheets.view;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;


public class MouseEventListener extends MouseAdapter {
  Map<Integer, Runnable> actionMap;

  public void setMouseActionMap(Map<Integer, Runnable> map) {
    actionMap = map;
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    if (actionMap.containsKey(e.getClickCount())) {
      actionMap.get(e.getClickCount()).run();
    }
  }
}
