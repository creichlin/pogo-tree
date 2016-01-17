package ch.kerbtier.pogo;

import static org.junit.Assert.*;

import org.junit.Test;

import ch.kerbtier.pogo.exceptions.NoSuchField;
import ch.kerbtier.pogo.util.H2Test;

public class TestPrimitiveListOperations extends H2Test {

  @Test
  public void testSetList() {
    transaction {
      pogo["field"] = PogoList.class
      assert pogo["field"] instanceof PogoList
    }
  }

  @Test
  public void testEmptyListSize() {
    transaction {
      pogo["field"] = PogoList.class
      assert pogo["field"].size() == 0
    }
  }

  @Test
  public void testAddElement() {
    transaction {
      pogo["field"] = PogoList.class
      pogo["field"] << "FOO"
      assert pogo["field"].size() == 1
    }
  }

  @Test
  public void testAddElementTwice() {
    transaction {
      pogo["field"] = PogoList.class
      pogo["field"] << "FOO"
      pogo["field"] << "BARR"
      assert pogo["field"].size() == 2
    }
  }

  @Test
  public void testAddElementWithTransaction() {
    transaction {
      pogo["field"] = PogoList.class
      pogo["field"] << "FOO"
    }

    transaction {
      assert pogo["field"].size() == 1
    }
  }

  @Test
  public void testAddPrimitiveWithTransaction() {
    transaction {
      pogo["field"] = PogoList.class
      pogo["field"] << "FOO"
    }

    transaction {
      assert pogo["field"][0] == "FOO"
    }
  }

  @Test(expected = NoSuchField.class)
  public void deleteList() {
    transaction {
      pogo["field"] = PogoList.class
      pogo["field"] << "FOO"

      pogo["field"].delete()

      pogo["field"]
    }
  }

  @Test(expected = NoSuchField.class)
  public void deleteListWithTransaction() {
    transaction {
      pogo["field"] = PogoList.class
      pogo["field"] << "FOO"
    }

    transaction { pogo["field"].delete() }

    transaction { pogo["field"] }
  }
}
