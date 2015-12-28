package com.datamorpher.model;

import java.util.ArrayList;
import java.util.List;

public class SimplePropertyWithAttribs extends SimpleProperty {
	
	public List<Property<?>> getAttributes() {
		return attributes;
	}

	private List<Property<?>> attributes;

	public SimplePropertyWithAttribs(String name, String value) {
		super(name, value);
	}
	
	public void addAttribute(SimpleProperty attrib) {
		if(this.attributes == null) {
			this.attributes = new ArrayList<Property<?>>();
		}
		this.attributes.add(attrib);
	}
	
	public void addAttributes(List<Property<?>> attributes) {
		if(this.attributes == null) {
			this.attributes = new ArrayList<Property<?>>();
		}
		this.attributes.addAll(attributes);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SimplePropertyWithAttribs [attributes=");
		builder.append(attributes);
		builder.append(", name=");
		builder.append(name);
		builder.append(", value=");
		builder.append(value);
		builder.append("]");
		return builder.toString();
	}

}
