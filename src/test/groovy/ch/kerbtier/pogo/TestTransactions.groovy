package ch.kerbtier.pogo;

import static org.junit.Assert.*;

import org.junit.Test;

import ch.kerbtier.pogo.util.H2Test;

public class TestTransactions extends H2Test {

  @Test
  public void testCanceledTransaction() {
    transaction {
      getPogo().set("field", "foo");
    }

    transaction { transaction ->
      getPogo().set("field", "bar");
      assertEquals("bar", getPogo().get("field"));
      transaction.rollback();
    }

    assertEquals("foo", getPogo().get("field"));
  }

}
