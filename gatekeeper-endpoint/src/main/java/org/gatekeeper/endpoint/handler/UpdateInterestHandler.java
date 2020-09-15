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
import org.gatekeeper.endpoint.util.CohortUtil;
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
        dataContext.setCohortId(CohortUtil.DEFAULT_COHORT);
        interests.forEach(interest -> saveInterest(sessionId, interest,dataContext));

        routingContext.next();
    }

    private void saveInterest(String sessionId, String userInterest,DataContext dataContext){
        Timestamp currentTimestamp = new Timestamp(Calendar.getInstance().getTime().getTime());
        Interest interest = new Interest();
        interest.setSessionId(sessionId);
        interest.setInterest(userInterest);
        interest.setCreatedOn(currentTimestamp);
        interestService.save(interest);
        dataContext.setCohortId(CohortUtil.getCohort(userInterest));
    }
}
