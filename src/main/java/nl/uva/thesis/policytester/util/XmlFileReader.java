package nl.uva.thesis.policytester.util;

import nl.uva.thesis.policytester.exception.PolicyTestException;
import nl.uva.thesis.policytester.model.requests.Requests;
import oasis.names.tc.xacml._3_0.core.schema.wd_17.PolicySet;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class XmlFileReader {
    private XmlFileReader() {
    }

    public static PolicySet readPolicySet(String path) throws PolicyTestException {
        return readClass(path, PolicySet.class);
    }

    public static Requests readRequests(String path) throws PolicyTestException {
        return readClass(path, Requests.class);
    }

    private static <T> T readClass(String path, Class<T> clazz) throws PolicyTestException {
        T result;
        try {
            File file = new File(path);
            JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            result = (T) jaxbUnmarshaller.unmarshal(file);
            return result;
        } catch (JAXBException e) {
            throw new PolicyTestException("Failed to unmarshall " + clazz.getName(), e);
        }
    }
}
