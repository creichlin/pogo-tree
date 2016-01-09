package ch.kerbtier.pogo.hops.types.operations;

import ch.kerbtier.pogo.hops.HopsPogo;
import ch.kerbtier.pogo.hops.HopsPogoObject;
import ch.kerbtier.pogo.hops.types.TypeOperation;

public class PogoObjectOperation implements TypeOperation {

  @Override
  public Object newInstance() {
    return null;
  }

  @Override
  public Object pack(HopsPogo root, Object value) {
    return new HopsPogoObject(root, null);
  }

}
