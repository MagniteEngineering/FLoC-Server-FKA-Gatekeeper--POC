package org.gatekeeper.endpoint.handler;

import io.vertx.core.Handler;
import io.vertx.core.json.DecodeException;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import java.util.ArrayList;
import java.util.List;
import org.gatekeeper.endpoint.context.DataContext;
import org.gatekeeper.endpoint.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GetInterestDetailsHandler implements Handler<RoutingContext> {
    private static final Logger logger = LoggerFactory.getLogger(GetInterestDetailsHandler.class);

    @Autowired
    public GetInterestDetailsHandler() {
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
        List<String> interests = getInterestList(json.getJsonArray("interests"));
        DataContext dataContext = DataContext.from(routingContext);
        dataContext.setSessionId(sessionId);
        dataContext.setInterests(interests);
        routingContext.next();
    }

    private List<String> getInterestList(JsonArray interests){
        List<String> interestsList = new ArrayList<>();
        for (int i=0; i< interests.size(); i++) {
            interestsList.add(interests.getString(i) );
        }
        return interestsList;
    }
}
