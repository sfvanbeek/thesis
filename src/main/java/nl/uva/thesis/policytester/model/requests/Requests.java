package nl.uva.thesis.policytester.model.requests;


import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "Requests")
@XmlAccessorType(XmlAccessType.FIELD)
public class Requests {

    @XmlAttribute(name = "name")
    private String name;

    @XmlElement(name = "Request")
    private List<Request> requestList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Request> getRequestList() {
        return requestList;
    }

    public void setRequestList(List<Request> requestList) {
        this.requestList = requestList;
    }
}
