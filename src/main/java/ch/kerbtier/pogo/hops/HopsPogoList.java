package ch.kerbtier.pogo.hops;

import java.sql.SQLException;
import java.util.List;

import ch.kerbtier.pogo.PogoList;
import ch.kerbtier.pogo.PogoType;
import ch.kerbtier.pogo.exceptions.PogoException;
import ch.kerbtier.pogo.hops.dao.DaoList;
import ch.kerbtier.pogo.hops.dao.DaoListValue;

public class HopsPogoList implements PogoList {

  private HopsPogo root;
  private DaoList dao;
  private List<DaoListValue> values_ = null;

  public HopsPogoList(HopsPogo root, DaoList dao) {
    this.root = root;
    this.dao = dao;
  }

  @Override
  public void add(Object value) {
    PogoType type = PogoType.of(value);
    List<DaoListValue> values = getValues();
    DaoListValue newValue = new DaoListValue(dao.getId(), values.size(), type.getId());
    try {
      root.getDb().create(newValue);
      AccessUtils.setValueDao(root, value, type, newValue);
      values.add(newValue);
    } catch (SQLException e) {
      throw new PogoException(e);
    }
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

  @Override
  public int size() {
    return getValues().size();
  }

  private List<DaoListValue> getValues() {
    if (values_ == null) {
      try {
        values_ = root.getDb().select(DaoListValue.class).where("parent = ?", dao.getId()).orderBy("index").listAll();
        System.out.println(values_);
      } catch (SQLException e) {
        throw new PogoException(e);
      }
    }
    return values_;
  }

  // groovy operators

  public void leftShift(Object value) {
    add(value);
  }

}
