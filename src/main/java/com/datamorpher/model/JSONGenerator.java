package com.datamorpher.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.datamorpher.util.RenderUtil;

public class JSONGenerator implements PropertyVisitor {

   private StringBuilder builder = new StringBuilder();

   public String getJSON() {
      return builder.toString();
   }

   public void visit(ComplexProperty cp) {
      extractJSONFromComplexProperty(cp, false);
   }

   public void visit(SimpleProperty sp) {
      extractJSONFromSimpleProperty(sp, false);
   }

   public void visit(SimplePropertyWithAttribs spwa) {
      extractJSONFromSimplePropertyWithAttribs(spwa, false);
   }

   private void extractJSON(Property<?> prop, boolean areTheyArrayElements) {
      if (prop instanceof SimplePropertyWithAttribs) {
         extractJSONFromSimplePropertyWithAttribs((SimplePropertyWithAttribs) prop, areTheyArrayElements);
      } else if (prop instanceof ComplexProperty) {
         extractJSONFromComplexProperty((ComplexProperty) prop, areTheyArrayElements);
      } else if (prop instanceof SimpleProperty) {
         extractJSONFromSimpleProperty((SimpleProperty) prop, areTheyArrayElements);
      }
   }

   private void extractJSONFromComplexProperty(ComplexProperty cp, boolean areTheyArrayElements) {
      if (!areTheyArrayElements) {
         builder.append(RenderUtil.encloseWithSingleQuotes(cp.getName()) + ": ");
      }

      if (cp.getValue() != null) {
         if (isAnArray(cp)) {
            // It's an array.
            builder.append("[");
            int i = 1;
            for (Property<?> prop : cp.getValue()) {
               builder.append("{");
               extractJSON(prop, true);
               builder.append("}");
               if (i++ < cp.getValue().size())
                  builder.append(",");
            }
            builder.append("]");
         } else {
            // It's an Object literal.
            if (!areTheyArrayElements) {
               builder.append(" {");
            }
            int i = 1;
            for (Property<?> prop : cp.getValue()) {
               extractJSON(prop, false);
               if (i++ < cp.getValue().size())
                  builder.append(",");
            }
            if (!areTheyArrayElements) {
               builder.append("}");
            }
         }
      }
   }

   private void extractJSONFromSimpleProperty(SimpleProperty sp, boolean areTheyArrayElements) {
      if (!areTheyArrayElements) {
         builder.append(RenderUtil.encloseWithSingleQuotes(sp.getName()));
         builder.append(": ");
      }
      builder.append(RenderUtil.encloseWithSingleQuotes(sp.getValue()));
   }

   private void extractJSONFromSimplePropertyWithAttribs(SimplePropertyWithAttribs spwa, boolean areTheyArrayElements) {
      if (!areTheyArrayElements) {
         builder.append(RenderUtil.encloseWithSingleQuotes(spwa.getName()));
         builder.append(": {");
      }
      List<Property<?>> attributes = spwa.getAttributes();
      for (Property<?> prop : attributes) {
         extractJSON(prop, false);
         builder.append(",");
      }
      builder.append(RenderUtil.encloseWithSingleQuotes("__value__") + ": ");
      builder.append(RenderUtil.encloseWithSingleQuotes(spwa.getValue()));
      if (!areTheyArrayElements) {
         builder.append("}");
      }
   }

   private boolean isAnArray(ComplexProperty cp) {
      Set<String> names = new HashSet<String>();
      List<Property<?>> props = cp.getValue();
      for (Property<?> prop : props) {
         names.add(prop.getName());
      }
      return props.size() > 1 && names.size() == 1;
   }
}
