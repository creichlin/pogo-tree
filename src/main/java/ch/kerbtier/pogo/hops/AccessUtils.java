package ch.kerbtier.pogo.hops;

import java.sql.SQLException;

import ch.kerbtier.pogo.PogoType;
import ch.kerbtier.pogo.hops.dao.DaoList;
import ch.kerbtier.pogo.hops.dao.DaoObject;
import ch.kerbtier.pogo.hops.dao.DaoValue;

public class AccessUtils {
  
  public static void setValueDao(HopsPogo root, Object value, PogoType type, DaoValue valueDao) throws SQLException, AssertionError {
    if (type == PogoType.STRING) {
      valueDao.setString((String) value);
      
    } else if (type == PogoType.INTEGER) {
      valueDao.setInteger((Integer) value);
      
    } else if (type == PogoType.NULL) {
      // no value to set when null
      
    } else if (type == PogoType.OBJECT) {
      DaoObject newObject = new DaoObject();
      root.getDb().create(newObject);
      valueDao.setInteger(newObject.getId());
      
    } else if (type == PogoType.LIST) {
      DaoList newList = new DaoList();
      root.getDb().create(newList);
      valueDao.setInteger(newList.getId());
      
    } else {
      throw new AssertionError();
    }
  }

}
