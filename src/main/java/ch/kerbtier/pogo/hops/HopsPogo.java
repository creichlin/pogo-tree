package ch.kerbtier.pogo.hops;

import java.sql.SQLException;

import ch.kerbtier.hopsdb.Db;
import ch.kerbtier.hopsdb.DbPs;
import ch.kerbtier.hopsdb.DbRs;
import ch.kerbtier.hopsdb.exceptions.NoMatchFound;
import ch.kerbtier.pogo.Pogo;
import ch.kerbtier.pogo.PogoObject;
import ch.kerbtier.pogo.PogoTransaction;
import ch.kerbtier.pogo.PogoType;
import ch.kerbtier.pogo.exceptions.NoSuchField;
import ch.kerbtier.pogo.exceptions.PogoException;
import ch.kerbtier.pogo.hops.dao.DaoObject;
import ch.kerbtier.pogo.hops.dao.DaoObjectValue;

public class HopsPogo extends HopsPogoObject implements Pogo {

  private Db db;
  
  public HopsPogo(Db db, DaoObject rootDao) {
    super(rootDao);
    this.db = db;
    setRoot(this);
  }

  @Override
  public PogoTransaction start() {
    return new HopsPogoTransaction(db);
  }

  public Db getDb() {
    return db;
  }

  @Override
  public String dumpSource() {
    try {
      DbPs ps = db.prepareStatement("SCRIPT;");
      DbRs rs = ps.executeQuery();
      
      String script = "";
      while(rs.next()) {
        script += rs.get(1, String.class) + "\n";
      }
      return script;
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public PogoObject getObjectByField(String field, Object value) {
    try {
      PogoType type = PogoType.of(value);
      
      
      DaoObjectValue valueDao = db.select(DaoObjectValue.class).where("name = ? and " + fieldOf(type) + " = ?", field, value).first();
      DaoObject obj = db.select(DaoObject.class).byPk(valueDao.getParent()).first();
      
      return new HopsPogoObject(this, obj);
    } catch(NoMatchFound e) {
      throw new NoSuchField("no field " + field + " with value " + value);
    }catch(SQLException e) {
      throw new PogoException(e);
    }
  }

  private String fieldOf(PogoType type) {
    if(type == PogoType.STRING) {
      return "string";
    } else {
      throw new AssertionError();
    }
  }
}
