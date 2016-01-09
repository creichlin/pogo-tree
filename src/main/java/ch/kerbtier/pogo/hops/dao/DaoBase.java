package ch.kerbtier.pogo.hops.dao;

import ch.kerbtier.hopsdb.model.annotations.Column;

public class DaoBase {
  @Column(key = true)
  private int id = -1;

  public int getId() {
    return id;
  }
}
