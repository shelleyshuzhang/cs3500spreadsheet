We have four interfaces in the model. Each of which contains several abstract and concrete classes. The basic functionality is to represent the spreadsheet, the cells on the spreadsheet, and the information they contain. The model will also keep track of the raw content and the evaluated value of a single cell. 
* Interface Worksheet: Represent the spreadsheet we are going to implement in the future.
   * Concrete Class BasicWorkSheet: Implements the interface and represents one possible spreadsheet. Have all the cells, their coordinates, their raw contents, and the evaluated values. We also have another method that can edit a cell to have new content, but we have not implemented this because we are not sure how we can get the value from the user.
* Interface CellGeneral: Represent the cell of the worksheet in general.
   * Concrete Class Cell: The class to represent a single cell, has the basic ability to get the instance of its content, evaluate its content, get its coordinate, and store the value after it evaluates itself.
* Interface Contents: Represent the information that could be stored in the cell.
   * Concrete Class Blank: Represents a blank content
   * Abstract Class Value: Represent the content that only contains a value, no equivalence mark.
      * Concrete Class String
      * Concrete Class Boolean
      * Concrete Class Double
   * Abstract Class Formula: Represent the content that is a formula that starts with an equivalence mark.
      * Concrete Class FormulaValue: Represent the value inside a Formula, use the Value class directly
      * Concrete Class FormulaReference: Represent the reference to cells, either single or a group
      * Abstract Class FormulaFunction: Represents the formula that is a function
         * Concrete Class FunctionLessThan: Represent the function “<”
         * Concrete Class FunctionProduct: Represent the function “PRODUCT”
         * Concrete Class FunctionSum: Represent the function “SUM”
         * Concrete Class StringAppend: Represent the function “SAPPEND”, it will append the given strings into a single string, if the given value is not a string, it will convert it to the string representation and append it.
* Interface ValueVisitor: visit the potential value that a cell could produce and transfer that to the type of value the visitor wants.
   * Concrete Class ValueVisitorBoolean: Visit a Value and convert it to a boolean, throw an exception if the Value does not contain a boolean
   * Concrete Class ValueVisitorDouble: Visit a Value and convert it to a double, throw an exception if the Value does not contain a double
   * Concrete Class ValueVisitorString: Visit a Value and convert it to a String, throw an exception if the Value does not contain a String
The Model also has:
* Concrete Class BasicWorkSheetBuilder: Implements the given builder interface and will build a Worksheet based on the given input.
* Enum BasicSupportFunctions: Provide the available functions that the spreadsheet supports, has a static method that will return a map with the string name of the function as the key and the Enum name as the value.
The sexp has one more class than given:
* Concrete Class SexpVisitorFormula: Implements the SexpVisitor, will read the expressions and convert them to the Formula we have.