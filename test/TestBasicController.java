import org.junit.Test;

import java.util.HashMap;

import edu.cs3500.spreadsheets.controller.BasicController;
import edu.cs3500.spreadsheets.controller.Features;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.cell.CellGeneral;
import edu.cs3500.spreadsheets.model.content.Blank;
import edu.cs3500.spreadsheets.model.content.value.ValueDouble;
import edu.cs3500.spreadsheets.model.worksheet.BasicWorkSheet;
import edu.cs3500.spreadsheets.model.worksheet.Worksheet;
import edu.cs3500.spreadsheets.view.EditableView;
import edu.cs3500.spreadsheets.view.IView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * tests for methods in basic controller.
 */
public class TestBasicController {

  @Test
  public void testSaveAndChange() {
    Worksheet model = new BasicWorkSheet(new HashMap<Coord, CellGeneral>());
    boolean noSuchCellContents = false;
    model.evaluateAll();
    IView view = new EditableView("blank and editable", model);
    Features c = new BasicController(model, view);
    view.setTextFieldInput("8.0");
    view.setSelectedCell(1, 3);
    try {
      model.getOneCellRawContents(2, 4);
    } catch (IllegalArgumentException e) {
      noSuchCellContents = true;
    }
    assertTrue(noSuchCellContents);
    c.saveAndChange();
    assertEquals(new ValueDouble(8.0), model.getOneCellRawContents(2, 4));
  }

  //test to verify controller call right method and pass right argument to view and model
  @Test
  public void testSaveAndChangeUseMock() {
    StringBuilder log = new StringBuilder();
    Worksheet model = new MockModel(log);
    IView view = new MockView(log);
    Features c = new BasicController(model, view);
    c.saveAndChange();
    assertEquals("view has been called to add a features object:\n" +
            "Basic Controller with features: saveAndChange, RefuseAndReset, getFocusAction, " +
            "focusAndShow, showContentAbove, deleteAll\n" +
            "view has been called to give it current text field input \n" +
            "view has been called to get the selected cell's col\n" +
            "view has been called to get the selected cell's row\n" +
            "model being called to edit a cell at col: 1, row: 1 with content: \n" +
            "view has been called to remove the focus from a specific place\n" +
            "view has been called to store it's text field input\n", log.toString());
  }

  @Test
  public void testRefuseAndReset() {
    Worksheet model = new BasicWorkSheet(new HashMap<Coord, CellGeneral>());
    boolean noSuchCellContents = false;
    model.evaluateAll();
    IView view = new EditableView("blank and editable", model);
    Features c = new BasicController(model, view);
    view.setTextFieldInput("8.0");
    view.setSelectedCell(1, 3);
    try {
      model.getOneCellRawContents(2, 4);
    } catch (IllegalArgumentException e) {
      noSuchCellContents = true;
    }
    assertTrue(noSuchCellContents);
    c.refuseAndReset();
    assertTrue(noSuchCellContents);
    assertEquals("", view.getTextFieldInput());
  }

  //test to verify controller call right method and pass right argument to view and model
  @Test
  public void testRefuseAndResetUseMock() {
    StringBuilder log = new StringBuilder();
    Worksheet model = new MockModel(log);
    IView view = new MockView(log);
    Features c = new BasicController(model, view);
    c.refuseAndReset();
    assertEquals("view has been called to add a features object:\n" +
            "Basic Controller with features: saveAndChange, RefuseAndReset, getFocusAction," +
            " focusAndShow, showContentAbove, deleteAll\n" +
            "view has been called to set the text field input as what is inside the store\n" +
            "view has been called to remove the focus from a specific place\n", log.toString());
  }

  @Test
  public void testGetFocusAction() {
    Worksheet model = new BasicWorkSheet(new HashMap<Coord, CellGeneral>());
    model.evaluateAll();
    IView view = new EditableView("blank and editable", model);
    Features c = new BasicController(model, view);
    view.setTextFieldInput("8.0");
    view.resetTextField();
    assertEquals("", view.getTextFieldInput());

    view.setTextFieldInput("8.0");
    c.getFocusAction();
    view.resetTextField();
    assertEquals("8.0", view.getTextFieldInput());
  }


  //test to verify controller call right method and pass right argument to view and model
  @Test
  public void testFocusAction() {
    StringBuilder log = new StringBuilder();
    Worksheet model = new MockModel(log);
    IView view = new MockView(log);
    Features c = new BasicController(model, view);
    c.getFocusAction();
    assertEquals("view has been called to add a features object:\n" +
            "Basic Controller with features: saveAndChange, RefuseAndReset, getFocusAction, " +
            "focusAndShow, showContentAbove, deleteAll\n" +
            "view has been called to get the focus to a specific place\n" +
            "view has been called to store it's text field input\n", log.toString());
  }

  @Test
  public void testFocusAndShow() {
    Worksheet model = new BasicWorkSheet(new HashMap<Coord, CellGeneral>());
    boolean noSuchCellContents = false;
    model.evaluateAll();
    IView view = new EditableView("blank and editable", model);
    Features c = new BasicController(model, view);
    view.setTextFieldInput("8.0");
    view.setSelectedCell(1, 3);
    try {
      model.getOneCellRawContents(2, 4);
    } catch (IllegalArgumentException e) {
      noSuchCellContents = true;
    }
    assertTrue(noSuchCellContents);
    c.saveAndChange();
    assertEquals(new ValueDouble(8.0), model.getOneCellRawContents(2, 4));
    c.focusAndShow();
    assertEquals("8.0", view.getTextFieldInput());
  }

