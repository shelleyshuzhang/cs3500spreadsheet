package edu.cs3500.spreadsheets;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.NoSuchElementException;

import edu.cs3500.spreadsheets.controller.MapController;
import edu.cs3500.spreadsheets.model.BasicWorkSheetBuilder;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.cell.CellGeneral;
import edu.cs3500.spreadsheets.model.content.value.Value;
import edu.cs3500.spreadsheets.model.worksheet.BasicWorkSheet;
import edu.cs3500.spreadsheets.model.worksheet.Worksheet;
import edu.cs3500.spreadsheets.model.WorksheetReader;
import edu.cs3500.spreadsheets.sexp.SexpVisitorFormula;
import edu.cs3500.spreadsheets.view.IView;
import edu.cs3500.spreadsheets.view.TextualView;
import edu.cs3500.spreadsheets.view.EditableView;
import edu.cs3500.spreadsheets.view.VisualView;

/**
 * The main class for our program.
 */
public class BeyondGood {
  /**
   * The main entry point. Support four kinds of command line: "-in some-filename -eval some-cell",
   * "-in some-filename -save some-new-filename", "-in some-filename -gui", "-gui".
   *
   * @param args any command-line arguments
   */
  public static void main(String[] args) {
    IView view;
    if (args.length >= 3 && args[0].equals("-in")) {
      try {
        File file = new File(args[1]);
        FileReader read = new FileReader(file);
        WorksheetReader.WorksheetBuilder<Worksheet> builder = new BasicWorkSheetBuilder();
        Worksheet model = WorksheetReader.read(builder, read);
        if (args.length == 4 && args[2].equals("-eval")) {
          model.evaluateAll();
          int[] coord = SexpVisitorFormula.getSingleRefer(args[3]);
          Value v = model.getOneCellResult(coord[0], coord[1]);
          System.out.print(v.print());
        } else if (args.length == 4 && args[2].equals("-save")) {
          File nFile = new File(args[3]);//file will be rewrite if the file already exists
          FileWriter writer = new FileWriter(nFile);
          view = new TextualView(model, writer);
          view.render();
          writer.close();
        } else if (args.length == 3 && args[2].equals("-gui")) {
          model.evaluateAll();
          view = new VisualView("evaluated and uneditable", model);
          view.render();
        } else if (args.length == 3 && args[2].equals("-edit")) {
          model.evaluateAll();
          view = new EditableView("evaluated and editable", model);
          MapController c = new MapController(model, view);
          c.go();
        } else {
          System.out.print("Illegal command line");
        }
      } catch (FileNotFoundException e) {
        System.out.print("file (reading) name is not valid");
        System.exit(1);
      } catch (IllegalArgumentException e) {
        System.out.print(e.getMessage());
        System.exit(1);
      } catch (NullPointerException | IOException e) {
        System.out.print("given a null file (new one) name");
        System.exit(1);
      } catch (NoSuchElementException | IllegalStateException e) {
        System.out.print("the file reading is not successful/the file is not well formed");
      }
    } else if (args.length == 1 && args[0].equals("-gui")) {
      Worksheet model = new BasicWorkSheet(new HashMap<Coord, CellGeneral>());
      model.evaluateAll();
      view = new VisualView("blank and uneditable", model);
      view.render();
    } else if (args.length == 1 && args[0].equals("-edit")) {
      Worksheet model = new BasicWorkSheet(new HashMap<Coord, CellGeneral>());
      model.evaluateAll();
      view = new EditableView("blank and editable", model);
      MapController c = new MapController(model, view);
      c.go();
    } else {
      System.out.print("Illegal command line");
    }
  }
}
