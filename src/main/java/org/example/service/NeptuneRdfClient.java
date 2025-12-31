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
@Profile( "neptune" )
public class NeptuneRdfClient implements RdfClient {

   private final WebClient client;

   public NeptuneRdfClient( WebClient neptuneWebClient ) {
      this.client = neptuneWebClient;
   }

   @Override
   public Mono<String> update( String sparql ) {
      return post( "update", sparql );
   }

   @Override
   public Mono<String> query( String sparql ) {
      return post( "query", sparql );
   }

   private Mono<String> post( String key, String value ) {
      MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
      form.add( key, value );

      return client.post()
            .contentType( MediaType.APPLICATION_FORM_URLENCODED )
            .bodyValue( form )
            .retrieve()
            .bodyToMono( String.class );
   }
}