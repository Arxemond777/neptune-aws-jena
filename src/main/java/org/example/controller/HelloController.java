package org.example.controller;


import java.time.Duration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class HelloController {

   @GetMapping("/hello")
   public Mono<String> hello() {
      return Mono.just("hello webflux + netty");
   }

   @GetMapping("/stream")
   public Flux<Long> stream() {
      return Flux.interval(Duration.ofSeconds(1))
            .take(5);
   }
}