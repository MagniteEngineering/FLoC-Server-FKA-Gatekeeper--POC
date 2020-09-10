package org.gatekeeper.endpoint.util;

import org.apache.commons.lang3.RandomStringUtils;

public class CohortUtil {

    public static String generateCohortId() {
        return RandomStringUtils
                .random(4, true, true);
    }
}
