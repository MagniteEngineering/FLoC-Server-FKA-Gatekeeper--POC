package org.gatekeeper.endpoint.context;

import io.vertx.ext.web.RoutingContext;
import java.util.List;

/**
 * Simple wrapper for retrieving type safe data from the RoutingContext data map.
 */
public class DataContext {
    private static final String DATA_DATA_CONTEXT = "data.dataContext";

    private static final String DATA_SESSION_ID = "data.sessionId";
    private static final String DATA_COHORT_ID = "data.cohortId";
    private static final String DATA_DOMAIN_ID = "data.domain";
    private static final String DATA_PROFILE = "data.profile";
    private static final String DATA_INTEREST = "data.interest";

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

    public List<String> getInterests() {
        return get(DATA_PROFILE);
    }

    public void setInterests(List<String> profiles) {
        put(DATA_PROFILE, profiles);
    }


    public String getDomain() {
        return get(DATA_DOMAIN_ID);
    }

    public void setDomain(String domain) {
        put(DATA_DOMAIN_ID, domain);
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
