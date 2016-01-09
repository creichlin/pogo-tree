package ch.kerbtier.pogo.hops.types;

import java.util.HashMap;
import java.util.Map;

import ch.kerbtier.pogo.PogoObject;
import ch.kerbtier.pogo.hops.types.operations.EmptyOperation;
import ch.kerbtier.pogo.hops.types.operations.PogoObjectOperation;

public class TypeOperations {
  
  private static TypeOperation empty = new EmptyOperation();
  private static Map<Class, TypeOperation> types = new HashMap<>();
  
  
  static {
    types.put(PogoObject.class, new PogoObjectOperation());
  }

  public static TypeOperation getFor(Class type) {
    TypeOperation operation = types.get(type);
    if(operation == null) {
      return empty;
    }
    return operation;
  }
  
}
