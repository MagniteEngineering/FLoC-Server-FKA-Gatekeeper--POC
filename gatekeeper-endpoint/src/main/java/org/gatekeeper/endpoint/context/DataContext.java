package org.gatekeeper.endpoint.context;

import io.vertx.core.http.Cookie;
import io.vertx.ext.web.RoutingContext;

/**
 * Simple wrapper for retrieving type safe data from the RoutingContext data map.
 */
public class DataContext {
    private static final String DATA_DATA_CONTEXT = "data.dataContext";

    private static final String DATA_SESSION_ID = "data.sessionId";
    private static final String DATA_COHORT_ID = "data.cohortId";

    private RoutingContext routingContext;

    public DataContext(RoutingContext routingContext) {
        this.routingContext = routingContext;
    }

    public static DataContext from(RoutingContext routingContext) {
        DataContext dataContext = (DataContext) routingContext.data().get(DATA_DATA_CONTEXT);

        if (dataContext == null) {
            dataContext = new DataContext(routingContext);
            routingContext.data().put(DATA_DATA_CONTEXT, dataContext);
        }

        return dataContext;
    }
    public String getSessionId() {
        return get(DATA_SESSION_ID);
    }

    public void setSessionId(String sessionId) {
        put(DATA_SESSION_ID, sessionId);
    }


    public String getCohortId() {
        return get(DATA_COHORT_ID);
    }

    public void setCohortId(String cohortId) {
        put(DATA_COHORT_ID, cohortId);
    }

    private <T> T get(String key, T def) {
        return (T) routingContext.data().getOrDefault(key, def);
    }

    private <T> T get(String key) {
        return (T) routingContext.data().get(key);
    }

    private <T> void put(String key, T obj) {
        routingContext.data().put(key, obj);
    }
}
