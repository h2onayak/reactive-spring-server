package io.hanumanta.reactivespring.router;

import io.hanumanta.reactivespring.constants.ItemConstants;
import io.hanumanta.reactivespring.handler.ItemsHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class ItemsRouter {
    @Bean
    public RouterFunction<ServerResponse> itemsRoute(ItemsHandler itemsHandler) {
        return RouterFunctions
                .route(GET(ItemConstants.ITEM_FUNCTIONAL_END_POINT_V1).and(accept(MediaType.APPLICATION_JSON))
                        ,itemsHandler::getAllItems)
                .andRoute(GET(ItemConstants.ITEM_FUNCTIONAL_END_POINT_V1.concat("/{id}")).and(accept(MediaType.APPLICATION_JSON))
                ,itemsHandler::getOneItem)
                .andRoute(POST(ItemConstants.ITEM_FUNCTIONAL_END_POINT_V1).and(accept(MediaType.APPLICATION_JSON))
                ,itemsHandler::createItem)
                .andRoute(DELETE(ItemConstants.ITEM_FUNCTIONAL_END_POINT_V1.concat("/{id}")).and(accept(MediaType.APPLICATION_JSON))
                ,itemsHandler::deleteItem)
                .andRoute(PUT(ItemConstants.ITEM_FUNCTIONAL_END_POINT_V1.concat("/{id}")).and(accept(MediaType.APPLICATION_JSON))
                        ,itemsHandler::updateItem);
    }

    @Bean
    public RouterFunction<ServerResponse> errorRoute(ItemsHandler itemsHandler) {
        return RouterFunctions
                .route(GET("/fun/runtimeexception").and(accept(MediaType.APPLICATION_JSON)), itemsHandler::temsEx);
    }
}
