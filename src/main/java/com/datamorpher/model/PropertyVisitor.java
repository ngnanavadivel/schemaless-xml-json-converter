package com.datamorpher.model;

public interface PropertyVisitor {
	void visit(SimpleProperty sp);
	void visit(ComplexProperty cp);
	void visit(SimplePropertyWithAttribs spwa);
}
