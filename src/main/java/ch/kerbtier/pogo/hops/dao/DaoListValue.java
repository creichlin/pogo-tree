package ch.kerbtier.pogo.hops.dao;


import ch.kerbtier.hopsdb.model.annotations.Column;
import ch.kerbtier.hopsdb.model.annotations.Table;

@Table(name = "listvalue")
public class DaoListValue extends DaoValue {

  @Column
  private int index;

  public DaoListValue(int parent, int index, int type) {
    super(parent, type);
    this.index = index;
  }
  
  public DaoListValue() {
    
  }

  public int getIndex() {
    return index;
  }

  public void setIndex(int index) {
    this.index = index;
  }
  
  public String toString() {
    return "<element#" + getId() + "@" + index + " type:" + getType() + ">";
  }
}
