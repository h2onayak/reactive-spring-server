package io.hanumanta.reactivespring.fluxandmonoplayground;

import org.junit.Test;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class ColdAndHotPublisherTest {
    @Test
    public void coldPublisherTest() throws InterruptedException {
        Flux<String> stringFlux = Flux.just("A", "B", "C", "D", "E", "F")
                .delayElements(Duration.ofSeconds(1));
        stringFlux.subscribe(s -> System.out.println("Subscribe 1: " + s));//Emits the value from beginning.
        Thread.sleep(2000);
        stringFlux.subscribe(s -> System.out.println("Subscribe 2: " + s));//Emits the value from beginning.
        Thread.sleep(4000);
    }

    @Test
    public void hotPublisherTest() throws InterruptedException {
        Flux<String> stringFlux = Flux.just("A", "B", "C", "D", "E", "F")
                .delayElements(Duration.ofSeconds(1));
        ConnectableFlux<String>connectableFlux = stringFlux.publish();
        connectableFlux.connect();
        connectableFlux.subscribe(s-> System.out.println("Subscribe 1 : "+s));
        Thread.sleep(3000);
        connectableFlux.subscribe(s -> System.out.println("Subscribe 2 : "+s));
        Thread.sleep(4000);
    }
}
