package edu.cs3500.spreadsheets.provider.view;

import java.awt.Dimension;

import javax.swing.JScrollPane;
import javax.swing.JFrame;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.provider.model.IModelSpreadsheet;

/**
 * A visual view that draw the image of spreadsheet.
 */
public class BasicVisualView extends JFrame implements ISpreadsheetView {

  final IModelSpreadsheet model;
  JScrollPane scrollPane;
  GridPanel gridPanel;

  /**
   * A textual view that draw the given model.
   *
   * @param m spreadsheet model
   */
  public BasicVisualView(IModelSpreadsheet m) {
    super();
    this.model = m;
    this.setTitle("Excel");
    this.setSize(500, 500);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    int cellWidth = 120;
    int cellHeight = 50;
    int editor = 20;
    gridPanel = new GridPanel(cellWidth, cellHeight, 0, 0, editor, model, null);
    scrollPane = new JScrollPane(gridPanel,
            JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    gridPanel.setPreferredSize(new Dimension(cellWidth * editor, cellHeight * editor));


    scrollPane.getHorizontalScrollBar().addAdjustmentListener(e -> {
      int show = scrollPane.getHorizontalScrollBar().getVisibleAmount();
      int cur = scrollPane.getHorizontalScrollBar().getValue();
      int max = scrollPane.getHorizontalScrollBar().getMaximum();

      if (cur == (max - show)) {
        gridPanel.setPreferredSize(new Dimension(
                gridPanel.getPreferredSize().width + 120 * 10,
                gridPanel.getPreferredSize().height));
      }
      gridPanel.changeCols((e.getValue() / 120));
      gridPanel.repaint();
    });

    scrollPane.getVerticalScrollBar().addAdjustmentListener(e -> {
      int show = scrollPane.getVerticalScrollBar().getVisibleAmount();
      int cur = scrollPane.getVerticalScrollBar().getValue();
      int max = scrollPane.getVerticalScrollBar().getMaximum();
      if (cur == (max - show)) {
        gridPanel.setPreferredSize(new Dimension(gridPanel.getPreferredSize().width,
                gridPanel.getPreferredSize().height + 50 * 10));
      }
      gridPanel.changeRows((e.getValue() / 50));
      gridPanel.repaint();
    });

    this.getContentPane().add(scrollPane);
  }


  @Override
  public void render() {
    this.setVisible(true);
  }

  @Override
  public String getTextString() {
    throw new IllegalArgumentException("error");
  }

  @Override
  public void resetFocus() {
    throw new IllegalArgumentException("error");
  }

  @Override
  public void cleanTextString() {
    throw new IllegalArgumentException("error");
  }

  @Override
  public void updateTextField(Coord c) {
    throw new IllegalArgumentException("error");
  }

  @Override
  public Coord getCoord() {
    throw new IllegalArgumentException("error");
  }

  @Override
  public void addMouse() {
    throw new IllegalArgumentException("error");
  }

  @Override
  public void reDraw() {
    throw new IllegalArgumentException("error");
  }

  @Override
  public void addButton() {
    throw new IllegalArgumentException("error");
  }
}
