import org.junit.Before;
import org.junit.Test;

import edu.cs3500.spreadsheets.model.content.value.Value;
import edu.cs3500.spreadsheets.model.content.value.ValueBoolean;
import edu.cs3500.spreadsheets.model.content.value.ValueDouble;
import edu.cs3500.spreadsheets.model.content.value.ValueString;
import edu.cs3500.spreadsheets.model.ValueVisitorBoolean;
import edu.cs3500.spreadsheets.model.ValueVisitorDouble;
import edu.cs3500.spreadsheets.model.ValueVisitorString;

import static org.junit.Assert.assertEquals;

/**
 * The tests for the ValueVisitors. They should check if the BooleanVisitor, DoubleVisitor, and
 * StringVisitor behave as expected to any Value Class.
 */
public class ValueVisitorTest {
  private Value valueBoolean;
  private Value valueString;
  private Value valueDouble;

  @Before
  public void setUp() {
    this.valueBoolean = new ValueBoolean(true);
    this.valueString = new ValueString("a");
    this.valueDouble = new ValueDouble(9.0);
  }

  /*
   * Tests for the valid situation that the value can be converted to the required visitor type.
   */

  @Test
  public void testValueBooleanVisitorValid() {
    assertEquals(true, valueBoolean.accept(new ValueVisitorBoolean()));
  }

  @Test
  public void testValueStringVisitorValid1() {
    assertEquals("a", valueString.accept(new ValueVisitorString()));
  }

  @Test
  public void testValueStringVisitorValid2() {
    assertEquals("true", valueBoolean.accept(new ValueVisitorString()));
  }

  @Test
  public void testValueStringVisitorValid3() {
    assertEquals("9.0", valueDouble.accept(new ValueVisitorString()));
  }

  @Test
  public void testValueDoubleVisitorValid() {
    assertEquals(9.0, valueDouble.accept(new ValueVisitorDouble()), 0.000000001);
  }

  /*
   * Tests for when the visitor should throw exceptions, that is, when trying to convert a string or
   * a number to a boolean, or trying to convert a boolean or string to a number.
   */

  @Test
          (expected = IllegalArgumentException.class)
  public void testValueBooleanVisitorNotValid1() {
    valueDouble.accept(new ValueVisitorBoolean());
  }

  @Test
          (expected = IllegalArgumentException.class)
  public void testValueBooleanVisitorNotValid2() {
    valueString.accept(new ValueVisitorBoolean());
  }

  @Test
          (expected = IllegalArgumentException.class)
  public void testValueDoubleVisitorNotValid1() {
    valueBoolean.accept(new ValueVisitorDouble());
  }

  @Test
          (expected = IllegalArgumentException.class)
  public void testValueDoubleVisitorNotValid2() {
    valueString.accept(new ValueVisitorDouble());
  }
}