package in.globalsoft.pojo;

import java.util.List;

/**
 * Created by LinchPin on 3/26/2015.
 */
public class RecentChatgPojoOffice {
    private String status;
    private List<MessagePojoOffice> message;


    public List<MessagePojoOffice> getMessage() {
        return message;
    }
    public void setMessage(List<MessagePojoOffice> message) {
        this.message = message;
    }
    public String getStatus()
    {
        return status;
    }
    public void setStatus(String status)
    {
        this.status = status;
    }
}
