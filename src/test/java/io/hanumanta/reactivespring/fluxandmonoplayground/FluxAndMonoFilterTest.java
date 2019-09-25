package io.hanumanta.reactivespring.fluxandmonoplayground;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

public class FluxAndMonoFilterTest {
    List<String> names = Arrays.asList("Arun", "Shiv", "Nayak");

    @Test
    public void filterTest() {
        Flux<String> namesFlux = Flux.fromIterable(names)
                .filter(s -> s.startsWith("A"))
                .log();
        StepVerifier.create(namesFlux)
                .expectNext("Arun")
                .verifyComplete();
    }

    @Test
    public void verifyTestLength(){
        Flux<String> namesFlux =  Flux.fromIterable(names)
                .filter(s->s.length()>4)
                .log();
        StepVerifier.create(namesFlux)
                .expectNext("Nayak")
                .verifyComplete();
    }
}
