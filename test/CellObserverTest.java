import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.cell.Cell;
import edu.cs3500.spreadsheets.model.cell.CellGeneral;
import edu.cs3500.spreadsheets.model.cell.CellObserver;
import edu.cs3500.spreadsheets.model.content.Contents;
import edu.cs3500.spreadsheets.model.content.formula.Formula;
import edu.cs3500.spreadsheets.model.content.formula.FormulaReference;
import edu.cs3500.spreadsheets.model.content.formula.FunctionSum;
import edu.cs3500.spreadsheets.model.content.value.Value;
import edu.cs3500.spreadsheets.model.content.value.ValueDouble;

/**
 * The tests for the cell observer for get coordinate and update cascade behavior.
 */
public class CellObserverTest {

  @Test
  public void testUpdateCascade() {
    List<CellGeneral> loc = new ArrayList<>();
    Contents c1 = new ValueDouble(10);
    Coord coordinate1 = new Coord(1, 1);
    CellGeneral cell1 = new Cell(coordinate1, c1);
    Contents c2 = new ValueDouble(20);
    Coord coordinate2 = new Coord(2, 1);
    CellGeneral cell2 = new Cell(coordinate1, c2);
    loc.add(cell1);
    loc.add(cell2);
    Coord coordinate3 = new Coord(1, 2);
    List<Formula> lof = new ArrayList<>();
    lof.add(new FormulaReference(loc, coordinate3));
    Contents c3 = new FunctionSum(lof);
    CellGeneral cell3 = new Cell(coordinate3, c3);
    HashMap<Coord, Value> allEva = new HashMap<>();
    allEva.put(coordinate1, cell1.evaluate(new HashMap<Formula, Value>()));
    allEva.put(coordinate2, cell2.evaluate(new HashMap<Formula, Value>()));
    allEva.put(coordinate3, cell3.evaluate(new HashMap<Formula, Value>()));
    cell1.setContents(new ValueDouble(20), allEva);
    assertEquals(cell1.evaluate(new HashMap<Formula, Value>()), new ValueDouble(20));
    assertEquals(cell3.evaluate(new HashMap<Formula, Value>()), new ValueDouble(40));
  }

  @Test
  public void testGetCoordinate() {
    Contents c1 = new ValueDouble(10);
    Coord coordinate1 = new Coord(1, 1);
    CellGeneral cell1 = new Cell(coordinate1, c1);
    CellObserver observer = new CellObserver(cell1);
    assertEquals(observer.getCoordinate(), coordinate1);
  }
}