package ch.kerbtier.pogo.hops.dao;

import ch.kerbtier.hopsdb.model.annotations.Column;
import ch.kerbtier.hopsdb.model.annotations.Table;

@Table(name = "object")
public class DaoObject {
  @Column(key = true)
  private int id = -1;

  public DaoObject() {
    // for Db
  }

  public int getId() {
    return id;
  }
}
