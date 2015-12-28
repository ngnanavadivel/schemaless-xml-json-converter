package com.datamorpher.model;

public interface PropertyVisitor {
   void visit(ComplexProperty cp);

   void visit(SimpleProperty sp);

   void visit(SimplePropertyWithAttribs spwa);
}