  //test to verify controller call right method and pass right argument to view and model
  @Test
  public void testFocusAndShowUseMock() {
    StringBuilder log = new StringBuilder();
    Worksheet model = new MockModel(log);
    IView view = new MockView(log);
    Features c = new BasicController(model, view);
    c.focusAndShow();
    assertEquals("view has been called to add a features object:\n" +
            "Basic Controller with features: saveAndChange, RefuseAndReset, " +
            "getFocusAction, focusAndShow, showContentAbove, deleteAll\n" +
            "view has been called to get the selected cell's col\n" +
            "view has been called to get the selected cell's row\n" +
            "model being called to get one cell raw content at col: 1, row: 1\n" +
            "model being called to get one cell raw content at col: 1, row: 1\n" +
            "view has been called to set  as it text field input\n" +
            "view has been called to get the focus to a specific place\n" +
            "view has been called to store it's text field input\n", log.toString());
  }

  //same behavior with focusAndShow regard "focus" thing. And focus is tested by mock.
  @Test
  public void testShowContentAboveShow() {
    Worksheet model = new BasicWorkSheet(new HashMap<Coord, CellGeneral>());
    boolean noSuchCellContents = false;
    model.evaluateAll();
    IView view = new EditableView("blank and editable", model);
    Features c = new BasicController(model, view);
    view.setTextFieldInput("8.0");
    view.setSelectedCell(1, 3);
    try {
      model.getOneCellRawContents(2, 4);
    } catch (IllegalArgumentException e) {
      noSuchCellContents = true;
    }
    assertTrue(noSuchCellContents);
    c.saveAndChange();
    assertEquals(new ValueDouble(8.0), model.getOneCellRawContents(2, 4));
    c.showContentAbove();
    assertEquals("8.0", view.getTextFieldInput());
  }

  //test to verify controller call right method and pass right argument to view and model
  @Test
  public void testShowContentAboveUseMock() {
    StringBuilder log = new StringBuilder();
    Worksheet model = new MockModel(log);
    IView view = new MockView(log);
    Features c = new BasicController(model, view);
    c.showContentAbove();
    assertEquals("view has been called to add a features object:\n" +
            "Basic Controller with features: saveAndChange, RefuseAndReset, " +
            "getFocusAction, focusAndShow, showContentAbove, deleteAll\n" +
            "view has been called to get the selected cell's col\n" +
            "view has been called to get the selected cell's row\n" +
            "model being called to get one cell raw content at col: 1, row: 1\n" +
            "model being called to get one cell raw content at col: 1, row: 1\n" +
            "view has been called to set  as it text field input\n", log.toString());
  }

  @Test
  public void testDeleteAll() {
    Worksheet model = new BasicWorkSheet(new HashMap<Coord, CellGeneral>());
    boolean noSuchCellContents = false;
    model.evaluateAll();
    IView view = new EditableView("blank and editable", model);
    Features c = new BasicController(model, view);
    view.setTextFieldInput("8.0");
    view.setSelectedCell(1, 3);
    try {
      model.getOneCellRawContents(2, 4);
    } catch (IllegalArgumentException e) {
      noSuchCellContents = true;
    }
    assertTrue(noSuchCellContents);
    c.saveAndChange();
    assertEquals(new ValueDouble(8.0), model.getOneCellRawContents(2, 4));
    noSuchCellContents = false;
    c.deleteAll();
    assertEquals(new Blank(), model.getOneCellRawContents(2, 4));
  }

  //test to verify controller call right method and pass right argument to view and model
  @Test
  public void testDeleteAllUseMock() {
    StringBuilder log = new StringBuilder();
    Worksheet model = new MockModel(log);
    IView view = new MockView(log);
    Features c = new BasicController(model, view);
    c.deleteAll();
    assertEquals("view has been called to add a features object:\n" +
            "Basic Controller with features: saveAndChange, RefuseAndReset, getFocusAction," +
            " focusAndShow, showContentAbove, deleteAll\n" +
            "view has been called to get the selected cell's col\n" +
            "view has been called to get the selected cell's row\n" +
            "model being called to edit a cell at col: 1, row: 1 with content: \n" +
            "view has been called to remove the focus from a specific place\n" +
            "view has been called to set  as it text field input\n", log.toString());
  }


  //test to verify controller call right method and pass right argument to view and model
  @Test
  public void testGoUseMock() {
    StringBuilder log = new StringBuilder();
    Worksheet model = new MockModel(log);
    IView view = new MockView(log);
    Features c = new BasicController(model, view);
    c.makeVisible();
    assertEquals("view has been called to add a features object:\n" +
            "Basic Controller with features: saveAndChange, RefuseAndReset, " +
            "getFocusAction, focusAndShow, showContentAbove, deleteAll\n" +
            "view has been called to render itself\n", log.toString());
  }

  //test to verify controller call right method and pass right argument to view and model
  @Test
  public void testOpenFileUseMock() {
    StringBuilder log = new StringBuilder();
    Worksheet model = new MockModel(log);
    IView view = new MockView(log);
    Features c = new BasicController(model, view);
    c.openFile();
    assertEquals("view has been called to add a features object:\n" +
            "Basic Controller with features: saveAndChange, RefuseAndReset, getFocusAction, " +
            "focusAndShow, showContentAbove, deleteAll\n" +
            "view has been called to open file", log.toString());
  }

  //test to verify controller call right method and pass right argument to view and model
  @Test
  public void testSaveFileUseMock() {
    StringBuilder log = new StringBuilder();
    Worksheet model = new MockModel(log);
    IView view = new MockView(log);
    Features c = new BasicController(model, view);
    c.saveFile();
    assertEquals("view has been called to add a features object:\n" +
            "Basic Controller with features: saveAndChange, RefuseAndReset, " +
            "getFocusAction, focusAndShow, showContentAbove, deleteAll\n" +
            "view has been called to save file", log.toString());
  }



}
