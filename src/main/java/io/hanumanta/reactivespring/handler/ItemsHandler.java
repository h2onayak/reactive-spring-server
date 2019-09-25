package io.hanumanta.reactivespring.handler;

import io.hanumanta.reactivespring.document.Item;
import io.hanumanta.reactivespring.repository.ItemReactiveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.BodyInserters.fromObject;

@Component
public class ItemsHandler {
    static Mono<ServerResponse> notFound = ServerResponse.notFound().build();
    @Autowired
    ItemReactiveRepository itemReactiveRepository;

    public Mono<ServerResponse> getAllItems(ServerRequest serverRequest) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(itemReactiveRepository.findAll(), Item.class);

    }

    public Mono<ServerResponse> getOneItem(ServerRequest serverRequest) {
        String id = serverRequest.pathVariable("id");
        Mono<Item> itemMono = itemReactiveRepository.findById(id);
        return itemMono.flatMap(item ->
                ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(fromObject(item))
                        .switchIfEmpty(notFound)
        );
    }
    //TODO Write test cases for this
    public Mono<ServerResponse> createItem(ServerRequest serverRequest){
        Mono<Item> itemTobeInserted = serverRequest.bodyToMono(Item.class);
        return itemTobeInserted.flatMap(item ->
                ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(itemReactiveRepository.save(item),Item.class));
    }
    //TODO Write test cases for this
    public Mono<ServerResponse> deleteItem(ServerRequest serverRequest){
        String id = serverRequest.pathVariable("id");
        Mono<Void> deleteItem = itemReactiveRepository.deleteById(id);
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(deleteItem,Void.class);
    }
    //TODO Write test cases for this
    public Mono<ServerResponse>updateItem(ServerRequest serverRequest){
        String id = serverRequest.pathVariable("id");
        Mono<Item> updatedItem = serverRequest.bodyToMono(Item.class)
                .flatMap(item ->{
                    Mono<Item> itemMono = itemReactiveRepository.findById(id)
                            .flatMap(currrentItem->{
                                currrentItem.setDescription(item.getDescription());
                                currrentItem.setPrice(item.getPrice());
                                return itemReactiveRepository.save(currrentItem);
                            });
                    return itemMono;
                });
        return updatedItem.flatMap(item ->
                ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(fromObject(item))
                        .switchIfEmpty(notFound));

    }

    public Mono<ServerResponse> temsEx(ServerRequest serverRequest) {
        throw new RuntimeException("RuntimeException occurred.");
    }
}
