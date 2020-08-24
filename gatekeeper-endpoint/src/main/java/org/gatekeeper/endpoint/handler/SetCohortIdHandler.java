package org.gatekeeper.endpoint.handler;

import de.huxhorn.sulky.ulid.ULID;
import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import org.apache.commons.lang3.StringUtils;
import org.gatekeeper.endpoint.context.DataContext;
import org.gatekeeper.endpoint.model.Cohort;
import org.gatekeeper.endpoint.service.CohortService;
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
    public SetCohortIdHandler() {
    }

    @Override
    public void handle(RoutingContext routingContext) {
        DataContext dataContext = DataContext.from(routingContext);
       String sessionId = dataContext.getSessionId();
       if(sessionId.isEmpty()){
           String cohortId = generateCohortId();
           dataContext.setCohortId(cohortId);

       }else {
        Cohort cohortIdFromDB =  cohortService.findBySessionId(sessionId);
        if(cohortIdFromDB != null){
            logger.debug("Cohrt id from DB "+ cohortIdFromDB.getCohortId());
            dataContext.setCohortId(cohortIdFromDB.getCohortId());
        }else{
            logger.debug(" Cohort not found ");
            String cohortId = generateCohortId();
            dataContext.setCohortId(cohortId);

        }
       }

        routingContext.next();
    }

    private String generateCohortId() {
        return ulidGenerator.nextULID();
    }
}
