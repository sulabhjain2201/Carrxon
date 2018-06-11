package in.globalsoft.urncr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import in.globalsoft.adapter.SavingCardsAdapter;
import in.globalsoft.pojo.ListSavingCardPojo;
import in.globalsoft.tasks.GetSavingCardsTask;
import in.globalsoft.util.Cons;

public class ListCopaySavingCardsActivity extends Activity implements GetSavingCardsTask.SavingCardsUpdate {

    private ListView lvSavingCards;
    private ListSavingCardPojo savingCards;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_copay_saving_cards);
        lvSavingCards = (ListView) findViewById(R.id.list_copay_saving_cards);

        if (Cons.isNetworkAvailable(this)) {
            new GetSavingCardsTask(this).execute();
        } else {
            Cons.showDialog(this, getString(R.string.app_name), getString(R.string.net_not_available), "OK");
        }

        lvSavingCards.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> adapterView, final View view, final int i, final long l) {

                final Intent savingCardDetailIntent = new Intent(ListCopaySavingCardsActivity.this , CopaySavingDetailActivity.class);
                savingCardDetailIntent.putExtra("savingCards" , savingCards.getSaving_cards().get(i));
                startActivity(savingCardDetailIntent);
            }
        });


    }

    @Override
    public void updateSavingCards(final ListSavingCardPojo savingCards) {
        this.savingCards = savingCards;

        if (savingCards == null) {
            Toast.makeText(ListCopaySavingCardsActivity.this, "Connection is slow or some error in apis.", Toast.LENGTH_LONG).show();
        }

        else if (savingCards.getCode().equals("200")) {
            lvSavingCards.setAdapter(new SavingCardsAdapter(savingCards, this));
        }

        else {
            Toast.makeText(ListCopaySavingCardsActivity.this, savingCards.getMessage(), Toast.LENGTH_LONG).show();
        }


    }
}
