package ClientsFiles;

import java.io.Serializable;

public class sendInfo implements Serializable {

    private String sender;  //Type of sender Interviewer or InterViewee
    private String typeOfInfo; //Depends upon whether sent object is a message or the screen sharing text
    private String targetedArea;//Tells which text area is targeted for this object and later uses HashMap for accessing that named textarea
    private String content;//SharedContent -- Screen share text or Message -- Message in the chatDisplayTextArea

    sendInfo(String sender,String typeOfInfo,String targetedArea,String content){
        this.sender = sender;
        this.typeOfInfo = typeOfInfo;
        this.targetedArea = targetedArea;
        this.content = content;
    }

    public String getSender() {
        return sender;
    }

    public String getTypeOfInfo() {
        return typeOfInfo;
    }

    public String getTargetedArea() {
        return targetedArea;
    }

    public String getContent() {
        return content;
    }
}
