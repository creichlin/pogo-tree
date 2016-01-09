package ch.kerbtier.pogo.hops;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

import org.apache.commons.io.IOUtils;

import ch.kerbtier.hopsdb.Db;
import ch.kerbtier.hopsdb.DbPs;
import ch.kerbtier.hopsdb.exceptions.NoMatchFound;
import ch.kerbtier.hopsdb.model.annotations.AnnotationModelProvider;
import ch.kerbtier.pogo.Pogo;
import ch.kerbtier.pogo.PogoFactory;
import ch.kerbtier.pogo.hops.dao.DaoObject;

public class HopsPogoFactory implements PogoFactory {

  private String driver;
  private String url;
  
  
  public HopsPogoFactory(String driver, String url) {
    this.driver = driver;
    this.url = url;
  }
  
  
  @Override
  public Pogo create() {
    
    Db db = null;
    
    try {
      Class.forName(driver);
      
      db = new Db(url, new AnnotationModelProvider());
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    }

    try {
      if (!db.hasTables("object")) {
        
        try {
          InputStream is = HopsPogoFactory.class.getResourceAsStream("tables.sql");
          String sql = IOUtils.toString(is);
          is.close();

          db.prepareStatement(sql).executeUpdate();
          db.commit();
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
      }

      DaoObject rootDao = null;
      try {
        rootDao = db.select(DaoObject.class, 0);
        db.commit();
      } catch (NoMatchFound e) {
        DbPs ps = db.prepareStatement("insert into object (id) values(0);");
        ps.executeUpdate();
        db.commit();
        rootDao = db.select(DaoObject.class, 0);
        db.commit();
      }
      return new HopsPogo(db, rootDao);
    } catch (SQLException e) {
      db.rollback();
      try {
        System.out.println("Available tables: " + db.getTableNames());
      } catch (SQLException e1) {
        e1.printStackTrace();
      }
      throw new RuntimeException(e);
    }
  }

}
