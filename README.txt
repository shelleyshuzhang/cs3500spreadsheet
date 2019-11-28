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
    are not in the interface. View also response to set up listeners which it need to use, it create
    map(s) with Key (as String/Integer/Character) and Value (Runnable) and set map(s) to listener
    then listener know what to do when events occur. But view do not response for setting exactly
    what to do when a event occur, it just create runnable, and inside the runnable, it delegate
    Features, which is the controller, to decide what to do.
* For the controller and listener:
Controller:
1. Features Interface:
Include features methods which should be implements by the controller class.
2. BasicController implements Feature Interface:
In the Constructor of the BasicController takes in a model and a view, the model is editable.
BasicController add itself as a feature listener to the view, it accomplishing features by
coordinate model and view. The reason we choose this design is, because "hearing from outside"
(like hearing from mouse clicked, button clicked...)will done by Listener in view's package, it do
not bond the controller with specific library. It just responds when user doing something on the
view (on the GUI), but do not exactly heard directly from the user (view heard directly from the
user).


Listener:
We make three listener: ButtonListener, KeyboardListener (implements KeyListener),
and MouseEventListener (implements MouseListener). We made these three classes, and let view to
set up objects of each of Listener and add them to (view)itself. We choose this design is because we
think it is clear and flexible.
1. ButtonListener (implements ActionListener)
Contain a map with String as key, Runnable as Value. When a action (like button clicked), the
listener will find the corresponding String of the ActionEvent. Use this String as key, to find a
Runnable value which should be run in this situation.
2. KeyboardListener (implements KeyListener)
Contain three maps for keyPressEvent, keyReleaseEvent, and keyTypedEvent. The first two have
Integer as Key, the third has Character as Key. Values are all Runnable. Same as ButtonListener,
catch a event -> transfer it to corresponding Integer/Character, find which runnable should run now
(or do nothing if the map do not contain corresponding Key).
3. MouseEventListener (implements MouseListener)
Because We don't want to rewrite all method in MouseListener Interface, we use "extends
MouseAdapter" to allow us only re-write the method we want. This Listener also contain a map,
Integer as Key, Runnable as Value, and do similar thing as above listener.

The reason we design map(s) for all listener is because we think it is, again, clear and flexible,
if we want to add some new function to it, we only need to put new Key and Value in the map.

