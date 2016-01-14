package ch.kerbtier.pogo.hops;

import ch.kerbtier.pogo.PogoList;
import ch.kerbtier.pogo.hops.dao.DaoList;

public class HopsPogoList implements PogoList {

  private HopsPogo root;
  private DaoList dao;
  
  public HopsPogoList(HopsPogo root, DaoList dao) {
    this.root = root;
    this.dao = dao;
  }

  @Override
  public void set(int index, Object value) {

  }

  @Override
  public Object get(int index) {

    return null;
  }

  @Override
  public <X> X get(int index, Class<X> type) {

    return null;
  }

  @Override
  public void delete(int index) {

  }

  @Override
  public void delete() {

  }

}
