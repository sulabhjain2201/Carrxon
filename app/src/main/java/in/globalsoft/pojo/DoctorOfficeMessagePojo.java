package in.globalsoft.pojo;

import java.util.List;

/**
 * Created by LinchPin on 3/26/2015.
 */
public class DoctorOfficeMessagePojo
{
    private String status;
    private List<DoctorOfficePojo> message;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<DoctorOfficePojo> getMessage() {
        return message;
    }

    public void setMessage(List<DoctorOfficePojo> message) {
        this.message = message;
    }
}
