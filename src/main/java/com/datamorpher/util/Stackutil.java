package com.datamorpher.util;

import java.util.Stack;

import com.datamorpher.model.Property;

public class Stackutil {
   public static void print(String name, Stack<?> stack) {
      if (stack != null && !stack.isEmpty()) {
         System.out.println("\n\n\t( " + name + " )");
         int size = stack.size();
         for (int i = size - 1; i >= 0; --i) {
            System.out.println("|__________________________|");
            System.out.println("| " + stack.get(i));
         }
         if (!stack.isEmpty())
            System.out.println("|__________________________|\n\n");
      }
   }

   public static void printEx(String name, Stack<Property<?>> stack) {
      if (stack != null && !stack.isEmpty()) {
         System.out.println("\n\n\t( " + name + " )");
         int size = stack.size();
         for (int i = size - 1; i >= 0; --i) {
            System.out.println("|________________________________________|");
            System.out.println("| Ref. of (" + stack.get(i).getName() + ")");
         }
         if (!stack.isEmpty())
            System.out.println("|________________________________________|\n\n");
      }
   }
}
