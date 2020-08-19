package org.gatekeeper.endpoint.config;

import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.common.template.TemplateEngine;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CorsHandler;
import io.vertx.ext.web.handler.TemplateHandler;
import io.vertx.ext.web.templ.handlebars.HandlebarsTemplateEngine;
import org.gatekeeper.endpoint.handler.*;
import org.gatekeeper.endpoint.util.HandlerRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class RouteConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(RouteConfiguration.class);

    @Bean
    public HandlerRegistry handlerRegistry(List<Handler<RoutingContext>> handlers) {
        return new HandlerRegistry(handlers);
    }

    @Bean
    public Router router(Vertx vertx, HandlerRegistry handlerRegistry) {
        Router router = Router.router(vertx);

        router.route("/*")
                .handler(handlerRegistry.get(AddRequestMetricsHandler.class))
                .handler(
                        CorsHandler.create(".*.")
                                .allowCredentials(true)
                                .allowedHeader("Access-Control-Allow-Method")
                                .allowedHeader("Access-Control-Allow-Origin")
                                .allowedHeader("Access-Control-Allow-Credentials")
                                .allowedHeader("Content-type")
                                .allowedMethod(HttpMethod.GET)
                                .allowedMethod(HttpMethod.POST)
                                .allowedMethod(HttpMethod.OPTIONS));


        router.get("/cohort/id")
                .handler(handlerRegistry.get(AddDefaultHeadersHandler.class))
                .handler(handlerRegistry.get(SetCohortIdHandler.class))
                .handler(handlerRegistry.get(RespondChorotIdHandler.class));

        router.post("/gatekeeper/sync")
                .handler(BodyHandler.create())
                .handler(handlerRegistry.get(AddDefaultHeadersHandler.class))
                .handler(handlerRegistry.get(GatekeeperSyncHandler.class));

        router.get("/gatekeeper/id")
                .handler(BodyHandler.create())
                .handler(handlerRegistry.get(AddDefaultHeadersHandler.class))
                .handler(handlerRegistry.get(SetUserIdHandler.class))
                .handler(handlerRegistry.get(RespondSessionIdHandler.class));


        router.get("/health")
                .handler(new RespondHealthCheckHandler());

        router.errorHandler(500, routingContext -> {
            logger.error("Failed to handle request", routingContext.failure());
        });

        return router;
    }
}
