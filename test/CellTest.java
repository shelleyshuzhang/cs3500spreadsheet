import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.cs3500.spreadsheets.model.cell.Cell;
import edu.cs3500.spreadsheets.model.cell.CellGeneral;
import edu.cs3500.spreadsheets.model.content.Contents;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.content.formula.Formula;
import edu.cs3500.spreadsheets.model.content.formula.FormulaReference;
import edu.cs3500.spreadsheets.model.content.formula.FormulaValue;
import edu.cs3500.spreadsheets.model.content.value.Value;
import edu.cs3500.spreadsheets.model.content.value.ValueBoolean;
import edu.cs3500.spreadsheets.model.content.value.ValueDouble;
import edu.cs3500.spreadsheets.model.content.value.ValueString;

/**
 * The tests for the cell class, some of the needed tests are inside the BasicWorkSheetTest class,
 * so we only add some additional ones here.
 */
public class CellTest {

  /*
  @Test
          (expected = IllegalArgumentException.class)
  public void testEditToIndirectSelfRefer() {
    // create the first cell at coordinate (1, 1)
    ValueBoolean b1 = new ValueBoolean(false);
    Contents cc1 = new FormulaValue(b1);
    Coord co1 = new Coord(1, 1);
    CellGeneral c1 = new Cell(co1, cc1);

    // create the second cell that points to c1 at coordinate (1, 2)
    List<CellGeneral> reference1 = new ArrayList<CellGeneral>();
    reference1.add(c1);
    Coord co2 = new Coord(1, 2);
    Contents fr1 = new FormulaReference(reference1, co2);
    CellGeneral c2 = new Cell(co2, fr1);

    // create the third cell that will point to the second cell at coordinate (1, 3)
    List<CellGeneral> reference2 = new ArrayList<CellGeneral>();
    reference2.add(c2);
    Coord co3 = new Coord(1, 3);
    Contents fr2 = new FormulaReference(reference2, co3);
    CellGeneral c3 = new Cell(co3, fr2);

    // try to change the content of cell1 to point to cell3, which will eventually point to cell1
    // itself
    List<CellGeneral> reference3 = new ArrayList<CellGeneral>();
    reference3.add(c3);
    Contents fr3 = new FormulaReference(reference3, co1);
    c1.setContents(fr3, new HashMap<Coord, Value>());
  }

   */

  @Test
  public void getCoordinate() {
    Value v1 = new ValueString("l");
    Contents c1 = new FormulaValue(v1);
    Coord co2 = new Coord(7, 6);
    CellGeneral c = new Cell(co2, c1);
    Coord c3 = new Coord(7, 6);
    assertEquals(c.getCoordinate(), c3);
  }

  @Test
  public void getContents() {
    ValueDouble d1 = new ValueDouble(33.56);
    Contents cc1 = new FormulaValue(d1);
    Coord co1 = new Coord(1, 1);
    CellGeneral c1 = new Cell(co1, cc1);
    List<CellGeneral> reference = new ArrayList<CellGeneral>();
    reference.add(c1);
    Coord co2 = new Coord(7, 6);
    Contents fr1 = new FormulaReference(reference, co2);
    CellGeneral c2 = new Cell(co2, fr1);
    assertEquals(c2.getContents(), fr1);
  }

  /*
  @Test
  public void setContents() {
    ValueDouble d1 = new ValueDouble(33.56);
    Contents cc1 = new FormulaValue(d1);
    Coord co1 = new Coord(1, 1);
    CellGeneral c1 = new Cell(co1, cc1);
    List<CellGeneral> reference = new ArrayList<CellGeneral>();
    reference.add(c1);
    Coord co2 = new Coord(7, 6);
    Contents fr1 = new FormulaReference(reference, co2);
    CellGeneral c2 = new Cell(co2, fr1);
    c2.setContents(cc1, new HashMap<Coord, Value>());
    assertEquals(c2.getContents(), cc1);
  }


   */
  @Test
  public void containsReference() {
    ValueDouble d1 = new ValueDouble(33.56);
    Contents cc1 = new FormulaValue(d1);
    Coord co1 = new Coord(1, 1);
    CellGeneral c1 = new Cell(co1, cc1);
    List<CellGeneral> reference = new ArrayList<CellGeneral>();
    reference.add(c1);
    Coord co2 = new Coord(7, 6);
    Contents fr1 = new FormulaReference(reference, co2);
    CellGeneral c2 = new Cell(co2, fr1);
    assertTrue(c2.containsReference());
    assertFalse(c1.containsReference());
  }

  @Test
  public void getCellValue() {
    ValueDouble d1 = new ValueDouble(33.56);
    Contents cc1 = new FormulaValue(d1);
    Coord co1 = new Coord(1, 1);
    CellGeneral c1 = new Cell(co1, cc1);
    List<CellGeneral> reference = new ArrayList<CellGeneral>();
    reference.add(c1);
    Coord co2 = new Coord(7, 6);
    Contents fr1 = new FormulaReference(reference, co2);
    CellGeneral c2 = new Cell(co2, fr1);
    HashMap<Formula, Value> acc = new HashMap<>();
    c2.evaluate(acc);
    assertEquals(c2.getCellValue(), new ValueDouble(33.56));
  }

}