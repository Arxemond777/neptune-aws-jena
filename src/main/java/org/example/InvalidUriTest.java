package org.example;

import java.net.URISyntaxException;

public class InvalidUriTest {
   public static void main(final String[] args) {
      final String uri = "mtt://aaaa   f.com/bbbb1"; // 3 spaces

      try {
         new java.net.URI(uri);
         System.out.println("Parsed successfully (unexpected)");
      } catch (final Exception e) {
         System.out.println("Exception class: " + e.getClass().getName());
         System.out.println("Raw message    : \"" + e.getMessage() + "\"");
         System.out.println("Raw message    : \"" + ((URISyntaxException)e).getInput() + "\"");  // 3 space in java 21; 1 spaces in java 25 -> jdk.internal.util.Exceptions.trim
         System.out.println("Raw message    : \"" + ((URISyntaxException)e).getReason() + "\"");

         if (e instanceof java.net.URISyntaxException) {
            final URISyntaxException use = (URISyntaxException) e;
            System.out.println("Index reported : " + use.getIndex());
         }
      }
   }
}
