package edu.cs3500.spreadsheets.controller;

import edu.cs3500.spreadsheets.view.IView;

public interface Features {
  void acceptEdit(int col, int row);

  void refuseEdit(int col, int row);

  void invokeEdit(int col, int row);

}
