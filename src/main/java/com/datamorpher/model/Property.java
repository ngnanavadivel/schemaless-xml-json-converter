package com.datamorpher.model;

public abstract class Property<T> {

   // To capture the name of the tag in case of XML.
   String name;

   // It would be a String in case of Simple property like
   // <book-name>Thought Works!</book-name>
   //
   // It could be a List of Property<T> itself in order to represent complex
   // types of XML.
   //
   // For example
   // <books>
   // <book>
   // <name>Alice in the wonderland!</name>
   // <book>
   // </books>
   T value;

   public Property(String name) {
      this.name = name;
   }

   public Property(String name, T value) {
      this.name = name;
      this.value = value;
   }

   public abstract void accept(PropertyVisitor visitor);

   public String getName() {
      return name;
   }

   public T getValue() {
      return value;
   }

   public void setName(String name) {
      this.name = name;
   }

   public void setValue(T value) {
      this.value = value;
   }

}
