package com.datamorpher.util;

import com.datamorpher.exception.ParserException;

public class ExceptionUtil {
	public static void wrapAndThrowAsParserException(Throwable cause) throws ParserException {
		throw new ParserException(cause);
	}
}
