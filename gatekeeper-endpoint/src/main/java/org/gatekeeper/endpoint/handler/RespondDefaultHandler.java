package org.gatekeeper.endpoint.handler;

import org.gatekeeper.endpoint.context.DataContext;
import org.gatekeeper.endpoint.util.ResponseUtil;
import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class RespondDefaultHandler implements Handler<RoutingContext> {
    private static final Logger logger = LoggerFactory.getLogger(RespondDefaultHandler.class);

    @Override
    public void handle(RoutingContext routingContext) {
        DataContext dataContext = DataContext.from(routingContext);

        String sessionId = dataContext.getSessionId();

        //TODO: before turning this over to prebid.org, remove the default response to rubicon
        String redirectUrl = "https://pixel.rubiconproject.com/tap.php?v=624210&nid=2231&put=" + sessionId;

        logger.debug("Sending default redirect to {}", redirectUrl);

        ResponseUtil.redirect(routingContext.response(), redirectUrl);
    }
}
