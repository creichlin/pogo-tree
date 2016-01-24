package ch.kerbtier.pogo.hops;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ch.kerbtier.hopsdb.exceptions.NoMatchFound;
import ch.kerbtier.pogo.PogoObject;
import ch.kerbtier.pogo.PogoType;
import ch.kerbtier.pogo.exceptions.NoSuchField;
import ch.kerbtier.pogo.exceptions.PogoException;
import ch.kerbtier.pogo.hops.dao.DaoList;
import ch.kerbtier.pogo.hops.dao.DaoObject;
import ch.kerbtier.pogo.hops.dao.DaoObjectValue;

public class HopsPogoObject implements PogoObject {

  private HopsPogo root;
  private DaoObject dao;

  protected HopsPogoObject(DaoObject dao) {
    this.dao = dao;
  }

  public HopsPogoObject(HopsPogo root, DaoObject dao) {
    this.root = root;
    this.dao = dao;
  }

  @Override
  public void set(String field, Object value) {
    PogoType type = PogoType.of(value);
    try {
      DaoObjectValue valueDao = null;
      try {
        valueDao = root.getDb().select(DaoObjectValue.class).where("parent = ? and name = ?", dao.getId(), field)
            .first();
      } catch (NoMatchFound e) {
        // no value yet, create one
        valueDao = new DaoObjectValue(dao.getId(), field, type.getId());
        root.getDb().create(valueDao);
      }
      valueDao.setType(type.getId());
      AccessUtils.setValueDao(root, value, type, valueDao);
      
      root.getDb().update(valueDao);
      
    } catch (SQLException e) {
      throw new PogoException(e);
    }
  }

  @Override
  public Object get(String field) {
    try {
      DaoObjectValue value = root.getDb().select(DaoObjectValue.class)
          .where("parent = ? and name = ?", dao.getId(), field).first();
      return AccessUtils.readValue(root, value);
      
    } catch (NoMatchFound e) {
      throw new NoSuchField(field);
    } catch (SQLException e) {
      throw new PogoException(e);
    }
  }

  @SuppressWarnings("unchecked")
  @Override
  public <X> X get(String field, Class<X> type) {
    return (X) get(field);
  }

  @Override
  public void delete() {
    try {
      DaoObjectValue fieldDao = root.getDb().select(DaoObjectValue.class)
          .where("integer = ? AND type = ?", dao.getId(), PogoType.OBJECT.getId()).first();

      DaoObject parentObject = root.getDb().select(DaoObject.class).byPk(fieldDao.getParent()).first();

      new HopsPogoObject(root, parentObject).delete(fieldDao.getName());
    } catch (SQLException e) {
      throw new PogoException(e);
    }
  }

  @Override
  public void delete(String field) {
    try {
      try {
        DaoObjectValue value = root.getDb().select(DaoObjectValue.class)
            .where("parent = ? and name = ?", dao.getId(), field).first();
        PogoType type = PogoType.byId(value.getType());
        if (type.isPrimitive()) {
          root.getDb().delete(value);
          
        } else if (type == PogoType.OBJECT) {
          HopsPogoObject subObject = get(field, HopsPogoObject.class);

          for (String subField : subObject.getFields()) {
            subObject.delete(subField);
          }
          DaoObject object = root.getDb().select(DaoObject.class).byPk(value.getInteger()).first();
          root.getDb().delete(object);
          root.getDb().delete(value);

        } else if (type == PogoType.LIST) {
          HopsPogoList subList = get(field, HopsPogoList.class);

          while (subList.size() > 0) {
            subList.delete(subList.size() - 1);
          }
          DaoList object = root.getDb().select(DaoList.class).byPk(value.getInteger()).first();
          root.getDb().delete(object);
          root.getDb().delete(value);
        
        } else {
          throw new AssertionError("unknown type " + type);
        }
      } catch (NoMatchFound e) {
        throw new AssertionError(e);
      }
    } catch (SQLException e) {
      throw new PogoException(e);
    }
  }

  protected void setRoot(HopsPogo root) {
    this.root = root;
  }

  @Override
  public Collection<String> getFields() {
    try {
      List<String> fields = new ArrayList<>();
      for (DaoObjectValue value : root.getDb().select(DaoObjectValue.class).where("parent = ?", dao.getId()).listAll()) {
        fields.add(value.getName());
      }
      
      return fields;
    } catch (SQLException e) {
      throw new PogoException(e);
    }
  }
}
