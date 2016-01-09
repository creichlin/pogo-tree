package ch.kerbtier.pogo.hops;

import ch.kerbtier.hopsdb.Db;
import ch.kerbtier.pogo.PogoTransaction;

public class HopsPogoTransaction implements PogoTransaction {

  private Db db;
  
  public HopsPogoTransaction(Db db) {
    this.db = db;
    
  }

  @Override
  public void close() throws RuntimeException {
    commit();
  }

  @Override
  public void commit() {
    db.commit();
  }

  @Override
  public void rollback() {
    db.rollback();
  }
}
