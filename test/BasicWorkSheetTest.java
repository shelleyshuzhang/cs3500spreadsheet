import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import edu.cs3500.spreadsheets.model.WorksheetReader;
import edu.cs3500.spreadsheets.model.worksheet.BasicWorkSheet;
import edu.cs3500.spreadsheets.model.BasicWorkSheetBuilder;
import edu.cs3500.spreadsheets.model.content.Blank;
import edu.cs3500.spreadsheets.model.cell.Cell;
import edu.cs3500.spreadsheets.model.cell.CellGeneral;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.content.formula.Formula;
import edu.cs3500.spreadsheets.model.content.formula.FormulaReference;
import edu.cs3500.spreadsheets.model.content.formula.FormulaValue;
import edu.cs3500.spreadsheets.model.content.formula.FunctionLessThan;
import edu.cs3500.spreadsheets.model.content.formula.FunctionProduct;
import edu.cs3500.spreadsheets.model.content.formula.FunctionStringAppend;
import edu.cs3500.spreadsheets.model.content.formula.FunctionSum;
import edu.cs3500.spreadsheets.model.content.value.ValueBoolean;
import edu.cs3500.spreadsheets.model.content.Contents;
import edu.cs3500.spreadsheets.model.content.value.ValueDouble;
import edu.cs3500.spreadsheets.model.content.value.ValueString;
import edu.cs3500.spreadsheets.model.worksheet.Worksheet;


/**
 * Test class for the basic worksheet. Also test any related methods.
 */
public class BasicWorkSheetTest {

  @Test
  public void testToStringArrayList() {
    StringBuilder b = new StringBuilder();
    b.append('a');
    b.append('b');
    b.append('c');
    assertEquals("abc", b.toString());
  }

  //through builder
  @Test
  public void testCreateAnEmptyWorkSheet() {
    BasicWorkSheetBuilder builder = new BasicWorkSheetBuilder();
    Worksheet model = builder.createWorksheet();
    assertEquals(0, model.getWorkSheetSize());
  }

  //through constructor
  @Test
  public void testCreateAnEmptyWorkSheet2() {
    HashMap<Coord, CellGeneral> cells = new HashMap<>();
    Worksheet model = new BasicWorkSheet(cells);
    assertEquals(0, model.getWorkSheetSize());
  }

  //the tests to check that we successfully build all the cells as we want
  @Test
  public void testAllCellInFactThere() {
    BasicWorkSheetBuilder builder = new BasicWorkSheetBuilder();
    builder.createCell(1, 1, "sakdjeed");
    builder.createCell(2, 2, "8.0");
    builder.createCell(3, 3, "true");
    Worksheet model = builder.createWorksheet();
    model.evaluateAll();
    assertEquals(3, model.getWorkSheetSize());
    assertEquals(new ValueString("sakdjeed").print(),
            model.getOneCellResult(1, 1).print());
    assertEquals(new ValueDouble(8.0).print(), model.getOneCellResult(2, 2).print());
    assertEquals(new ValueBoolean(true).print(), model.getOneCellResult(3, 3).print());
  }

  //we will throw exception for try to call getOneCellResult for a blank cell: no result
  @Test
  public void testForBlankCell() {
    BasicWorkSheetBuilder builder = new BasicWorkSheetBuilder();
    builder.createCell(1, 1, null);
    Worksheet model = builder.createWorksheet();
    Contents c = model.getOneCellRawContents(1, 1);
    Contents toCheckBlank = new Blank();
    assertEquals(toCheckBlank, c);
    assertEquals(1, model.getWorkSheetSize());
    model.evaluateAll();
  }

  @Test
  public void testForNumericValue() {
    BasicWorkSheetBuilder builder = new BasicWorkSheetBuilder();
    builder.createCell(1, 1, "8.0");
    Worksheet model = builder.createWorksheet();
    model.evaluateAll();
    assertEquals(new ValueDouble(8.0), model.getOneCellResult(1, 1));
    assertEquals(new ValueDouble(8.0), model.getOneCellRawContents(1, 1));
    assertEquals(1, model.getWorkSheetSize());
    assertEquals("8.000000", model.getOneCellResult(1, 1).print());
  }

