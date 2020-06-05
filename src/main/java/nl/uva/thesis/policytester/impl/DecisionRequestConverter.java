package nl.uva.thesis.policytester.impl;

import nl.uva.thesis.policytester.exception.PolicyTestException;
import nl.uva.thesis.policytester.model.requests.Attribute;
import nl.uva.thesis.policytester.model.requests.Request;
import org.ow2.authzforce.core.pdp.api.AttributeFqn;
import org.ow2.authzforce.core.pdp.api.AttributeFqns;
import org.ow2.authzforce.core.pdp.api.DecisionRequest;
import org.ow2.authzforce.core.pdp.api.DecisionRequestBuilder;
import org.ow2.authzforce.core.pdp.api.value.*;
import org.ow2.authzforce.xacml.identifiers.XacmlAttributeCategory;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class DecisionRequestConverter {
    private final Request request;
    private final DecisionRequestBuilder<?> decisionRequestBuilder;


    public DecisionRequestConverter(Request request, DecisionRequestBuilder decisionRequestBuilder) {
        this.request = request;
        this.decisionRequestBuilder = decisionRequestBuilder;
    }

    public DecisionRequest build() throws PolicyTestException {
        extractAllCategories();
        return decisionRequestBuilder.build(true);
    }


    /*
     * Initaite the extraction of attributes of all categories
     */
    private void extractAllCategories() throws PolicyTestException {
        // here we will handle all basic attribute categories, ignoring various subject specific ones
        extractAttributesForCategory(XacmlAttributeCategory.XACML_1_0_ACCESS_SUBJECT, request.getSubjectAttributes());
        extractAttributesForCategory(XacmlAttributeCategory.XACML_3_0_ACTION, request.getActionAttributes());
        extractAttributesForCategory(XacmlAttributeCategory.XACML_3_0_ENVIRONMENT, request.getEnvironmentAttributes());
        extractAttributesForCategory(XacmlAttributeCategory.XACML_3_0_RESOURCE, request.getResourceAttributes());
    }

    /**
     * Process all attributes given in the request for a given category.
     */
    private void extractAttributesForCategory(XacmlAttributeCategory category, List<Attribute> attributeList) throws PolicyTestException {
        Optional<List<Attribute>> optionalAttributes = Optional.ofNullable(attributeList);

        if (optionalAttributes.isPresent()) {
            for (Attribute attribute : attributeList) {
                extractAttribute(category, attribute);
            }
        }
    }

    /**
     * This method will take a locally modelled attribute and add it to the bag of AttributeValues expected in
     * the decision request. Finally, add the bag to the decision request.
     */
    private void extractAttribute(XacmlAttributeCategory category, Attribute attribute) throws PolicyTestException {
        AttributeDatatype<?> datatype = getStandardDatatype(attribute.getDatatype());
        final AttributeFqn attributeName = AttributeFqns.newInstance(category.value(), Optional.empty(), attribute.getId());
        final AttributeBag<?> bag = Bags.newAttributeBag((Datatype) datatype, getValueForDatatype(datatype, attribute.getValues()));
        decisionRequestBuilder.putNamedAttributeIfAbsent(attributeName, bag);
    }

    /**
     * This method parses a list of attribue values and casts it to the AttributeValueType associated with the supplied
     * datatype.
     */
    private <AV extends AttributeValue> List<AV> getValueForDatatype(AttributeDatatype<AV> datatype, List<String> serializables) throws PolicyTestException {
        List<AV> result;
        try {
            result = serializables.stream()
                    .map(s -> castValue(datatype, s))
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .map(datatype::cast)
                    .collect(Collectors.toList());

        } catch (ClassCastException c) {
            // both the conversion to string as the attribute value can cause class cast exceptions
            throw new PolicyTestException("Failed to convert value to datatype", c);
        }
        return result;
    }

    /**
     * This method uses the known datatype to parse a String object and convert it to a Value
     * Perhaps this could be handled better in the model using JAXB
     */
    private Optional<Value> castValue(AttributeDatatype datatype, String s) {
        if (StandardDatatypes.STRING.equals(datatype)) {
            return Optional.of(new StringValue(s));
        } else {
            return Optional.empty();
        }
    }

    /**
     * Method to convert to one of the default datatype in XACML, currently only string is supported.
     */
    private AttributeDatatype<?> getStandardDatatype(String dataType) throws PolicyTestException {
        switch (dataType.toUpperCase()) {
            // Extend this with more datatypes as necessary
            case "STRING":
                return StandardDatatypes.STRING;
            default:
                throw new PolicyTestException("Invalid datatype in attribute: " + dataType);
        }
    }


}
