package org.gatekeeper.endpoint.handler;

import de.huxhorn.sulky.ulid.ULID;
import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import java.util.List;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.gatekeeper.endpoint.context.DataContext;
import org.gatekeeper.endpoint.model.Cohort;
import org.gatekeeper.endpoint.model.Interest;
import org.gatekeeper.endpoint.service.CohortService;
import org.gatekeeper.endpoint.service.InterestService;
import org.gatekeeper.endpoint.util.CohortUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SetCohortIdHandler implements Handler<RoutingContext> {

    private static final Logger logger = LoggerFactory.getLogger(SetCohortIdHandler.class);
    @Autowired
    CohortService cohortService;
    private ULID ulidGenerator = new ULID();


    @Autowired
    InterestService interestService;

    @Autowired
    public SetCohortIdHandler() {
    }

    @Override
    public void handle(RoutingContext routingContext) {
        DataContext dataContext = DataContext.from(routingContext);
       String sessionId = dataContext.getSessionId();
     if(sessionId.isEmpty()){
           String cohortId = CohortUtil.generateCohortId();
           dataContext.setCohortId(cohortId);

       }else {
         List<Interest> interests =  interestService.findBySessionId(sessionId);
         logger.debug("Interest: "+ interests.size());
         String cohort = CohortUtil.DEFAULT_COHORT;
         if(interests.size() > 0){
              cohort = CohortUtil.getCohort(interests.get(0).getInterest());
             dataContext.setCohortId(cohort);
         }
            logger.debug(" Cohort not found ");
            dataContext.setCohortId(cohort);

       }

        routingContext.next();
    }

}