  @Test
  public void testForBooleanValue() {
    BasicWorkSheetBuilder builder = new BasicWorkSheetBuilder();
    builder.createCell(1, 1, "true");
    Worksheet model = builder.createWorksheet();
    model.evaluateAll();
    assertEquals(new ValueBoolean(true), model.getOneCellResult(1, 1));
    assertEquals(new ValueBoolean(true), model.getOneCellRawContents(1, 1));
    assertEquals(1, model.getWorkSheetSize());
    assertEquals("true", model.getOneCellResult(1, 1).print());
  }

  @Test
  public void testForStringValue() {
    BasicWorkSheetBuilder builder = new BasicWorkSheetBuilder();
    builder.createCell(1, 1, "hdllhwuqCndx");
    Worksheet model = builder.createWorksheet();
    model.evaluateAll();
    assertEquals(new ValueString("hdllhwuqCndx"), model.getOneCellResult(1, 1));
    assertEquals(new ValueString("hdllhwuqCndx"), model.getOneCellRawContents(1, 1));
    assertEquals(1, model.getWorkSheetSize());
    assertEquals("\"hdllhwuqCndx\"", model.getOneCellResult(1, 1).print());
  }

  @Test
  public void testForFormulaAll() {
    BasicWorkSheetBuilder builder = new BasicWorkSheetBuilder();
    builder.createCell(1, 1, "3.0");
    builder.createCell(1, 2, "=3.0");
    Worksheet model = builder.createWorksheet();
    model.evaluateAll();
    assertEquals(new ValueDouble(3.0), model.getOneCellResult(1, 1));
    assertEquals(new ValueDouble(3.0), model.getOneCellResult(1, 2));
    assertEquals(new ValueDouble(3.0), model.getOneCellRawContents(1, 1));
    assertEquals(new FormulaValue(new ValueDouble(3.0)),
            model.getOneCellRawContents(1, 2));
    assertEquals(2, model.getWorkSheetSize());
    assertEquals("3.000000", model.getOneCellResult(1, 1).print());
    assertEquals("3.000000", model.getOneCellResult(1, 2).print());
  }

  @Test
  public void testForFormulaValue() {
    BasicWorkSheetBuilder builder = new BasicWorkSheetBuilder();
    builder.createCell(1, 1, "=13.0");
    builder.createCell(2, 1, "=\"ndelwae\"");
    builder.createCell(3, 1, "=true");
    Worksheet model = builder.createWorksheet();
    assertEquals(new FormulaValue(new ValueDouble(13.0)),
            model.getOneCellRawContents(1, 1));
    assertEquals(new FormulaValue(new ValueBoolean(true)),
            model.getOneCellRawContents(3, 1));
    assertEquals(new FormulaValue(new ValueString("ndelwae")),
            model.getOneCellRawContents(2, 1));
  }

  @Test
  public void testForFunctionSum() {
    // test the function sum that add 13 and 13
    BasicWorkSheetBuilder builder = new BasicWorkSheetBuilder();
    builder.createCell(1, 1, "13.0");
    builder.createCell(2, 1, "13.0");
    builder.createCell(3, 1, "=(SUM A1 B1)");
    Worksheet model = builder.createWorksheet();
    CellGeneral c1 = new Cell(new Coord(1, 1), new ValueDouble(13.0));
    CellGeneral c2 = new Cell(new Coord(2, 1), new ValueDouble(13.0));
    Formula referFunc1 = new FormulaReference(Collections.singletonList(c1),
            new Coord(3, 1));
    Formula referFunc2 = new FormulaReference(Collections.singletonList(c2),
            new Coord(3, 1));
    // check if the cell A3 contains the formula SUM as we want
    assertEquals(new FunctionSum(Arrays.asList(referFunc1, referFunc2)),
            model.getOneCellRawContents(3, 1));
    model.evaluateAll();
    // check if the SUM result is correct
    assertEquals(new ValueDouble(26.0), model.getOneCellResult(3, 1));
  }

