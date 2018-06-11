package in.globalsoft.pojo;


import java.io.Serializable;

public class SavingCardsPoJo implements Serializable {

    private final String id;

    private final String saving_card_no;

    private final String bin;

    private final String group;

    private final String name;



    final String member;




    public SavingCardsPoJo(final String id, final String saving_card_no, final String bin, final String group, final String name, final String member) {
        this.id = id;
        this.saving_card_no = saving_card_no;
        this.bin = bin;
        this.group = group;
        this.name = name;
        this.member = member;
    }

    public String getId() {
        return id;
    }

    public String getSaving_card_no() {
        return saving_card_no;
    }

    public String getBin() {
        return bin;
    }

    public String getGroup() {
        return group;
    }

    public String getMember() {
        return member;
    }

    public String getName() {
        return name;
    }
}
