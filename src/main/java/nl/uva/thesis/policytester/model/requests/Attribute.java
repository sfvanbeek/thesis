package nl.uva.thesis.policytester.model.requests;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class Attribute {
    @XmlAttribute(name = "name")
    private String name;
    @XmlElement(name = "Id", required = true)
    private String id;
    @XmlElement(name = "DataType", required = true)
    private String datatype;
    @XmlElementWrapper(name = "Values", required = true)
    @XmlElement(name="Value")
    private List<String> values;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDatatype() {
        return datatype;
    }

    public void setDatatype(String datatype) {
        this.datatype = datatype;
    }

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }
}
