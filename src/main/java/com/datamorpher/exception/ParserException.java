package com.datamorpher.exception;

public class ParserException extends Exception {
   private static final long serialVersionUID = -7390375667629228136L;

   public ParserException(String message) {
      super(message);
   }

   public ParserException(String message, Throwable cause) {
      super(message, cause);
   }

   public ParserException(Throwable cause) {
      super(cause);
   }
}
