package io.hanumanta.reactivespring.initialize;

import io.hanumanta.reactivespring.document.Item;
import io.hanumanta.reactivespring.repository.ItemReactiveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;

@Component
@Profile("!test")
public class ItemDataInitializer implements CommandLineRunner {
    @Autowired
    ItemReactiveRepository itemReactiveRepository;

    @Override
    public void run(String... args) throws Exception {
        initialDataSetUp();
    }

    private void initialDataSetUp() {
        itemReactiveRepository.deleteAll()
                .thenMany(Flux.fromIterable(data()))
                .flatMap(itemReactiveRepository::save)
                .thenMany(itemReactiveRepository.findAll())
                .subscribe(item -> {
                    System.out.println("Item inserted from CommandLineRunner " + item);
                });
    }

    private List<Item> data() {
        return Arrays.asList(new Item(null, "Samsung TV", 200.0),
                new Item(null, "LG TV", 300.0),
                new Item(null, "Apple Watch", 400.0),
                new Item("abc", "OnePlus 6T", 500.0));

    }
}
