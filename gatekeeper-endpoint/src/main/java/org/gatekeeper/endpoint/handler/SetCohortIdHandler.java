package org.gatekeeper.endpoint.handler;

import de.huxhorn.sulky.ulid.ULID;
import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import org.apache.commons.lang3.StringUtils;
import org.gatekeeper.endpoint.context.DataContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SetCohortIdHandler implements Handler<RoutingContext> {

    private ULID ulidGenerator = new ULID();

    @Autowired
    public SetCohortIdHandler() {
    }

    @Override
    public void handle(RoutingContext routingContext) {
        DataContext dataContext = DataContext.from(routingContext);
         String cohortId = generateCohortId();
         dataContext.setCohortId(cohortId);

        routingContext.next();
    }

    private String generateCohortId() {
        return ulidGenerator.nextULID();
    }
}
