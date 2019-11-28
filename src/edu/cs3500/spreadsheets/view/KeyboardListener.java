package edu.cs3500.spreadsheets.view;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Map;

/**
 * a key board listener implements KeyListener, contain three maps which has information about what
 * to do when specific events happened.
 */
public class KeyboardListener implements KeyListener {
  private Map<Integer, Runnable> mapPress;
  private Map<Integer, Runnable> mapRelease;
  private Map<Character, Runnable> mapTyped;

  /**
   * An Empty Default constructor for key board listener.
   */
  public KeyboardListener() {
    //empty default constructor
  }

  /**
   * A setter to set a event-whatToDo map for key pressed event.
   *
   * @param mapPress a event-whatToDo map
   */
  public void setPressKeyMap(Map<Integer, Runnable> mapPress) {
    this.mapPress = mapPress;
  }

  /**
   * A setter to set a event-whatToDo map for key released event.
   * @param mapRelease a event-whatToDo map
   */
  public void setReleaseKeyMap(Map<Integer, Runnable> mapRelease) {
    this.mapRelease = mapRelease;
  }

  /**
   * A setter to set a event-whatToDo map for key typed event.
   * @param mapTyped a event-whatToDo map
   */
  public void setTypedKeyMap(Map<Character, Runnable> mapTyped) {
    this.mapTyped = mapTyped;
  }

  @Override
  public void keyTyped(KeyEvent e) {
    if (mapTyped.containsKey(e.getKeyChar())) {
      mapTyped.get(e.getKeyChar()).run();
    }
  }

  @Override
  public void keyPressed(KeyEvent e) {
    if (mapPress.containsKey(e.getKeyCode())) {
      mapPress.get(e.getKeyCode()).run();
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {
    if (mapRelease.containsKey(e.getKeyCode())) {
      mapRelease.get(e.getKeyCode()).run();
    }
  }
}
