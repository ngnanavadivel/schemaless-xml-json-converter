package com.datamorpher.parser;

import org.xml.sax.InputSource;

import com.datamorpher.exception.ParserException;
import com.datamorpher.model.Property;

public interface Parser {
	Property<?> parse(InputSource source) throws ParserException;
}
