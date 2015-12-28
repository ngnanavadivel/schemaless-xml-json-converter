package com.datamorpher;

import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.junit.Test;
import org.xml.sax.InputSource;

import com.datamorpher.exception.ParserException;
import com.datamorpher.model.JSONGenerator;
import com.datamorpher.model.Property;
import com.datamorpher.parser.XmlParser;

public class XmlParserTest {

	@Test
	public void testParse() throws Exception {
		InputStream istream = Thread.currentThread().getContextClassLoader().getResourceAsStream("3.xml");
		InputSource source = new InputSource(new InputStreamReader(istream));
		try {
			Property<?> model = new XmlParser().parse(source);
			System.out.println(model);
			//System.out.println(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(model));
			JSONGenerator jsonGenerator = new JSONGenerator();
			model.accept(jsonGenerator);
			System.out.println(jsonGenerator.getJSON());
		} catch (ParserException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
		finally {
			if(istream != null) {
				try {
					istream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
