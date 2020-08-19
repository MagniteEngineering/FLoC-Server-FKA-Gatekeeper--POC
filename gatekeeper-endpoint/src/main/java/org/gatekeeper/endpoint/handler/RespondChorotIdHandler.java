package org.gatekeeper.endpoint.handler;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import org.gatekeeper.endpoint.context.DataContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class RespondChorotIdHandler implements Handler<RoutingContext> {
    private static final Logger logger = LoggerFactory.getLogger(RespondChorotIdHandler.class);

    @Override
    public void handle(RoutingContext routingContext) {
        DataContext dataContext = DataContext.from(routingContext);

        HttpServerResponse response = routingContext.response();

        String cohortId = dataContext.getCohortId();

        JsonObject responseJson = new JsonObject();
        responseJson.put("CohortId", cohortId);

        response.setStatusCode(200);

        logger.debug("Responding with {} {}", response.getStatusCode(), responseJson);

        response.end(responseJson.toBuffer());
    }
}
