package com.datamorpher.parser;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.InputSource;

import com.datamorpher.exception.ParserException;
import com.datamorpher.model.Property;
import com.datamorpher.util.ExceptionUtil;

public class XmlParser implements Parser {

   private static final Logger logger = LoggerFactory.getLogger(XmlParser.class);

   public Property<?> parse(InputSource source) throws ParserException {
      logger.info("Started parsing XML content.");
      Property<?> model = null;
      if (source == null) {
         throw new IllegalArgumentException("Argument 'source' is null.");
      }
      try {
         SAXParserFactory factory = SAXParserFactory.newInstance();
         SAXParser saxparser = factory.newSAXParser();
         XmlHandler handler = new XmlHandler();
         saxparser.parse(source, handler);
         logger.info("Parsing Completed.");
         model = handler.getModel();
      } catch (Throwable t) {
         ExceptionUtil.wrapAndThrowAsParserException(t);
      }
      return model;
   }

}
