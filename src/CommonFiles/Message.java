package CommonFiles;

import java.io.Serializable;

public class Message implements Serializable {

    private String status;
    private String id;
    private String Message;
    private String othermanid;

    public String getOthermanid() {
        return othermanid;
    }

    public void setOthermanid(String othermanid) {
        this.othermanid = othermanid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
