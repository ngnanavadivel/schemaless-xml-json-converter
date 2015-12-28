package com.datamorpher.model;

import java.util.ArrayList;
import java.util.List;

public class ComplexProperty extends Property<List<Property<?>>> {

   public ComplexProperty(String name) {
      super(name);
   }

   @Override
   public void accept(PropertyVisitor visitor) {
      visitor.visit(this);
   }

   public void addProperties(List<Property<?>> properties) {
      if (this.value == null) {
         this.value = new ArrayList<Property<?>>();
      }
      this.value.addAll(properties);
   }

   public void addProperty(Property<?> property) {
      if (this.value == null) {
         this.value = new ArrayList<Property<?>>();
      }
      this.value.add(property);
   }

   @Override
   public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("ComplexProperty [name=");
      builder.append(name);
      builder.append(", value=");
      builder.append(value);
      builder.append("]");
      return builder.toString();
   }

}
