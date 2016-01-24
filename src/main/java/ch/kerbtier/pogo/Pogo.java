package ch.kerbtier.pogo;

public interface Pogo extends PogoObject {

  PogoTransaction start();
  
  String dumpSource();

  PogoObject getObjectByField(String field, Object value);
  
}
