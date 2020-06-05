package nl.uva.thesis.policytester.util;

import nl.uva.thesis.policytester.exception.PolicyTestException;
import oasis.names.tc.xacml._3_0.core.schema.wd_17.PolicySet;
import org.ow2.authzforce.core.pdp.impl.DefaultEnvironmentProperties;
import org.ow2.authzforce.core.pdp.impl.PdpEngineConfiguration;
import org.ow2.authzforce.core.xmlns.pdp.Pdp;
import org.ow2.authzforce.core.xmlns.pdp.StaticPolicyProvider;
import org.ow2.authzforce.core.xmlns.pdp.TopLevelPolicyElementRef;

import java.io.IOException;
import java.util.Collections;

public class PdpConfigurationGenerator {
    private PdpConfigurationGenerator() {
    }

    public static PdpEngineConfiguration getConfiguration(String policyLocation, PolicySet policySet) throws PolicyTestException {

        try {
            // Instantiate config class and fill using policyset data and path
            Pdp pdp = new Pdp();
            pdp.setRootPolicyRef(new TopLevelPolicyElementRef(policySet.getPolicySetId(), policySet.getVersion(), true));
            pdp.setPolicyProvider(new StaticPolicyProvider(Collections.singletonList(policyLocation), false));

            return new PdpEngineConfiguration(pdp, new DefaultEnvironmentProperties());
        } catch (IOException e) {
            throw new PolicyTestException("Failed to generate PDP configuration", e);
        }
    }
}
