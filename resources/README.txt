We implemented the feature to resize columns or rows. We did this by adding a new mouse listener
for the header table to catch the mouse pressed, moved and dragged events. This allows the cursor
to change when a user reached the middle of two cells in the header table, and resize when a user
drag it. We only save the information to model in each cell when a user clicked save button. Both
files with the width and height information and files that does not have them can be read and shown
as expected. We simply save the width and height of every cell we have in the file, so the model
will have them if the file have them, and then show them as view.

The affected files:
BasicController
CellGeneral
Cell
BasicWorkSheetBuilder
WorksheetReadOnly
Worksheet
BasicWorkSheet
IView
EditableView
WorksheetScrollablePanel
ResizeRowMouseListener
