package in.globalsoft.beans;

import java.util.List;

/**
 * Created by root on 28/9/17.
 */

public class BeansListSpecialities {

    private String code;
    private String message;
    private List<BeanSpecaility> specialities;


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

    public List<BeanSpecaility> getSpecialities() {
        return specialities;
    }

    public void setSpecialities(List<BeanSpecaility> specialities) {
        this.specialities = specialities;
    }
}
