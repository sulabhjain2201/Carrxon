package in.globalsoft.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Linchpin66 on 09-07-17.
 */
public class SavedDocRespo implements Serializable {
    private String code;
    private String message;
    private List<SavedDocPojo> doc_list;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<SavedDocPojo> getDoc_list() {
        return doc_list;
    }

    public void setDoc_list(List<SavedDocPojo> doc_list) {
        this.doc_list = doc_list;
    }
}
