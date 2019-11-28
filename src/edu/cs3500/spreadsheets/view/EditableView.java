package edu.cs3500.spreadsheets.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.TextField;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.JToolBar;

import edu.cs3500.spreadsheets.controller.Features;
import edu.cs3500.spreadsheets.model.worksheet.WorksheetReadOnly;
import edu.cs3500.spreadsheets.sexp.SexpVisitorFormula;

/**
 * An editable view extends JFrame implements IView. It's a editable version of visual view, take
 * the same element as a visual view excepts that it has a text field for editing a specific cell
 * and tow buttons for either confirm the change or reject it. User can also press the delete key
 * to delete the content of a cell. There is one text field for showing the formula of a cell and
 * edit it, a button for confirming changes, a button for refusing changes, a button for saving
 * the file, and another button for open a file. You can select a cell by single click the mouse,
 * move the selection to neighbor cells by pressing the keyboard, and double click a cell or click
 * the text field when a cell is selected to edit the cell.
 */
public class EditableView extends JFrame implements IView {
  protected WorksheetScrollablePanel panel;
  private JToolBar toolBar;
  private TextField textField;
  private JButton tick;
  private JButton cross;
  private JButton saveButton;
  private JButton openButton;
  private String store;
  private List<Features> featuresListener = new ArrayList<>();
  private static Color FRAME_BACKGROUND = new Color(233, 233, 243);
  private static int VIEW_LOCATION_X = 500;
  private static int VIEW_LOCATION_Y = 500;
  private static Dimension VIEW_MIN_SIZE = new Dimension(500, 500);
  private static int TICK_INDEX = 10004;
  private static int CROSS_INDEX = 10006;
  private static String SIGN_TICK = Character.toString((char) TICK_INDEX);
  private static String SIGN_CROSS = Character.toString((char) CROSS_INDEX);
  private static int DEFAULT_ROW = 1000;
  private static int DEFAULT_COL = 1000;

  /**
   * The constructor for the editable view. The same as non editable view, it takes a string which
   * is the caption of the view and a read-only model, which provides needed read only information
   * for the view
   *
   * @param caption the caption of the view window
   * @param worksheetReadOnly the read-only model it takes to get needed information of the
   *                          spreadsheet in order to show to users
   */
  public EditableView(String caption, WorksheetReadOnly worksheetReadOnly) {
    super(caption);
    this.setLocation(VIEW_LOCATION_X, VIEW_LOCATION_Y);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setResizable(true);
    this.setMinimumSize(VIEW_MIN_SIZE);
    this.setLayout(new BorderLayout());
    this.toolBar = new JToolBar();
    this.tick = new JButton(SIGN_TICK);
    this.tick.setActionCommand("accept edit");
    this.cross = new JButton(SIGN_CROSS);
    this.cross.setActionCommand("refuse edit");
    this.toolBar.add(this.tick);
    this.toolBar.add(this.cross);
    this.textField = new TextField();
    this.toolBar.add(textField);
    this.openButton = new JButton("open");
    this.openButton.setActionCommand("open file");
    this.saveButton = new JButton("save");
    this.saveButton.setActionCommand("save file");
    this.toolBar.add(this.openButton);
    this.toolBar.add(this.saveButton);
    this.toolBar.setLayout(new BoxLayout(this.toolBar, BoxLayout.X_AXIS));
    this.store = "";
    this.add(toolBar, BorderLayout.NORTH);
    this.panel = new WorksheetScrollablePanel(new JTable(DEFAULT_ROW, DEFAULT_COL) {
      private static final long serialVersionUID = 1L;

      public boolean isCellEditable(int row, int column) {
        return false;
      }
    });
    this.add(this.panel, BorderLayout.CENTER);
    this.setBackground(FRAME_BACKGROUND);
    setTableValues(worksheetReadOnly, this.panel, this);
    pack();
    setButtonListener();
    setMouseListener();
    setKeyBoardListener();
  }

  @Override
  public void addColumn() {
    this.panel.addColumn();
    this.repaint();
  }

  @Override
  public void addRow() {
    this.panel.addRow();
    this.repaint();
  }

  @Override
  public void increaseRowHeaderWidth() {
    this.panel.increaseWorksheetHeaderSize();
  }

  @Override
  public void render() {
    setVisible(true);
  }

  @Override
  public void editCell(int col, int row, String value) {
    this.panel.editCellValue(col, row, value);
  }

  @Override
  public String getTextFieldInput() {
    return this.textField.getText();
  }

  @Override
  public void clearTextField() {
    this.textField.setText("");
  }

  @Override
  public void resetFocus() {
    this.setFocusable(true);
    this.requestFocus();
  }

  @Override
  public int getSelectedCellRow() {
    return this.panel.getSelectedCellRow();
  }

  @Override
  public int getSelectedCellCol() {
    return this.panel.getSelectedCellColumn();
  }

  @Override
  public void removeFocus() {
    this.textField.setFocusable(false);
  }

  @Override
  public void getFocus() {
    this.textField.setFocusable(true);
    this.textField.requestFocus();
  }

  @Override
  public void addActionListener(ActionListener ac) {
    this.tick.addActionListener(ac);
    this.cross.addActionListener(ac);
    this.saveButton.addActionListener(ac);
    this.openButton.addActionListener(ac);
  }

