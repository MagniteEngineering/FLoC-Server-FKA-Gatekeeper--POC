package org.gatekeeper.endpoint.handler;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.DecodeException;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import java.sql.Timestamp;
import java.util.Calendar;
import org.gatekeeper.endpoint.context.DataContext;
import org.gatekeeper.endpoint.model.Session;
import org.gatekeeper.endpoint.service.SessionService;
import org.gatekeeper.endpoint.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GetSessionIdHandler implements Handler<RoutingContext> {
    private static final Logger logger = LoggerFactory.getLogger(GetSessionIdHandler.class);

    @Autowired
    SessionService sessionService;

    @Autowired
    public GetSessionIdHandler() {
    }

    @Override
    public void handle(RoutingContext routingContext) {

        JsonObject json;

        try {
            json = routingContext.getBodyAsJson();
        } catch (DecodeException e) {
            logger.debug("Body is not json");
            ResponseUtil.badRequest(routingContext.response());
            return;
        }

        if (json == null) {
            logger.debug("Body is missing");
            ResponseUtil.badRequest(routingContext.response());
            return;
        }
        String sessionId =  json.getString("sid");
        logger.debug(" Session id "+ sessionId);
        DataContext dataContext = DataContext.from(routingContext);
        dataContext.setSessionId(sessionId);
         routingContext.next();
    }

}
