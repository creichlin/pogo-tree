package ch.kerbtier.pogo;

import static org.junit.Assert.*;

import org.junit.Test;

import ch.kerbtier.pogo.exceptions.NoSuchField;
import ch.kerbtier.pogo.util.H2Test;

public class TestObjectListOperations extends H2Test {

  @Test
  public void testAddObject() {
    transaction {
      pogo["field"] = PogoList.class
      pogo["field"] << PogoObject.class
      assert pogo["field"].size() == 1
    }
  }

  @Test
  public void testSetChildObjectValue() {
    transaction {
      pogo["field"] = PogoList.class
      pogo["field"] << PogoObject.class
      pogo['field'][0]['foo'] = 'bar'
      
      assert pogo['field'][0]['foo'] == 'bar'
    }
  }

  @Test
  public void testAddObjectWithTransaction() {
    transaction {
      pogo["field"] = PogoList.class
      pogo["field"] << PogoObject.class
    }

    transaction {
      assert pogo["field"].size() == 1
    }
  }
}