  @Test
  public void testForFunctionProduct() {
    BasicWorkSheetBuilder builder = new BasicWorkSheetBuilder();
    builder.createCell(1, 1, "13.0");
    builder.createCell(2, 1, "13.0");
    builder.createCell(3, 1, "=(PRODUCT A1 B1)");
    Worksheet model = builder.createWorksheet();
    CellGeneral c1 = new Cell(new Coord(1, 1), new ValueDouble(13.0));
    CellGeneral c2 = new Cell(new Coord(2, 1), new ValueDouble(13.0));
    Formula referFunc1 = new FormulaReference(Collections.singletonList(c1),
            new Coord(3, 1));
    Formula referFunc2 = new FormulaReference(Collections.singletonList(c2),
            new Coord(3, 1));
    // check if the cell A3 contains the formula PRODUCT as we want
    assertEquals(new FunctionProduct(Arrays.asList(referFunc1, referFunc2)),
            model.getOneCellRawContents(3, 1));
    model.evaluateAll();
    // check if the PRODUCT result is correct
    assertEquals(new ValueDouble(169.0), model.getOneCellResult(3, 1));
  }

  @Test
  public void testForFunctionLessThan() {
    BasicWorkSheetBuilder builder = new BasicWorkSheetBuilder();
    builder.createCell(1, 1, "15.0");
    builder.createCell(2, 1, "15.0");
    builder.createCell(3, 1, "=(< A1 B1)");
    Worksheet model = builder.createWorksheet();
    CellGeneral c1 = new Cell(new Coord(1, 1), new ValueDouble(15.0));
    CellGeneral c2 = new Cell(new Coord(2, 1), new ValueDouble(15.0));
    Formula referFunc1 = new FormulaReference(Collections.singletonList(c1),
            new Coord(3, 1));
    Formula referFunc2 = new FormulaReference(Collections.singletonList(c2),
            new Coord(3, 1));
    // check if the cell A3 contains the formula < as we want
    assertEquals(new FunctionLessThan(Arrays.asList(referFunc1, referFunc2)),
            model.getOneCellRawContents(3, 1));
    model.evaluateAll();
    // check if the < result is correct
    assertEquals(new ValueBoolean(false), model.getOneCellResult(3, 1));
  }

  @Test
  public void testForStringAppend() {
    BasicWorkSheetBuilder builder = new BasicWorkSheetBuilder();
    builder.createCell(1, 1, "ABC");
    builder.createCell(2, 1, "SAL");
    builder.createCell(3, 1, "=(SAPPEND A1 B1)");
    Worksheet model = builder.createWorksheet();
    CellGeneral c1 = new Cell(new Coord(1, 1), new ValueString("ABC"));
    CellGeneral c2 = new Cell(new Coord(2, 1), new ValueString("SAL"));
    Formula referFunc1 = new FormulaReference(Collections.singletonList(c1),
            new Coord(3, 1));
    Formula referFunc2 = new FormulaReference(Collections.singletonList(c2),
            new Coord(3, 1));
    // check if the cell A3 contains the formula AppendString as we want
    assertEquals(new FunctionStringAppend(Arrays.asList(referFunc1, referFunc2)),
            model.getOneCellRawContents(3, 1));
    model.evaluateAll();
    // check if the AppendString result is correct
    assertEquals(new ValueString("ABCSAL"), model.getOneCellResult(3, 1));
  }

  @Test
  public void testReferToACellMultipleTime() {
    BasicWorkSheetBuilder builder = new BasicWorkSheetBuilder();
    builder.createCell(1, 1, "13.0");
    builder.createCell(2, 1, "=(SUM A1 A1)");
    Worksheet model = builder.createWorksheet();
    CellGeneral c1 = new Cell(new Coord(1, 1), new ValueDouble(13.0));
    Formula referFunc1 = new FormulaReference(Collections.singletonList(c1),
            new Coord(2, 1));
    assertEquals(new FunctionSum(Arrays.asList(referFunc1, referFunc1)),
            model.getOneCellRawContents(2, 1));
    model.evaluateAll();
    assertEquals(new ValueDouble(26.0), model.getOneCellResult(2, 1));
  }

