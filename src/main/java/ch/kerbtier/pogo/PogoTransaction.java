package ch.kerbtier.pogo;

public interface PogoTransaction extends AutoCloseable {
  @Override
  void close() throws RuntimeException;
  void commit();
  void rollback();
}
