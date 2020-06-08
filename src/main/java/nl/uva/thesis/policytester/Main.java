package nl.uva.thesis.policytester;

import nl.uva.thesis.policytester.impl.PolicyTester;
import nl.uva.thesis.policytester.model.requests.Request;
import nl.uva.thesis.policytester.model.requests.Requests;
import nl.uva.thesis.policytester.util.PdpConfigurationGenerator;
import nl.uva.thesis.policytester.util.XmlFileReader;
import oasis.names.tc.xacml._3_0.core.schema.wd_17.PolicySet;
import org.ow2.authzforce.core.pdp.impl.BasePdpEngine;
import org.ow2.authzforce.core.pdp.impl.PdpEngineConfiguration;

public class Main {

    static {
        // Required because of external schema lookup
        System.setProperty("javax.xml.accessExternalSchema", "http,file");
    }

    /**
     * Program entry
     */
    public static void main(String[] args) throws Exception {
        String policySetPath = null;
        String requestsPath = null;

        // Check presence of policy and requests xml path in command line arguments
        if (args.length == 2) {
            policySetPath = args[0];
            requestsPath = args[1];
        } else {
            System.out.println("Usage: java -jar <executable> <path-to-policy.xml> <path-to-requests.xml>");
            System.exit(1);
        }

        // Extract the XACML policyset from xml file
        PolicySet policySet = XmlFileReader.readPolicySet(policySetPath);

        // Path and policyset required for setting up config
        PdpEngineConfiguration config = PdpConfigurationGenerator.getConfiguration(policySetPath, policySet);

        // Read the request from xml file using JAXB annotated model
        Requests requests = XmlFileReader.readRequests(requestsPath);

        // Initalize tester class using preconfigured PDP
        PolicyTester tester = new PolicyTester(new BasePdpEngine(config));

        // Print the request name and policy result for each request in the XML file
        for (Request req : requests.getRequestList()) {
            System.out.println(req.getName() + " : " + tester.evaluate(req).toString());
        }
    }

}
