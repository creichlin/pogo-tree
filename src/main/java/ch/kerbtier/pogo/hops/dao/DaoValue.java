package ch.kerbtier.pogo.hops.dao;

import ch.kerbtier.hopsdb.model.annotations.Column;

public abstract class DaoValue extends DaoBase {

  @Column
  private int parent;

  @Column
  private int type;

  @Column
  private String string;

  @Column
  private int integer;

  public DaoValue(int parent, int type) {
    this.parent = parent;
    this.type = type;
  }

  public DaoValue() {

  }

  public int getParent() {
    return parent;
  }

  public int getType() {
    return type;
  }
  
  public void setType(int type) {
    this.type = type;
  }

  public String getString() {
    return string;
  }

  public void setString(String string) {
    this.string = string;
  }

  public int getInteger() {
    return integer;
  }

  public void setInteger(int integer) {
    this.integer = integer;
  }
}
