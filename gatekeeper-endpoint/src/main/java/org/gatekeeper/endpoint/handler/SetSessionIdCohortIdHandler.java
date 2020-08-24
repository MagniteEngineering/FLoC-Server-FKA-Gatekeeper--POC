package org.gatekeeper.endpoint.handler;

import de.huxhorn.sulky.ulid.ULID;
import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Random;
import org.gatekeeper.endpoint.context.DataContext;
import org.gatekeeper.endpoint.model.Cohort;
import org.gatekeeper.endpoint.service.CohortService;
import org.gatekeeper.endpoint.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SetSessionIdCohortIdHandler implements Handler<RoutingContext> {

    private ULID ulidGenerator = new ULID();

    @Autowired
    CohortService cohortService;

    @Autowired
    public SetSessionIdCohortIdHandler() {
    }

    @Override
    public void handle(RoutingContext routingContext) {
        DataContext dataContext = DataContext.from(routingContext);
        String sessionId = dataContext.getSessionId();
        Timestamp currentTimestamp = new Timestamp(Calendar.getInstance().getTime().getTime());
        String cohortId = generateCohortId();
        Cohort cohort = new Cohort();
        cohort.setSessionId(sessionId);
        cohort.setCohortId(cohortId);
        cohort.setCreatedOn(currentTimestamp);
        cohortService.save(cohort);
        routingContext.next();
    }

    private String generateCohortId() {
        return ulidGenerator.nextULID();
    }
}
