package in.globalsoft.pojo;

import java.io.Serializable;

/**
 * Created by Linchpin66 on 09-07-17.
 */

public class SavedDocPojo implements Serializable {

    String user_id;
    String doc_title;
    String doc_description;
    String doc_category;
    String doc_url;
    String doc_time;
    String attach_name;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getDoc_title() {
        return doc_title;
    }

    public void setDoc_title(String doc_title) {
        this.doc_title = doc_title;
    }

    public String getDoc_description() {
        return doc_description;
    }

    public void setDoc_description(String doc_description) {
        this.doc_description = doc_description;
    }

    public String getDoc_category() {
        return doc_category;
    }

    public void setDoc_category(String doc_category) {
        this.doc_category = doc_category;
    }

    public String getDoc_url() {
        return doc_url;
    }

    public void setDoc_url(String doc_url) {
        this.doc_url = doc_url;
    }

    public String getDoc_time() {
        return doc_time;
    }

    public void setDoc_time(String doc_time) {
        this.doc_time = doc_time;
    }

    public String getAttach_name() {
        return attach_name;
    }

    public void setAttach_name(String attach_name) {
        this.attach_name = attach_name;
    }
}
