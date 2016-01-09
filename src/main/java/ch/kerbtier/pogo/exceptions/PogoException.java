package ch.kerbtier.pogo.exceptions;

import java.sql.SQLException;

public class PogoException extends RuntimeException {

  public PogoException(SQLException e) {
    super(e);
  }

}