  @Test
  public void testRegionReference() {
    BasicWorkSheetBuilder builder = new BasicWorkSheetBuilder();
    builder.createCell(1, 1, "13.0");
    builder.createCell(2, 1, "13.0");
    builder.createCell(3, 1, "=(SUM A1:B1)");
    Worksheet model = builder.createWorksheet();
    assertTrue(model.getOneCellRawContents(3, 1) instanceof FunctionSum);
    model.evaluateAll();
    assertEquals(new ValueDouble(26.0), model.getOneCellResult(3, 1));
  }

  @Test
  public void testNotCorrectInputForFunction() {
    BasicWorkSheetBuilder builder = new BasicWorkSheetBuilder();
    builder.createCell(1, 1, "=(SUM 5.0 true)");
    Worksheet model = builder.createWorksheet();
    List<Formula> arguments = new ArrayList<>(Arrays.asList(new FormulaValue(new ValueDouble(5.0)),
            new FormulaValue(new ValueBoolean(true))));
    assertEquals(new FunctionSum(arguments), model.getOneCellRawContents(1, 1));
    model.evaluateAll();
    assertEquals(new ValueDouble(5.0), model.getOneCellResult(1, 1));
    //the non-numeric arguments will be ignored
  }

  @Test
  public void testRenderingAStringWork() {
    BasicWorkSheetBuilder builder = new BasicWorkSheetBuilder();
    builder.createCell(1, 1, "13.0");
    builder.createCell(1, 2, "true");
    builder.createCell(1, 3, "jhjl\\");
    builder.createCell(1, 4, "ABC");
    builder.createCell(1, 5, "\"");
    Worksheet model = builder.createWorksheet();
    model.evaluateAll();
    assertEquals("13.000000", model.getOneCellResult(1, 1).print());
    assertEquals("true", model.getOneCellResult(1, 2).print());
    assertEquals("\"jhjl\\\\\"", model.getOneCellResult(1, 3).print());
    assertEquals("\"ABC\"", model.getOneCellResult(1, 4).print());
    assertEquals("\"\"\"", model.getOneCellResult(1, 5).print());
  }

  //add more tests based on grader's advise
  @Test
  public void testNestedFunctionCall() {
    BasicWorkSheetBuilder builder = new BasicWorkSheetBuilder();
    builder.createCell(1, 1, "13.0");
    builder.createCell(1, 2, "1.0");
    builder.createCell(1, 3, "3.5");
    builder.createCell(1, 4, "skejq");
    builder.createCell(1, 5, "6.0");
    builder.createCell(1, 6, "7.0");
    builder.createCell(1, 7, "=(PRODUCT(SUM (SUM A1 A2) A3) A4 A5 A6)");
    Worksheet model = builder.createWorksheet();
    model.evaluateAll();
    assertEquals(new ValueDouble(735.0), model.getOneCellResult(1, 7));
  }

  @Test
  public void testWithSimpleMockFile() {
    StringReader read = new StringReader(
            "A1 3\n" +
                    "B1 5\n" +
                    "C1 6\n" +
                    "D1 11\n" +
                    "A2 =(PRODUCT (SUM C1 A1) (SUM C1 A1))\n" +
                    "B2 =(PRODUCT (SUM D1 B1) (SUM D1 B1))\n" +
                    "A3 =(SUM (SUM A2:B2))\n" +
                    "B3 =(< 11 A3)\n" +
                    "\n" +
                    "A4 =(SUM (PRODUCT (SUM C1 A1) (SUM C1 A1)) " +
                    "(PRODUCT (SUM D1 B1) (SUM D1 B1)))\n");
    WorksheetReader.WorksheetBuilder<Worksheet> builder = new BasicWorkSheetBuilder();
    Worksheet model = WorksheetReader.read(builder, read);
    model.evaluateAll();
    assertEquals(new ValueDouble(3.0), model.getOneCellResult(1, 1));
    assertEquals(new ValueDouble(5.0), model.getOneCellResult(2, 1));
    assertEquals(new ValueDouble(6.0), model.getOneCellResult(3, 1));
    assertEquals(new ValueDouble(11.0), model.getOneCellResult(4, 1));
    assertEquals(new ValueDouble(81.0), model.getOneCellResult(1, 2));
    assertEquals(new ValueDouble(256.0), model.getOneCellResult(2, 2));
    assertEquals(new ValueDouble(337.0), model.getOneCellResult(1, 3));
    assertEquals(new ValueBoolean(true), model.getOneCellResult(2, 3));
    assertEquals(new ValueDouble(337.0), model.getOneCellResult(1, 4));
  }

