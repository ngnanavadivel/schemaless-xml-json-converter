package com.datamorpher.model;

public class SimpleProperty extends Property<String> {

   public SimpleProperty(String name, String value) {
      super(name, value);
   }

   @Override
   public void accept(PropertyVisitor visitor) {
      visitor.visit(this);
   }

   @Override
   public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("SimpleProperty [name=");
      builder.append(name);
      builder.append(", value=");
      builder.append(value);
      builder.append("]");
      return builder.toString();
   }

}
