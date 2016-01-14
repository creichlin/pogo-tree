package ch.kerbtier.pogo;

public interface PogoList {
  void set(int index, Object value);

  Object get(int index);

  <X> X get(int index, Class<X> type);

  void delete(int index);

  void delete();
}