  @Override
  public void addMouseEventListener(MouseListener textField, MouseListener cells) {
    this.textField.addMouseListener(textField);
    this.panel.addMouseEventListener(cells);
  }

  @Override
  public void setTextFieldInput(String s) {
    this.textField.setText(s);
  }

  @Override
  public void storeTextFieldInput() {
    this.store = this.getTextFieldInput();
  }

  @Override
  public void resetTextField() {
    this.textField.setText(this.store);
  }

  @Override
  public void addKeyboardListener(KeyListener k) {
    this.panel.addKeyboardEventListener(k);
  }

  @Override
  public int[] getSelectedColumns() {
    return this.panel.getSelectedColumns();
  }

  @Override
  public int[] getSelectedRows() {
    return this.panel.getSelectedRows();
  }

  @Override
  public void addFeatures(Features features) {
    this.featuresListener.add(features);
  }

  @Override
  public void setSelectedCell(int col, int row) {
    this.panel.setSelectedCell(col, row);
  }

  @Override
  public File setSaveFileChooser() {
    File f;
    JFileChooser save = new JFileChooser();
    if (save.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
      f = save.getSelectedFile();
    } else {
      f = null;
    }
    return f;
  }

  @Override
  public File setOpenFileChooser() {
    File f;
    JFileChooser open = new JFileChooser();
    if (open.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
      f = open.getSelectedFile();
    } else {
      f = null;
    }
    return f;
  }

  protected static void setTableValues(WorksheetReadOnly worksheetReadOnly,
                                       WorksheetScrollablePanel panel, IView view) {
    Set<String> coords = worksheetReadOnly.getAllCellCoordinates();
    for (String s : coords) {
      int[] coord = SexpVisitorFormula.getSingleRefer(s);
      int col = coord[0];
      int row = coord[1];
      if (col >= 1000 || row >= 1000) {
        while (col >= 1000) {
          panel.addColumn();
          col--;
        }
        while (row >= 1000) {
          panel.addRow();
          row--;
        }
      }
      String value;
      try {
        value = worksheetReadOnly.getOneCellResult(col, row).print();
      } catch (IllegalArgumentException e) {
        value = e.getMessage();
      }
      view.editCell(col, row, value);
    }
  }

  private void setButtonListener() {
    Map<String, Runnable> buttonClickedMap = new HashMap<String, Runnable>();
    ButtonListener bListener = new ButtonListener();

    buttonClickedMap.put("accept edit", new Runnable() {
      @Override
      public void run() {
        for (Features f : featuresListener)
          f.saveAndChange();
      }
    });
    buttonClickedMap.put("refuse edit", new Runnable() {
      @Override
      public void run() {
        for (Features f : featuresListener)
          f.refuseAndReset();
      }
    });
    buttonClickedMap.put("save file", new Runnable() {
      @Override
      public void run() {
        for (Features f : featuresListener)
          f.saveFile();
      }
    });
    buttonClickedMap.put("open file", new Runnable() {
      @Override
      public void run() {
        for (Features f : featuresListener)
          f.openFile();
      }
    });

    bListener.setButtonActionMap(buttonClickedMap);
    this.addActionListener(bListener);
  }

  private void setMouseListener() {
    Map<Integer, Runnable> MouseMapTextField = new HashMap<Integer, Runnable>();
    Map<Integer, Runnable> MouseMapCells = new HashMap<Integer, Runnable>();
    MouseEventListener mListenerTextField = new MouseEventListener();
    MouseEventListener mListenerCells = new MouseEventListener();

    MouseMapTextField.put(1, new Runnable() {
      @Override
      public void run() {
        for (Features f : featuresListener)
          f.getFocusAction();
      }
    });
    MouseMapCells.put(2, new Runnable() {
      @Override
      public void run() {
        for (Features f : featuresListener)
          f.focusAndShow();
      }
    });
    MouseMapCells.put(1, new Runnable() {
      @Override
      public void run() {
        for (Features f : featuresListener)
          f.showContentAbove();
      }
    });

    mListenerTextField.setMouseActionMap(MouseMapTextField);
    mListenerCells.setMouseActionMap(MouseMapCells);
    this.addMouseEventListener(mListenerTextField, mListenerCells);
  }

  private void setKeyBoardListener() {
    Map<Integer, Runnable> keyMapPress = new HashMap<>();
    Map<Integer, Runnable> keyMapRelease = new HashMap<>();
    Map<Character, Runnable> keyMapTyped = new HashMap<>();

    keyMapPress.put(KeyEvent.VK_DELETE, new Runnable() {
      @Override
      public void run() {
        for (Features f : featuresListener)
          f.deleteAll();
      }
    });
    keyMapPress.put(KeyEvent.VK_BACK_SPACE, new Runnable() {
      @Override
      public void run() {
        for (Features f : featuresListener)
          f.deleteAll();
      }
    });

    KeyboardListener kl = new KeyboardListener();
    kl.setPressKeyMap(keyMapPress);
    kl.setReleaseKeyMap(keyMapRelease);
    kl.setTypedKeyMap(keyMapTyped);

    this.addKeyboardListener(kl);
  }

}
