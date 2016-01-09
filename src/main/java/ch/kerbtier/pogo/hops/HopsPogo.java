package ch.kerbtier.pogo.hops;

import ch.kerbtier.hopsdb.Db;
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

}
