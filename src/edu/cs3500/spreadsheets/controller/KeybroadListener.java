package edu.cs3500.spreadsheets.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Map;

public class KeybroadListener implements KeyListener {
  Map<Integer, Runnable> mapPress;
  Map<Integer, Runnable> mapRelease;
  Map<Character, Runnable> mapTyped;

  public void setPressKeyMap(Map<Integer, Runnable> mapPress) {
    this.mapPress = mapPress;
  }

  public void setReleaseKeyMap(Map<Integer, Runnable> mapRelease) {
    this.mapRelease = mapRelease;
  }

  public void setTypedKeyMap(Map<Character, Runnable> mapTyped) {
    this.mapTyped = mapTyped;
  }

  @Override
  public void keyTyped(KeyEvent e) {
    if (mapTyped.containsKey(e.getKeyChar()))
      mapTyped.get(e.getKeyChar()).run();
  }

  @Override
  public void keyPressed(KeyEvent e) {
    if (mapPress.containsKey(e.getKeyCode()))
      mapPress.get(e.getKeyCode()).run();
  }

  @Override
  public void keyReleased(KeyEvent e) {
    if (mapRelease.containsKey(e.getKeyCode()))
      mapRelease.get(e.getKeyCode()).run();
  }
}
