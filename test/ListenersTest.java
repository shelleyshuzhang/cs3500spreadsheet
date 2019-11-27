import org.junit.Test;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

import edu.cs3500.spreadsheets.view.ButtonListener;
import edu.cs3500.spreadsheets.view.KeyboardListener;
import edu.cs3500.spreadsheets.view.MouseEventListener;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * test for listeners (mouse, keyboard, and buttons) works for editable view.
 */
public class ListenersTest {

  @Test
  public void testKeyBoardListenerSetMapAndCallRunnableCorrect() {
    // create a new listener
    KeyboardListener k = new KeyboardListener();
    // a field aim to store information to verify the run() method has been called correctly
    final String[] show = new String[1];
    // create three empty maps
    Map<Integer, Runnable> mapPress = new HashMap<>();
    Map<Integer, Runnable> mapRelease = new HashMap<>();
    Map<Character, Runnable> mapTyped = new HashMap<>();
    // set three empty maps to the listener
    k.setPressKeyMap(mapPress);
    k.setReleaseKeyMap(mapRelease);
    k.setTypedKeyMap(mapTyped);
    // mock the user press the key
    KeyEvent eventDeleteC = new KeyEvent(new Component() {
      // empty, just mock
    }, 1, 1, 1, KeyEvent.VK_DELETE, 'c');
    // nothing happened when key event happened
    k.keyPressed(eventDeleteC);
    assertNull(show[0]);
    k.keyTyped(eventDeleteC);
    assertNull(show[0]);
    k.keyReleased(eventDeleteC);
    assertNull(show[0]);
    // now put elements inside the map and set them to the listener again
    mapPress.put(KeyEvent.VK_DELETE, new Runnable() {
      @Override
      public void run() {
        show[0] = "the runnable corresponding to press VK_DELETE event has been called correctly";
      }
    });
    mapRelease.put(KeyEvent.VK_DELETE, new Runnable() {
      @Override
      public void run() {
        show[0] = "the runnable corresponding to release VK_DELETE event has been called correctly";
      }
    });
    mapTyped.put('c', new Runnable() {
      @Override
      public void run() {
        show[0] = "the runnable corresponding to type c event has been called correctly";
      }
    });
    k.setPressKeyMap(mapPress);
    k.setReleaseKeyMap(mapRelease);
    k.setTypedKeyMap(mapTyped);
    // correct runnable has been called. And setMap methods worked also.
    k.keyPressed(eventDeleteC);
    assertEquals("the runnable corresponding to press " +
            "VK_DELETE event has been called correctly", show[0]);
    k.keyTyped(eventDeleteC);
    assertEquals("the runnable corresponding to type c event " +
            "has been called correctly", show[0]);
    k.keyReleased(eventDeleteC);
    assertEquals("the runnable corresponding to release " +
            "VK_DELETE event has been called correctly", show[0]);
  }

  @Test
  public void testMouseEventListenerSetMapAndCallRunnableCorrect() {
    // create a new listener
    MouseEventListener m = new MouseEventListener();
    // a field aim to store information to verify the run() method has been called correctly
    final String[] show = new String[1];
    // create a empty map
    Map<Integer, Runnable> map = new HashMap<>();
    // set the empty map to the listener
    m.setMouseActionMap(map);
    // mock the user creating mouse event
    MouseEvent eventClickedOnce = new MouseEvent(new Component() {
      //empty, just mock
    }, 1, 1, 1, 1, 1, 1, true);
    MouseEvent eventClickedTwice = new MouseEvent(new Component() {
      //empty, just mock
    }, 1, 1, 1, 1, 1, 2, true);
    // nothing happened when key event happened
    m.mouseClicked(eventClickedOnce);
    assertNull(show[0]);
    m.mouseClicked(eventClickedTwice);
    assertNull(show[0]);
    // now put elements inside the map and set them to the listener again
    map.put(1, new Runnable() {
      @Override
      public void run() {
        show[0] = "the runnable corresponding to mouse clicked once" +
                " event has been called correctly";
      }
    });
    map.put(2, new Runnable() {
      @Override
      public void run() {
        show[0] = "the runnable corresponding to mouse clicked twice " +
                "event has been called correctly";
      }
    });
    m.setMouseActionMap(map);
    // correct runnable has been called. And setMap methods worked also.
    m.mouseClicked(eventClickedOnce);
    assertEquals("the runnable corresponding to mouse clicked once" +
            " event has been called correctly", show[0]);
    m.mouseClicked(eventClickedTwice);
    assertEquals("the runnable corresponding to mouse clicked twice " +
            "event has been called correctly", show[0]);
  }

  @Test
  public void testButtonListenerSetMapAndCallRunnableCorrect() {
    // create a new listener
    ButtonListener b = new ButtonListener();
    // a field aim to store information to verify the run() method has been called correctly
    final String[] show = new String[1];
    // create a empty map
    Map<String, Runnable> map = new HashMap<>();
    // set the empty map to the listener
    b.setButtonActionMap(map);
    // mock the user creating mouse event
    ActionEvent eventAccept = new ActionEvent(new Object(), 1, "accept edit");
    ActionEvent eventRefuse = new ActionEvent(new Object(), 1, "refuse edit");
    // nothing happened when key event happened
    b.actionPerformed(eventAccept);
    assertNull(show[0]);
    b.actionPerformed(eventRefuse);
    assertNull(show[0]);
    // now put elements inside the map and set them to the listener again
    map.put("accept edit", new Runnable() {
      @Override
      public void run() {
        show[0] = "the runnable corresponding to accept edit button clicked" +
                " event has been called correctly";
      }
    });
    map.put("refuse edit", new Runnable() {
      @Override
      public void run() {
        show[0] = "the runnable corresponding to refuse edit button clicked" +
                "event has been called correctly";
      }
    });
    b.setButtonActionMap(map);
    // correct runnable has been called. And setMap methods worked also.
    b.actionPerformed(eventAccept);
    assertEquals("the runnable corresponding to accept edit button clicked" +
            " event has been called correctly", show[0]);
    b.actionPerformed(eventRefuse);
    assertEquals("the runnable corresponding to refuse edit button clicked" +
            "event has been called correctly", show[0]);
  }


}
