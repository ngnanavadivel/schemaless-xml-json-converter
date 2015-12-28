package com.datamorpher.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.datamorpher.model.ComplexProperty;
import com.datamorpher.model.Property;
import com.datamorpher.model.SimpleProperty;
import com.datamorpher.model.SimplePropertyWithAttribs;

public class XmlHandler extends DefaultHandler {

	private static final Logger logger = LoggerFactory.getLogger(XmlHandler.class);

	private Stack<String> stack = new Stack<String>();

	private Stack<String> complexTypes = new Stack<String>();

	private Stack<Property<?>> references = new Stack<Property<?>>();

	private String currentTagValue;

	private Property<?> currentProperty = null;

	private List<Property<?>> prevAttributes = null;

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

		String valueFromStack = !stack.isEmpty()
													? stack.peek()
													: null;

		if (valueFromStack == null) {
			// This must be the root element.
			stack.push(qName);
		} else {
			// Change in the level.
			if (!qName.equals(valueFromStack)) {
				Property<?> parent = null;
				if (!references.isEmpty()) {
					parent = references.peek();
				}
				this.currentProperty = new ComplexProperty(valueFromStack);
				if (prevAttributes != null) {
					((ComplexProperty) this.currentProperty).addProperties(prevAttributes);
					prevAttributes = null;
				}
				references.push(currentProperty);
				// Link the new complex type with the parent if any exists.
				if (parent != null) {
					((ComplexProperty) parent).addProperty(this.currentProperty);
				}
				// remove the current tag that is in temp. stack and push it to
				// the complex types stack.
				stack.pop();
				complexTypes.push(valueFromStack);

				stack.push(qName);
			}
		}

		if (attributes != null) {
			int count = attributes.getLength();
			if (count > 0) {
				prevAttributes = new ArrayList<Property<?>>();
				for (int i = 0; i < count; ++i) {
					String attrName = attributes.getQName(i);
					String attrValue = attributes.getValue(i);
					prevAttributes.add(new SimpleProperty(attrName, attrValue));
				}
			}
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		String valueFromStack = !stack.isEmpty()
													? stack.peek()
													: null;

		if (qName.equals(valueFromStack)) {
			SimpleProperty simpleProperty = null;

			if (prevAttributes != null) {
				simpleProperty = new SimplePropertyWithAttribs(valueFromStack, this.currentTagValue);

				((SimplePropertyWithAttribs) simpleProperty).addAttributes(prevAttributes);
				prevAttributes = null;
			} else {
				simpleProperty = new SimpleProperty(valueFromStack, this.currentTagValue);
			}

			this.currentProperty = references.peek();

			// last one should be a simple property
			if (this.currentProperty == null) {
				this.currentProperty = simpleProperty;
			} else if (this.currentProperty instanceof ComplexProperty) {
				((ComplexProperty) this.currentProperty).addProperty(simpleProperty);
			}
			stack.pop();
		}

		if (!complexTypes.isEmpty() && complexTypes.peek().equals(qName)) {
			if (references.size() == 1) {
				this.currentProperty = references.peek();
			}
			complexTypes.pop();
			references.pop();
		}
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		this.currentTagValue = new String(ch, start, length).trim();
	}

	public Property<?> getModel() {
		return this.currentProperty;
	}

}