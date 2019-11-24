import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.io.StringReader;
import java.util.NoSuchElementException;

import edu.cs3500.spreadsheets.model.BasicWorkSheetBuilder;
import edu.cs3500.spreadsheets.model.WorksheetReader;
import edu.cs3500.spreadsheets.model.worksheet.Worksheet;
import edu.cs3500.spreadsheets.view.IView;
import edu.cs3500.spreadsheets.view.TextualView;

/**
 * Tests to make sure a textual view for worksheet can work properly.
 */
public class TextualViewTest {

  @Test
  public void testTextualViewWhole1() {
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
    StringBuffer out = new StringBuffer();
    IView v = new TextualView(model, out);
    v.render();
    Readable newRead = new StringReader(out.toString());
    WorksheetReader.WorksheetBuilder<Worksheet> newBuilder = new BasicWorkSheetBuilder();
    Worksheet newModel = WorksheetReader.read(newBuilder, newRead);
    assertEquals(model, newModel);

  }

  @Test
  public void testTextualViewWhole2() {
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
    StringBuffer out = new StringBuffer();
    IView v = new TextualView(model, out);
    v.render();
    Readable newRead = new StringReader(out.toString());
    WorksheetReader.WorksheetBuilder<Worksheet> newBuilder = new BasicWorkSheetBuilder();
    Worksheet newModel = WorksheetReader.read(newBuilder, newRead);
    assertEquals(model, newModel);
  }

  @Test
  public void testTextualViewWithOtherError() {
    StringReader read = new StringReader(
            "A1 8\n" +
                    "A2 A3\n" +
                    "A3 A4\n" +
                    "A4 A1:A2\n" +
                    "A6 =(< true false)\n" +
                    "A7 =A1:A3");
    WorksheetReader.WorksheetBuilder<Worksheet> builder = new BasicWorkSheetBuilder();
    Worksheet model = WorksheetReader.read(builder, read);
    StringBuffer out = new StringBuffer();
    IView v = new TextualView(model, out);
    v.render();
    Readable newRead = new StringReader(out.toString());
    WorksheetReader.WorksheetBuilder<Worksheet> newBuilder = new BasicWorkSheetBuilder();
    Worksheet newModel = WorksheetReader.read(newBuilder, newRead);
    assertEquals(model, newModel);
  }

  @Test
  public void testTextualViewWithSelfReferentError() {
    StringReader read = new StringReader(
            "A1 8\n" +
                    "A2 =A3\n" +
                    "A3 =A4\n" +
                    "A4 =A1:A2\n" +
                    "A6 =(< true false)\n" +
                    "A7 =A1:A3");
    WorksheetReader.WorksheetBuilder<Worksheet> builder = new BasicWorkSheetBuilder();
    Worksheet model = WorksheetReader.read(builder, read);
    StringBuffer out = new StringBuffer();
    IView v = new TextualView(model, out);
    v.render();
    Readable newRead = new StringReader(out.toString());
    WorksheetReader.WorksheetBuilder<Worksheet> newBuilder = new BasicWorkSheetBuilder();
    Worksheet newModel = WorksheetReader.read(newBuilder, newRead);
    assertEquals(model, newModel);
  }

  @Test
  public void testTextualViewEmpty() {
    StringReader read = new StringReader("");
    WorksheetReader.WorksheetBuilder<Worksheet> builder = new BasicWorkSheetBuilder();
    Worksheet model = WorksheetReader.read(builder, read);
    StringBuffer out = new StringBuffer();
    IView v = new TextualView(model, out);
    v.render();
    Readable newRead = new StringReader(out.toString());
    WorksheetReader.WorksheetBuilder<Worksheet> newBuilder = new BasicWorkSheetBuilder();
    Worksheet newModel = WorksheetReader.read(newBuilder, newRead);
    assertEquals(model, newModel);
  }

  @Test
  public void testTextualViewDirectly() {
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
    StringBuffer out = new StringBuffer();
    IView v = new TextualView(model, out);
    v.render();
    assertEquals("A2 =(PRODUCT (SUM C1 A1) (SUM C1 A1))\n" +
            "B3 =(< 11.0 A3)\n" +
            "A1 3.0\n" +
            "B2 =(PRODUCT (SUM D1 B1) (SUM D1 B1))\n" +
            "B1 5.0\n" +
            "C1 6.0\n" +
            "D1 11.0\n" +
            "A4 =(SUM (PRODUCT (SUM C1 A1) (SUM C1 A1)) (PRODUCT (SUM D1 B1) (SUM D1 B1)))\n" +
            "A3 =(SUM (SUM A2:B2))", out.toString());
  }

  @Test
          (expected = NoSuchElementException.class)
  public void testTextualViewWithError() {
    StringReader read = new StringReader(
            "A1");
    WorksheetReader.WorksheetBuilder<Worksheet> builder = new BasicWorkSheetBuilder();
    Worksheet model = WorksheetReader.read(builder, read);
    StringBuffer out = new StringBuffer();
    IView v = new TextualView(model, out);
    v.render();
    Readable newRead = new StringReader(out.toString());
    WorksheetReader.WorksheetBuilder<Worksheet> newBuilder = new BasicWorkSheetBuilder();
    Worksheet newModel = WorksheetReader.read(newBuilder, newRead);
    assertEquals(model, newModel);
  }

}
