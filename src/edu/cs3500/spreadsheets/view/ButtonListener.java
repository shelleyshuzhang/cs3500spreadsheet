package edu.cs3500.spreadsheets.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

/**
 * A Button Listener implements Action Listener, contain a map which has the information about what
 * to do when heard specific event.
 */
public class ButtonListener implements ActionListener {
  private Map<String, Runnable> actionMap;

  /**
   * An Empty Default constructor for button listener.
   */
  public ButtonListener() {
    //empty default constructor
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (actionMap.containsKey(e.getActionCommand())) {
      actionMap.get(e.getActionCommand()).run();
    }
  }

  /**
   * A setter to set a event-whatToDo map for button clicked action event.
   *
   * @param map a event-whatToDo map
   */
  public void setButtonActionMap(Map<String, Runnable> map) {
    this.actionMap = map;
  }
}
