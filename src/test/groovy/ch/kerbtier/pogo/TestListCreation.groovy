package ch.kerbtier.pogo;

import static org.junit.Assert.*;

import org.junit.Test;

import ch.kerbtier.pogo.exceptions.NoSuchField;
import ch.kerbtier.pogo.util.H2Test;

public class TestListCreation extends H2Test {

  @Test
  public void testSetList() {
    transaction {
      pogo["field"] = PogoList.class
      assert pogo["field"] instanceof PogoList
    }
  }

}
