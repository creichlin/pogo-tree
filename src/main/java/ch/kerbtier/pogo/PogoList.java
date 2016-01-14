package ch.kerbtier.pogo;

public interface PogoList {
  void add(Object value);

  Object get(int index);

  <X> X get(int index, Class<X> type);

  int size();
  
  void delete(int index);

  void delete();
}
