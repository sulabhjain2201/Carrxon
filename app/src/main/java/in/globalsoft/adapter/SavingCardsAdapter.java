package in.globalsoft.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import in.globalsoft.pojo.ListSavingCardPojo;
import in.globalsoft.pojo.SavingCardsPoJo;
import in.globalsoft.urncr.R;


public class SavingCardsAdapter extends BaseAdapter {


    private final ListSavingCardPojo savingCards;
    private final Activity context;
    private ViewHolder viewHolder;


    public SavingCardsAdapter(final ListSavingCardPojo savingCards, final Activity context) {

        this.savingCards = savingCards;
        this.context = context;
    }

    @Override
    public int getCount() {
        return savingCards.getSaving_cards().size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        if (view == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            view = inflater.inflate(R.layout.adapter_saving_cards, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tvSavingCardName = (TextView) view.findViewById(R.id.list_item);

            view.setTag(viewHolder);
        } else
            viewHolder = (ViewHolder) view.getTag();

        final SavingCardsPoJo savingCard = savingCards.getSaving_cards().get(i);

        //imageLoader.displayImage( ConstantUtil.urlBase+bar.getFlyerUrl(), viewHolder.ivBarFetelogo, options, null);
        viewHolder.tvSavingCardName.setText(savingCard.getName());


        return view;
    }

    public static class ViewHolder {
        private TextView tvSavingCardName;

    }
}
