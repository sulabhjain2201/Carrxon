package in.globalsoft.urncr;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import in.globalsoft.pojo.SavingCardsPoJo;

public class CopaySavingDetailActivity extends Activity {

    private SavingCardsPoJo savingCard;
    private TextView tvName , tvSavingCard , tvBin , tvGroup , tvMember , tvTitle;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_copay_saving_detail);
        savingCard = (SavingCardsPoJo) getIntent().getSerializableExtra("savingCards");

        initGUI();

        assignValues();

    }

    private void assignValues() {
        tvTitle.setText(savingCard.getName());
        tvName.setText(savingCard.getName());
        tvBin.setText(savingCard.getBin());
        tvGroup.setText(savingCard.getGroup());
        tvMember.setText(savingCard.getMember());
        tvSavingCard.setText(savingCard.getSaving_card_no());
    }

    private void initGUI() {
        tvTitle = (TextView) findViewById(R.id.title_text);
        tvBin = (TextView) findViewById(R.id.tvBinValue);
        tvGroup = (TextView) findViewById(R.id.tvGroupValue);
        tvMember = (TextView) findViewById(R.id.tvMemberValue);
        tvSavingCard = (TextView) findViewById(R.id.tvSavingCardValue);
        tvName = (TextView) findViewById(R.id.tvNameValue);
    }
}
