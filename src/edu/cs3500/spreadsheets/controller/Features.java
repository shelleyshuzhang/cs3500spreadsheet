package edu.cs3500.spreadsheets.controller;

public interface Features {
  void acceptEdit(int col, int row);

  void refuseEdit(int col, int row);

  void invokeEdit(int col, int row);

}
