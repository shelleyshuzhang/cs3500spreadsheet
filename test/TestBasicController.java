import org.junit.Test;

import java.util.HashMap;

import edu.cs3500.spreadsheets.controller.BasicController;
import edu.cs3500.spreadsheets.controller.Features;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.cell.CellGeneral;
import edu.cs3500.spreadsheets.model.content.value.ValueDouble;
import edu.cs3500.spreadsheets.model.worksheet.BasicWorkSheet;
import edu.cs3500.spreadsheets.model.worksheet.Worksheet;
import edu.cs3500.spreadsheets.view.EditableView;
import edu.cs3500.spreadsheets.view.IView;

import static org.junit.Assert.assertEquals;

/**
 * tests for methods in basic controller.
 */
public class TestBasicController {

  @Test
  public void testSaveAndChange() {
    Worksheet model = new BasicWorkSheet(new HashMap<Coord, CellGeneral>());
    model.evaluateAll();
    IView view = new EditableView("blank and editable", model);
    Features c = new BasicController(model, view);
    view.setTextFieldInput("8.0");
    view.setStorePosition(1, 3);

    c.saveAndChange();

    assertEquals(new ValueDouble(8.0), model.getOneCellRawContents(2, 4));
  }

}
