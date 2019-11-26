package edu.cs3500.spreadsheets.view;


import java.awt.event.ActionEvent;
import java.util.List;
import java.util.ArrayList;

import javax.swing.*;

import edu.cs3500.spreadsheets.controller.Features;

public class KeyComponent extends JPanel {
  List<Features> featureListeners = new ArrayList<>();

  public KeyComponent() {
    this.getActionMap().put("acceptCellInput", new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        for (Features f : featureListeners) {

        }
      }
    });
    this.getActionMap().put("rejectCellInput", new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        for (Features f : featureListeners) {

        }
      }
    });
    this.getActionMap().put("evokeCellEdit", new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        for (Features f : featureListeners) {

        }
      }
    });
  }

  public void addFeature(Features f) {
    this.featureListeners.add(f);
  }

}
