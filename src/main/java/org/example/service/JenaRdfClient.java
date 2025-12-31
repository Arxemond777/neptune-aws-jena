package org.example.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@Service
@Profile( "jena" )
public class JenaRdfClient implements RdfClient {

   private final WebClient update;
   private final WebClient query;

   public JenaRdfClient(
         @Qualifier( "sparqlUpdateClient" ) WebClient update,
         @Qualifier( "sparqlQueryClient" ) WebClient query
   ) {
      this.update = update;
      this.query = query;
   }

   @Override
   public Mono<String> update( String sparql ) {
      MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
      form.add( "update", sparql );

      return update.post()
            .contentType( MediaType.APPLICATION_FORM_URLENCODED )
            .bodyValue( form )
            .retrieve()
            .bodyToMono( String.class );
   }

   @Override
   public Mono<String> query( String sparql ) {
      MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
      form.add( "query", sparql );

      return query.post()
            .contentType( MediaType.APPLICATION_FORM_URLENCODED )
            .bodyValue( form )
            .retrieve()
            .bodyToMono( String.class );
   }
}