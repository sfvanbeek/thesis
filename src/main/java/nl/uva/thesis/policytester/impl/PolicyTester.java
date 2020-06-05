package nl.uva.thesis.policytester.impl;

import nl.uva.thesis.policytester.exception.PolicyTestException;
import nl.uva.thesis.policytester.model.requests.Request;
import oasis.names.tc.xacml._3_0.core.schema.wd_17.DecisionType;
import org.ow2.authzforce.core.pdp.api.DecisionRequest;
import org.ow2.authzforce.core.pdp.api.DecisionRequestBuilder;
import org.ow2.authzforce.core.pdp.api.DecisionResult;
import org.ow2.authzforce.core.pdp.impl.BasePdpEngine;

public class PolicyTester {
    private final BasePdpEngine basePdpEngine;

    public PolicyTester(BasePdpEngine basePdpEngine) {
        this.basePdpEngine = basePdpEngine;
    }

    /**
     * Converts a local request to a workable decision request in order to evaluate it.
     * Returns the outcome of the evaluation.
     */
    public DecisionType evaluate(Request request) throws PolicyTestException {
        DecisionRequest decisionRequest = new DecisionRequestConverter(request, getDecisionRequestBuilder()).build();
        DecisionResult evaluate = basePdpEngine.evaluate(decisionRequest);
        return evaluate.getDecision();

    }

    private DecisionRequestBuilder<?> getDecisionRequestBuilder() {
        // These arguments removes the expected size limit of the request, we don't care about performance
        return basePdpEngine.newRequestBuilder(-1, -1);
    }
}
