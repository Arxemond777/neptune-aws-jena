package org.example.service;

import reactor.core.publisher.Mono;

public interface RdfClient {
   Mono<String> update(String sparql);
   Mono<String> query(String sparql);
}