  @Test
  public void testBigModelWithManyCell() {
    StringReader read = new StringReader(
            "A1 3\n" +
                    "A2 true\n" +
                    "A3 lalalalal\n" +
                    "A4 =5\n" +
                    "A5 =false\n" +
                    "A6 =\"something\"\n" +
                    "A7 =\"Else\"\n" +
                    "A8 =A1\n" +
                    "A9 =(SUM A1 A2 A6)\n" +
                    "A10 =(SUM A1:A2)\n" +
                    "A11 =(SUM A2:A3)\n" +
                    "A12 =(SUM A1)\n" +
                    "A13 =(PRODUCT A1 A2 A6)\n" +
                    "A14 =(PRODUCT A1:A2)\n" +
                    "A15 =(PRODUCT A2:A3)\n" +
                    "A16 =(PRODUCT A1)\n" +
                    "A17 =(< A4 A1)\n" +
                    "A18 =(SAPPEND A2 A3 A6:A7)\n" +
                    "A19 =(SUM 1 7)\n" +
                    "A20 =(PRODUCT 3 0)\n" +
                    "A21 =(< 2 1)\n" +
                    "A22 =(SAPPEND \"skT\" \"skwT\")\n" +
                    "\n");
    WorksheetReader.WorksheetBuilder<Worksheet> builder = new BasicWorkSheetBuilder();
    Worksheet model = WorksheetReader.read(builder, read);
    model.evaluateAll();
    assertEquals(new ValueDouble(3.0), model.getOneCellResult(1, 1));
    assertEquals(new ValueBoolean(true), model.getOneCellResult(1, 2));
    assertEquals(new ValueString("lalalalal"), model.getOneCellResult(1, 3));
    assertEquals(new ValueDouble(5.0), model.getOneCellResult(1, 4));
    assertEquals(new ValueBoolean(false), model.getOneCellResult(1, 5));
    assertEquals(new ValueString("something"), model.getOneCellResult(1, 6));
    assertEquals(new ValueString("Else"), model.getOneCellResult(1, 7));
    assertEquals(new ValueDouble(3.0), model.getOneCellResult(1, 8));
    assertEquals(new ValueDouble(3.0), model.getOneCellResult(1, 9));
    assertEquals(new ValueDouble(3.0), model.getOneCellResult(1, 10));
    assertEquals(new ValueDouble(0.0), model.getOneCellResult(1, 11));
    assertEquals(new ValueDouble(3.0), model.getOneCellResult(1, 12));
    assertEquals(new ValueDouble(3.0), model.getOneCellResult(1, 13));
    assertEquals(new ValueDouble(3.0), model.getOneCellResult(1, 14));
    assertEquals(new ValueDouble(0.0), model.getOneCellResult(1, 15));
    assertEquals(new ValueDouble(3.0), model.getOneCellResult(1, 16));
    assertEquals(new ValueBoolean(false), model.getOneCellResult(1, 17));
    assertEquals(new ValueString("truelalalalalsomethingElse"),
            model.getOneCellResult(1, 18));
    assertEquals(new ValueDouble(8.0), model.getOneCellResult(1, 19));
    assertEquals(new ValueDouble(0.0), model.getOneCellResult(1, 20));
    assertEquals(new ValueBoolean(false), model.getOneCellResult(1, 21));
    assertEquals(new ValueString("skTskwT"), model.getOneCellResult(1, 22));

  }

}
