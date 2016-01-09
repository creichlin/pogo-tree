package ch.kerbtier.pogo;

import static org.junit.Assert.*;

import org.junit.Test;

import ch.kerbtier.pogo.exceptions.NoSuchField;
import ch.kerbtier.pogo.util.H2Test;

public class TestBasicObjectFieldManipulation extends H2Test {

  @Test
  public void testSetAttribute() {
    transaction {
      pogo["field"] = "value"
      assertEquals("value", pogo["field"])
    }
  }

  @Test
  public void testNullAttribute() {
    transaction {
      pogo["field"] = null
      assertNull(pogo["field"])
    }
  }

  @Test
  public void testChangeField() {
    transaction {
      pogo["field"] = "foo"
      pogo["field"] = "bar"
      assertEquals("bar", pogo["field"])
    }
  }

  @Test
  public void testChangeFieldType() {
    transaction {
      pogo["field"] = "foo"
      pogo["field"] = 17
      assertEquals(17, pogo["field"]);
    }
  }

  @Test(expected = NoSuchField.class)
  public void testDeleteField() {
    transaction {
      pogo["field"] = "foo"
      pogo.delete("field")
      pogo["field"]
    }
  }

  @Test(expected = NoSuchField.class)
  public void testInexistent() {
    transaction {
      pogo["field"]
    }
  }

  @Test
  public void testFieldsAfterDeletion() {
    transaction {
      pogo["field"] = "foo"
      pogo["field2"] = 17
      pogo.delete "field"
      assertTrue(["field2"] as Set == pogo.fields as Set)
    }
  }

    @Test
  public void testFields() {
    transaction {
      pogo["field"] = "foo"
      pogo["field2"] = 17
      assertTrue(["field", "field2"] as Set == pogo.fields as Set)
    }
  }
}
