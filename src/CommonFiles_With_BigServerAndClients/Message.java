package CommonFiles_With_BigServerAndClients;

import java.io.Serializable;

public class Message implements Serializable {

    private String content;
    private String lang;
    private String option;


    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }
}
