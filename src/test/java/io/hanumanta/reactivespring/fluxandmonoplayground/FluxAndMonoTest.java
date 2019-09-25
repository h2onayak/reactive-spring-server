package io.hanumanta.reactivespring.fluxandmonoplayground;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class FluxAndMonoTest {
    @Test
    public void fluxTest(){
        Flux<String> stringFlux = Flux.just("Spring", "Spring Boot","Reactive Spring")
//                .concatWith(Flux.error(new RuntimeException("Exception occurred!")))
                .concatWith(Flux.just("After Error"))
                .log();
        stringFlux
                .subscribe(System.out::println,
                        (e)-> System.out.println("Exception is : "+e),
                        ()-> System.out.println("Completed."));
    }

    @Test
    public void fluxTestElements_WithoutError(){
        Flux<String> stringFlux = Flux.just("Spring", "Spring Boot","Reactive Spring")
                .log();

        StepVerifier.create(stringFlux)
                .expectNext("Spring")
                .expectNext("Spring Boot")
                .expectNext("Reactive Spring")
                .verifyComplete();
    }
    @Test
    public void fluxTestElements_WithoutError1(){
        Flux<String> stringFlux = Flux.just("Spring", "Spring Boot","Reactive Spring")
                .log();

        StepVerifier.create(stringFlux)
                .expectNext("Spring","Spring Boot","Reactive Spring")
                .verifyComplete();
    }

    @Test
    public void fluxTestElements_WithError() {
        Flux<String> stringFlux = Flux.just("Spring", "Spring Boot", "Reactive Spring")
                .concatWith(Flux.error(new RuntimeException("Exception occurred!")))
                .log();

        StepVerifier.create(stringFlux)
                .expectNext("Spring")
                .expectNext("Spring Boot")
                .expectNext("Reactive Spring")
               // .expectError(RuntimeException.class)
                .expectErrorMessage("Exception occurred!")
                .verify();
    }
    @Test
    public void fluxTestElementsCount_WithError() {
        Flux<String> stringFlux = Flux.just("Spring", "Spring Boot", "Reactive Spring")
                .concatWith(Flux.error(new RuntimeException("Exception occurred!")))
                .log();

        StepVerifier.create(stringFlux)
                .expectNextCount(3)
                // .expectError(RuntimeException.class)
                .expectErrorMessage("Exception occurred!")
                .verify();
    }

    @Test
    public void monoTest(){
        Mono<String> stringMono = Mono.just("spring");
        StepVerifier.create(stringMono.log())
                .expectNext("spring")
                .verifyComplete();
    }
    @Test
    public void monoTest_Error(){
        StepVerifier.create(Mono.error(new RuntimeException("Exception occurred!")).log())
                .expectError(RuntimeException.class)
                .verify();
    }
}
