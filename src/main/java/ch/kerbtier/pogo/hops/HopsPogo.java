package ch.kerbtier.pogo.hops;

import java.sql.SQLException;

import ch.kerbtier.hopsdb.Db;
import ch.kerbtier.hopsdb.DbPs;
import ch.kerbtier.hopsdb.DbRs;
import ch.kerbtier.pogo.Pogo;
import ch.kerbtier.pogo.PogoTransaction;
import ch.kerbtier.pogo.hops.dao.DaoObject;

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

}
