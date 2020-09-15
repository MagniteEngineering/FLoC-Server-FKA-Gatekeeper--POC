package org.gatekeeper.endpoint.util;

import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.RandomStringUtils;

public class CohortUtil {

    public static String DEFAULT_COHORT = "abcd";
    private static Map<String, String> interestCohort = new HashMap<>();

   static {
        interestCohort.put("Arts", "bbcc");
        interestCohort.put("Health", "aabb");
        interestCohort.put("Recreation", "ddee");
        interestCohort.put("Science", "ffgg");
        interestCohort.put("World", "hhii");
        interestCohort.put("Business", "jjkk");
        interestCohort.put("Home", "llmm");
        interestCohort.put("Reference", "nnoo");
        interestCohort.put("Society", "ppqq");
        interestCohort.put("Computers", "rrss");
        interestCohort.put("News", "ttuu");
        interestCohort.put("Regional", "vvww");
        interestCohort.put("Sports", "xxyy");
    }

    public static String generateCohortId() {
        return RandomStringUtils
                .random(4, true, true);
    }

    public static String getCohort(String interest){
       String cohort = DEFAULT_COHORT;
       if(interestCohort.containsKey(interest)){
           cohort = interestCohort.get(interest);
       }
       return cohort;
    }

}
