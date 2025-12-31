package org.example.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@Profile("neptune")
public class NeptuneConfig {

   @Bean
   WebClient neptuneWebClient(
         WebClient.Builder builder,
         @Value("${neptune.sparqlUrl}") String sparqlUrl
   ) {
      return builder
            .baseUrl(sparqlUrl)
            .defaultHeader(HttpHeaders.ACCEPT, "application/sparql-results+json")
            .build();
   }
}