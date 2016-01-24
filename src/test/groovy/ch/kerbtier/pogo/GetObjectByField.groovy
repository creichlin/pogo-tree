package ch.kerbtier.pogo;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.rules.ExpectedException;

import ch.kerbtier.pogo.exceptions.NoSuchField;
import ch.kerbtier.pogo.util.H2Test;

public class GetObjectByField extends H2Test {

  @Test
  public void testGetObject() {
    transaction {
      pogo["field"] = PogoObject.class
      pogo["field"]["foo"] = "bar"
      pogo["field"]["foo2"] = "bar2"
      
      def obj = pogo.getObjectByField("foo", "bar")
      assert obj["foo2"] == "bar2"
    }
  }

  @Test
  public void testGetObjectWithTransaction() {
    transaction {
      pogo["field"] = PogoObject.class
      pogo["field"]["foo"] = "bar"
      pogo["field"]["foo2"] = "bar2"
    }
    
    transaction {
      def obj = pogo.getObjectByField("foo", "bar")
      assert obj["foo2"] == "bar2"
    }
  }
  
  @Test(expected = NoSuchField.class)
  public void testMiss() {
    transaction {
      def obj = pogo.getObjectByField("foo", "bar")
    }
  }  
}
