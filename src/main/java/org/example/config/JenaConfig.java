package org.example.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@Profile( "jena" )
public class JenaConfig {

   private final String LOGIN = "admin";
   private final String PASSWORD = "admin";

   @Bean
   WebClient sparqlUpdateClient(
         @Value( "${neptune.sparqlUpdateUrl}" ) String url
   ) {
      return WebClient.builder()
            .baseUrl( url )
            .defaultHeaders( h -> h.setBasicAuth( LOGIN, PASSWORD ) )
            .build();
   }

   @Bean
   WebClient sparqlQueryClient(
         @Value( "${neptune.sparqlQueryUrl}" ) String url
   ) {
      return WebClient.builder()
            .baseUrl( url )
            .defaultHeaders( h -> h.setBasicAuth( LOGIN, PASSWORD ) )
            .defaultHeader( HttpHeaders.ACCEPT, "application/sparql-results+json" )
            .build();
   }
}