package nl.uva.thesis.policytester.model.requests;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class Request {
    @XmlAttribute(name = "name", required = true)
    private String name;

    @XmlElement(name = "SubjectAttribute")
    private List<Attribute> subjectAttributes;
    @XmlElement(name = "ResourceAttribute")
    private List<Attribute> resourceAttributes;
    @XmlElement(name = "EnvironmentAttribute")
    private List<Attribute> environmentAttributes;
    @XmlElement(name = "ActionAttribute")
    private List<Attribute> actionAttributes;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Attribute> getSubjectAttributes() {
        return subjectAttributes;
    }

    public void setSubjectAttributes(List<Attribute> subjectAttributes) {
        this.subjectAttributes = subjectAttributes;
    }

    public List<Attribute> getResourceAttributes() {
        return resourceAttributes;
    }

    public void setResourceAttributes(List<Attribute> resourceAttributes) {
        this.resourceAttributes = resourceAttributes;
    }

    public List<Attribute> getEnvironmentAttributes() {
        return environmentAttributes;
    }

    public void setEnvironmentAttributes(List<Attribute> environmentAttributes) {
        this.environmentAttributes = environmentAttributes;
    }

    public List<Attribute> getActionAttributes() {
        return actionAttributes;
    }

    public void setActionAttributes(List<Attribute> actionAttributes) {
        this.actionAttributes = actionAttributes;
    }
}
