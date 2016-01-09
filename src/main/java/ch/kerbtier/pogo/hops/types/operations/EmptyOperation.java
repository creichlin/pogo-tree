package ch.kerbtier.pogo.hops.types.operations;


import ch.kerbtier.pogo.hops.HopsPogo;
import ch.kerbtier.pogo.hops.types.TypeOperation;

public class EmptyOperation implements TypeOperation {

  @Override
  public Object newInstance() {
    throw new RuntimeException("trying to create invalid type");
  }

  @Override
  public Object pack(HopsPogo root, Object value) {
    return value;
  }

}
