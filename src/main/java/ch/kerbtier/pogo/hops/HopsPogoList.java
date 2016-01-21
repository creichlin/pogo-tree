package ch.kerbtier.pogo.hops;

import java.sql.SQLException;
import java.util.List;

import ch.kerbtier.hopsdb.DbPs;
import ch.kerbtier.pogo.PogoList;
import ch.kerbtier.pogo.PogoType;
import ch.kerbtier.pogo.exceptions.PogoException;
import ch.kerbtier.pogo.hops.dao.DaoList;
import ch.kerbtier.pogo.hops.dao.DaoListValue;
import ch.kerbtier.pogo.hops.dao.DaoObject;
import ch.kerbtier.pogo.hops.dao.DaoObjectValue;
import ch.kerbtier.pogo.hops.dao.DaoValue;

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
      AccessUtils.setValueDao(root, value, type, newValue);
      root.getDb().create(newValue);
      values.add(newValue);
    } catch (SQLException e) {
      throw new PogoException(e);
    }
  }

  @Override
  public Object get(int index) {
    DaoValue value = getValues().get(index);
    try {
      return AccessUtils.readValue(root, value);
    } catch (SQLException e) {
      throw new PogoException(e);
    }
  }

  @SuppressWarnings("unchecked")
  @Override
  public <X> X get(int index, Class<X> type) {
    return (X)get(index);
  }

  @Override
  public void delete(int index) {
      try {
        int size = size();
        root.getDb().delete(getValues().get(index));
        getValues().remove(index);
        
        if(index < size - 1) {
          DbPs update = root.getDb().prepareStatement("update listvalue set index = index - 1 where parent = ? and index > ?");
          update.setParameter(1, dao.getId());
          update.setParameter(2, index);
          update.executeUpdate();
        }
      } catch (SQLException e) {
        throw new PogoException(e);
      }
  }

  @Override
  public void delete() {
    try {
      DaoObjectValue fieldDao = root.getDb().select(DaoObjectValue.class)
          .where("integer = ? AND type = ?", dao.getId(), PogoType.LIST.getId()).first();

      DaoObject parentObject = root.getDb().select(DaoObject.class).byPk(fieldDao.getParent()).first();

      new HopsPogoObject(root, parentObject).delete(fieldDao.getName());
    } catch (SQLException e) {
      throw new PogoException(e);
    }
  }

  @Override
  public int size() {
    return getValues().size();
  }

  private List<DaoListValue> getValues() {
    if (values_ == null) {
      try {
        values_ = root.getDb().select(DaoListValue.class).where("parent = ?", dao.getId()).orderBy("index").listAll();
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

  public Object getAt(int index) {
    return get(index);
  }

}
