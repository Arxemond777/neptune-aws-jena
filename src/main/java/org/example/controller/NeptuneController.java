package org.example.controller;

import org.example.service.NeptuneRdfService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/neptune")
public class NeptuneController {

   private final NeptuneRdfService svc;

   public NeptuneController(NeptuneRdfService svc) {
      this.svc = svc;
   }

   @PostMapping("/seed")
   public Mono<String> seed() {
      return svc.insertSampleData();
   }

   @GetMapping("/friends/{name}")
   public Mono<String> friends(@PathVariable String name) {
      return svc.friendsOf(name);
   }

   @GetMapping("/fof/{name}")
   public Mono<String> fof(@PathVariable String name) {
      return svc.friendsOfFriends(name);
   }

   @DeleteMapping("/clear")
   public Mono<String> clear() {
      return svc.clearNamespace();
   }
}