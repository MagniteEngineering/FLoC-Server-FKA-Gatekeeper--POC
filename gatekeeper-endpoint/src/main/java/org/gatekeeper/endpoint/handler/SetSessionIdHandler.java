package org.gatekeeper.endpoint.handler;

import de.huxhorn.sulky.ulid.ULID;
import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import java.util.Random;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;
import org.gatekeeper.endpoint.context.DataContext;
import org.gatekeeper.endpoint.model.Session;
import org.gatekeeper.endpoint.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SetSessionIdHandler implements Handler<RoutingContext> {
    private static final Random RANDOM = new Random();

    @Autowired
    public SetSessionIdHandler() {
    }

    @Override
    public void handle(RoutingContext routingContext) {
        DataContext dataContext = DataContext.from(routingContext);
         String sessionId = createKhaosId();
         dataContext.setSessionId(sessionId);

        routingContext.next();
    }

    private String createKhaosId() {
        int partitionId = RANDOM.nextInt(80) + 1;
        String userId = String.join("-",
                Long.toString(System.currentTimeMillis(), 36),
                Integer.toString(partitionId, 36),
                Long.toString(Math.round(Math.random() * 1024 * 1024), 36));
        userId = userId.toUpperCase();
        return userId;
    }
}
