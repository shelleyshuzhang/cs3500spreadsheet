package edu.cs3500.spreadsheets.provider.view;

import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.BasicStroke;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.provider.model.IModelSpreadsheet;

/**
 * a GridPanel which override the JPanel's paint for paint a table.
 */
public class GridPanel extends javax.swing.JPanel {
  final int width;
  final int height;
  int firstRows;
  int firstCols;
  int size;
  IModelSpreadsheet model;
  Coord coord;

  /**
   * draw the table of given model.
   *
   * @param w width of table
   * @param h height of table
   * @param r rows of table
   * @param c columns of table
   * @param m model of spreadsheet
   */
  GridPanel(int w, int h, int r, int c, int size, IModelSpreadsheet m, Coord coord) {
    width = w;
    height = h;
    firstRows = r;
    firstCols = c;
    this.size = size;
    model = m;
    this.coord = coord;

  }

  /**
   * get the selected coord in the gridPanel.
   *
   * @return the selected coord in the gridPanel
   */
  public Coord getCoord() {
    return this.coord;
  }

  /**
   * change the firstCols to the give int.
   *
   * @param changing the int calculate for the scrollPanel
   */
  public void changeCols(int changing) {
    this.firstCols = changing;
  }

  /**
   * change the firstRows to the give int.
   *
   * @param changing the int calculate for the scrollPanel
   */
  public void changeRows(int changing) {
    this.firstRows = changing;
  }

  @Override
  protected void paintComponent(Graphics g) {

    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g;
    int fontSize = 16;
    Font f = new Font("Comic Sans Ms", Font.BOLD, fontSize);
    g2.setFont(f);

    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        if (j == 0 && i >= 1) {
          g2.setPaint(Color.gray);
          g2.setClip((j + firstCols) * width, (i + firstRows) * height, width, height);
          g2.fillRect((j + firstCols) * width, (i + firstRows) * height, width, height);
          String rowName = Integer.toString(i + firstRows);
          g2.setPaint(Color.CYAN);
          g2.drawString(rowName,
                  (j + firstCols) * width + width / 2, (i + firstRows) * height + height / 2);
          g2.setPaint(Color.black);
        } else if (i == 0 && j >= 1) {
          g2.setPaint(Color.gray);
          g2.setClip((j + firstCols) * width, (i + firstRows) * height, width, height);
          g2.fillRect((j + firstCols) * width, (i + firstRows) * height, width, height);
          String colName = Coord.colIndexToName(j + firstCols);
          g2.setPaint(Color.CYAN);
          g2.drawString(colName, (j + firstCols) * width + width / 2,
                  (i + firstRows) * height + height / 2);
          g2.setPaint(Color.black);
        } else if (i != 0 && j != 0) {
          if (coord != null && this.coord.equals(new Coord(j + firstCols, i + firstRows))) {
            g2.setPaint(Color.RED);
            g2.setStroke(new BasicStroke(6));
            g2.setClip((j + firstCols) * width, (i + firstRows) * height, width, height);
            g2.drawRect((j + firstCols) * width, (i + firstRows) * height, width, height);
            g2.setStroke(new BasicStroke(0));
            g2.setPaint(Color.black);
            Coord cur = new Coord(j + firstCols, i + firstRows);
            if (model != null && model.getWorksheet().containsKey(cur)) {
              String info = model.getContent(j + firstCols, i + firstRows);
              g2.drawString(info, (j + firstCols) * width + 5,
                      (i + firstRows) * height + height * 2 / 3);
            }
          } else {
            g2.setClip((j + firstCols) * width, (i + firstRows) * height, width, height);
            g2.drawRect((j + firstCols) * width, (i + firstRows) * height, width, height);
            Coord cur = new Coord(j + firstCols, i + firstRows);
            if (model != null && model.getWorksheet().containsKey(cur)) {
              String info = model.getContent(j + firstCols, i + firstRows);
              g2.drawString(info, (j + firstCols) * width + 5,
                      (i + firstRows) * height + height * 2 / 3);
            }
          }
        } else if (i == 0 && j == 0) {
          g2.setPaint(Color.darkGray);
          g2.setClip((j + firstCols) * width, (i + firstRows) * height, width, height);
          g2.fillRect((j + firstCols) * width, (i + firstRows) * height, width, height);
          g2.setPaint(Color.black);
        }
      }
    }
  }
}