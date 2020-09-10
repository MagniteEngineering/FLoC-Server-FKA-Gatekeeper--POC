package org.gatekeeper.endpoint.handler;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import org.gatekeeper.endpoint.context.DataContext;
import org.gatekeeper.endpoint.model.Interest;
import org.gatekeeper.endpoint.service.InterestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UpdateInterestHandler implements Handler<RoutingContext> {
    private static final Logger logger = LoggerFactory.getLogger(UpdateInterestHandler.class);

    @Autowired
    InterestService interestService;

    @Autowired
    public UpdateInterestHandler() {
    }

    @Override
    public void handle(RoutingContext routingContext) {
        DataContext dataContext = DataContext.from(routingContext);

        String sessionId = dataContext.getSessionId();
        List<String> interests = dataContext.getInterests();
        interests.forEach(interest -> saveInterest(sessionId, interest));

        HttpServerResponse response = routingContext.response();
        response.setStatusCode(200);

        logger.debug("Responding with {} ", response.getStatusCode());

        response.end();
    }

    private void saveInterest(String sessionId, String userInterest){
        Timestamp currentTimestamp = new Timestamp(Calendar.getInstance().getTime().getTime());
        Interest interest = new Interest();
        interest.setSessionId(sessionId);
        interest.setInterest(userInterest);
        interest.setCreatedOn(currentTimestamp);
        interestService.save(interest);
    }
}
