package edu.cs3500.spreadsheets.provider.view;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JScrollPane;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTextField;

import edu.cs3500.spreadsheets.model.Coord;

/**
 * a EditableVisualView in ISpreadsheetView which use to edit.
 */
public class EditableVisualView extends JFrame implements ISpreadsheetView {
  private BasicVisualView bvv;
  private JButton sure;
  private JButton cancel;
  private JTextField textField;
  private JScrollPane scrollPane;
  private GridPanel gridPanel;

  /**
   * a EditableVisualView input a BasicVisualView and use it gridPanel to create a view.
   *
   * @param bvv BasicVisualView
   */
  public EditableVisualView(BasicVisualView bvv) {
    super();
    this.bvv = bvv;
    bvv.setVisible(false);
    this.setTitle("Excel edit");
    this.setLayout(null);
    this.setSize(1800, 900);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    sure = new JButton("√");
    sure.setActionCommand("sure button");
    sure.setBounds(25, 25, 50, 50);
    cancel = new JButton("×");
    cancel.setActionCommand("cancel button");
    cancel.setBounds(100, 25, 50, 50);
    textField = new JTextField();
    textField.setBounds(200, 10, 1000, 80);
    this.add(sure);
    this.add(cancel);
    this.add(textField);
    scrollPane = bvv.scrollPane;
    gridPanel = bvv.gridPanel;
    scrollPane.setBounds(0, 100, 1800, 900);
    scrollPane.setPreferredSize(new Dimension(1800, 900));
    this.add(scrollPane);
  }


  @Override
  public void render() {
    this.setVisible(true);
  }

  @Override
  public void addButton() {
    this.sure.addActionListener(e1 -> {
      String text = this.getTextString();
      if (this.getCoord() != null) {
        if (!text.equals("")) {
          this.bvv.model.addAndUpdate(this.getCoord().col, this.getCoord().row, text);
          this.reDraw();
          this.resetFocus();
        }
        if (bvv.model.getWorksheet().containsKey(this.getCoord()) && text.equals("")) {
          bvv.model.removeCell(this.getCoord());
          this.reDraw();
          this.resetFocus();
        }
      }
    });
    this.cancel.addActionListener(e2 -> {
      if (bvv.model.getWorksheet().containsKey(this.getCoord())) {
        Coord c = this.getCoord();
        textField.setText(bvv.model.getContent(c.col, c.row, true));
        this.reDraw();
        this.resetFocus();
      } else {
        this.cleanTextString();
      }
    });
  }


  @Override
  public void addMouse() {
    this.gridPanel.addMouseListener(new MouseAdapter() {

      @Override
      public void mouseClicked(MouseEvent e) {
        Point position = e.getLocationOnScreen();
        int hor = scrollPane.getHorizontalScrollBar().getValue();
        int ver = scrollPane.getVerticalScrollBar().getValue();
        position.x = position.x + hor;
        position.y = position.y + ver;
        double cellWidth = 120.0;
        double cellHeight = 50.0;
        double editor = 100.0;
        double rowPosition = Math.floor((position.getX() - cellWidth) / cellWidth) + 1;
        double colPosition = Math.floor(
                (position.getY() - editor - cellHeight - 35) / cellHeight) + 1;
        Coord c = new Coord((int) rowPosition, (int) colPosition);
        gridPanel.coord = c;
        updateTextField(c);
        gridPanel.repaint();
        resetFocus();
      }
    });
  }

  @Override
  public void updateTextField(Coord c) {
    this.textField.setText(bvv.model.getContent(c.col, c.row, true));
  }

  @Override
  public String getTextString() {
    return textField.getText();
  }

  @Override
  public void resetFocus() {
    this.setFocusable(true);
    this.requestFocus();
  }

  @Override
  public void cleanTextString() {
    textField.setText("");
  }

  @Override
  public Coord getCoord() {
    return this.gridPanel.getCoord();
  }

  public void reDraw() {
    this.gridPanel.repaint();
  }
}
