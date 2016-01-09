package ch.kerbtier.pogo.util;

import java.util.Properties;

import org.codehaus.groovy.runtime.StackTraceUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import ch.kerbtier.pogo.Pogo;
import ch.kerbtier.pogo.PogoTransaction;
import ch.kerbtier.pogo.hops.HopsPogoFactory;

public class H2Test {

  private static int num;

  private Pogo pogo;

  @Before
  public void setUp() {

    Properties p = new Properties(System.getProperties());
    p.put("com.mchange.v2.log.MLog", "com.mchange.v2.log.FallbackMLog");
    p.put("com.mchange.v2.log.FallbackMLog.DEFAULT_CUTOFF_LEVEL", "OFF"); // Off or any other level
    System.setProperties(p);


    HopsPogoFactory factory = new HopsPogoFactory("org.h2.Driver", "jdbc:h2:mem:m" + (num++)
        + ";USER=test;PASSWORD=test;DB_CLOSE_DELAY=-1");
    pogo = factory.create();
  }

  public Pogo getPogo() {
    return pogo;
  }

  public PogoTransaction start() {
    return pogo.start();
  }

  public void transaction(body) {
    PogoTransaction transaction = pogo.start();

    try {
      body(transaction);
      transaction.commit();
    }catch(Exception e) {
      transaction.rollback();
      throw e;
    }
  }

  @Rule
  public TestWatcher watchman = new TestWatcher() {
    @Override
    protected void failed(Throwable e, Description description) {
      // System.out.println("print some status infos, sql dump or whatever");
    }

    @Override
    protected void succeeded(Description description) {
    }
  };
}
