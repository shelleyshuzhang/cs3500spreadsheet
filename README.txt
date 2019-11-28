Our design is mostly based on the previous code, but we did make several changes.
* The model now enforces the observer pattern. In our cell package, we have a new CellObserver class
    which takes in a cell. The observer class will be added to any cell that it wants to observe.
    For example, if A3 is the sum of A1 and A2, we will create an observer that takes in A3 and add
    this observer to both A1 and A2. Every time that A1 or A2 changes, A3 will change too. Also,
    if A1 or A2 observes another cell, say A1 observes A10, then the change of A10 will cause A1
    and A3 to change too. We enforced this functionality by using recursive functions to go through
    the list of observers in a cell and updates the evaluated results. Our method returns a list of
    coordinates contains every cell that changes and has to be updated in the view, then we update
    them accordingly. We will remove the old observers and add new ones whenever we construct a new
    cell or update the content of an existing cell. There should not be any cycles because our
    content throws an exception for the cells that contain self-reference, and so stop them from
    existing. And we do not go into a cell if its coordinate already exists before in our returning
    list of coordinates.
* For the view, we added the new editable view using the existing scrollable panel we have created.
    We merged the blank and evaluated views into a single view because we found it unnecessary.
    Whenever a blank view is edited by a user, it becomes an evaluated view, so we changed that.
    Now, we have a textual view, a visual view, and an editable view. The editable view is almost
    the same as the visual view except that it has a toolbar for all the necessary editing
    operations. There is one text field for showing the formula of a cell and edit it, a button for
    confirming changes, a button for refusing changes, a button for saving the file, and another
    button for open a file. You can select a cell by single click the mouse, move the selection to
    neighbor cells by pressing the keyboard, delete the cell content by pressing the delete key,
    and double click a cell or click the text field when a cell is selected to edit the cell. We
    add the new methods in the interface and let the visual and textual view do nothing in case of
    those methods so that we avoid casting or letting the editable view have public methods that
    are not in the interface.