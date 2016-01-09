package ch.kerbtier.pogo.hops.types;


import ch.kerbtier.pogo.hops.HopsPogo;


public interface TypeOperation {
  Object newInstance();
  Object pack(HopsPogo root, Object value);
}
