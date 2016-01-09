package ch.kerbtier.pogo;

import java.util.Collection;

public interface PogoObject {
  void set(String field, Object value);

  Object get(String field);

  <X> X get(String field, Class<X> type);

  void delete(String field);

  void delete();

  Collection<String> getFields();
}
