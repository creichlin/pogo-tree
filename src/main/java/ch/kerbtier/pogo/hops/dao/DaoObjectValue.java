package ch.kerbtier.pogo.hops.dao;

import ch.kerbtier.hopsdb.model.annotations.Column;
import ch.kerbtier.hopsdb.model.annotations.Table;

@Table(name = "objectvalue")
public class DaoObjectValue extends DaoValue {
  
  @Column
  private String name;

  public DaoObjectValue(int parent, String name, int type) {
    super(parent, type);
    this.name = name;
  }
  
  public DaoObjectValue() {
    
  }
  
  public String getName() {
    return name;
  }

}
