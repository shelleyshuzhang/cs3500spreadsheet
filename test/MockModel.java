import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.cell.CellGeneral;
import edu.cs3500.spreadsheets.model.content.Contents;
import edu.cs3500.spreadsheets.model.content.value.Value;
import edu.cs3500.spreadsheets.model.content.value.ValueString;
import edu.cs3500.spreadsheets.model.worksheet.Worksheet;

/**
 * A mock model for work sheet, which contain a log, when methods in it have been called, the model
 * append corresponding message on the log, which show what have been done by the model.
 */
public class MockModel implements Worksheet {
  final StringBuilder log;

  /**
   * Construct a MockModel with a non-null StringBuilder.
   *
   * @param log the given StringBuilder
   */
  public MockModel(StringBuilder log) {
    this.log = Objects.requireNonNull(log);
  }

  @Override
  public void evaluateAll() {
    log.append("model being called to evaluate all cells in it\n");
  }

  @Override
  public List<Coord> editCellContent(int col, int row, Contents contents) {
    log.append("model being called to edit a cell at col: " + col + ", " + "row: " + row + " with "
            + "content: " + contents.toString() + "\n");
    return new ArrayList<>();
  }

  @Override
  public void setCellRowHeight(int col, int row, int height) {
    log.append("model being called to set a cell in col:" + col + ", row:" + row + " ,'s height " +
            "as " + height + "\n");
  }

  @Override
  public void setCellColWidth(int col, int row, int width) {
    log.append("model being called to set a cell in col:" + col + ", row:" + row + " ,'s width " +
            "as " + width + "\n");
  }

  @Override
  public Value getOneCellResult(int col, int row) {
    log.append("model being called to get one cell result at col: " + col + ", row: " + row + "\n");
    return new ValueString("");
  }

  @Override
  public int getWorkSheetSize() {
    return 0;
  }

  @Override
  public Contents getOneCellRawContents(int col, int row) {
    log.append("model being called to get one cell raw content at col: " + col + ", row: " + row
            + "\n");
    return new ValueString("");
  }

  @Override
  public Set<String> getAllCellCoordinates() {
    log.append("model being called to get all cells' coordinate\n");
    return new HashSet<>();
  }

  @Override
  public HashMap<Coord, CellGeneral> getAllRawCell() {
    log.append("model being called to get all cells' raw content\n");
    return new HashMap<>();
  }

  @Override
  public int getCellRowHeight(int col, int row) {
    return 0;
  }

  @Override
  public int getCellColWidth(int col, int row) {
    return 0;
  }
}
