package edu.cs3500.spreadsheets.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class ButtonListener implements ActionListener {
  Map<String, Runnable> actionMap;

  @Override
  public void actionPerformed(ActionEvent e) {
    if (actionMap.containsKey(e.getActionCommand())) {
      actionMap.get(e.getActionCommand()).run();
    }
  }

  public void setButtonActionMap(Map<String, Runnable> map) {
    this.actionMap = map;
  }
}
