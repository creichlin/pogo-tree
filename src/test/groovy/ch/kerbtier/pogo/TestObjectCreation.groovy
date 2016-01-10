package ch.kerbtier.pogo;

import static org.junit.Assert.*;

import org.junit.Test;

import ch.kerbtier.pogo.exceptions.NoSuchField;
import ch.kerbtier.pogo.util.H2Test;

public class TestObjectCreation extends H2Test {

  @Test
  public void testSetObject() {
    transaction {
      pogo["field"] = PogoObject.class
      pogo["field"]["foo"] = "bar"
      assertEquals("bar", pogo["field"]["foo"])
    }
  }

  @Test
  public void testDeleteObjectBySettingNull() {
    transaction {
      pogo["field"] = PogoObject.class
      pogo["field"] = null
      assertNull(pogo["field"])
    }
  }
  
  @Test(expected = NoSuchField.class)
  public void testDeleteObject() {
    transaction {
      pogo["field"] = PogoObject.class
      pogo["field"].delete();
      pogo["field"]
    }
  }
}
