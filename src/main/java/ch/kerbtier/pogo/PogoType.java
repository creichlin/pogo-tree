package ch.kerbtier.pogo;

public enum PogoType {
  STRING(1, true), INTEGER(2, true), NULL(3, true), OBJECT(4, false), LIST(5, false);
  
  private int id;
  private boolean primitive;
  
  PogoType(int id, boolean primitive) {
    this.id = id;
    this.primitive = primitive;
  }
  
  public int getId() {
    return id;
  }

  public boolean isPrimitive() {
    return primitive;
  }

  public static PogoType of(Object value) {
    if(value == null) {
      return NULL;
    } else if(value instanceof String) {
      return STRING;
    } else if(value instanceof Integer) {
      return INTEGER;
    } else if(value instanceof Class) {
      Class classType = (Class)value;
      
      if(classType.isAssignableFrom(PogoObject.class)) {
        return OBJECT;
      } else if(classType.isAssignableFrom(PogoList.class)) {
        return LIST;
      }
    }
    
    throw new RuntimeException("unknown type " + value);
  }

  public static PogoType byId(int id) {
    for(PogoType type: PogoType.values()) {
      if(type.getId() == id) {
        return type;
      }
    }
    throw new RuntimeException("invalid type id " + id);
  }
}
