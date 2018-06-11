package in.globalsoft.pojo;


import java.io.Serializable;
import java.util.List;

public class ListSavingCardPojo implements Serializable {
    private final String code;

    private final String message ;

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public List<SavingCardsPoJo> getSaving_cards() {
        return saving_cards;
    }

    private final List<SavingCardsPoJo> saving_cards;

    public ListSavingCardPojo(final String code,final  String message,final List<SavingCardsPoJo> saving_cards) {
        this.code = code;
        this.message = message;
        this.saving_cards = saving_cards;
    }
}
