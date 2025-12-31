package org.example.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
public class NeptuneRdfService {

   private final String ns;

   private final RdfClient rdfCleint;

   public NeptuneRdfService( RdfClient rdfCleint, @Value( "${neptune.namespace}" ) String namespace ) {
      this.rdfCleint = rdfCleint;
      this.ns = namespace;
   }

   public Mono<String> insertSampleData() {
      String update = """
            PREFIX : <%s>
            INSERT DATA {
              :Alice :knows :Bob .
              :Bob :knows :Carol .
              :Alice :likes "GraphDB" .
              :Bob :likes "Cats" .
            }
            """.formatted( ns );

      return rdfCleint.update( update );
   }

   public Mono<String> friendsOf( String personLocalName ) {
      String query = """
            PREFIX : <%s>
            SELECT ?friend WHERE {
              :%s :knows ?friend .
            }
            """.formatted( ns, personLocalName );

      System.out.println( query );

      return rdfCleint.query( query );
   }

   public Mono<String> friendsOfFriends( String personLocalName ) {
      String query = """
            PREFIX : <%s>
            SELECT DISTINCT ?fof WHERE {
              :%s :knows ?x .
              ?x :knows ?fof .
              FILTER(?fof != :%s)
            }
            """.formatted( ns, personLocalName, personLocalName );

      return rdfCleint.query( query );
   }

   public Mono<String> clearNamespace() {
      String update = """
            DELETE WHERE {
              ?s ?p ?o .
              FILTER(STRSTARTS(STR(?s), "%s"))
            }
            """.formatted( ns );

      return rdfCleint.update( update );
   }
}