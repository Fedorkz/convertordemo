package com.fedorkzsoft.demo.ui.view;

import android.content.Context;
import android.support.v4.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fedorkzsoft.demo.R;
import com.fedorkzsoft.demo.presentation.model.Currency;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.zanlabs.widget.infiniteviewpager.InfinitePagerAdapter;

import java.util.List;

import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * Created by RxRead on 2015/9/24.
 */
public class CurrencyPagerAdapter extends InfinitePagerAdapter {

    private final LayoutInflater mInflater;
    private final Context mContext;
    PublishSubject<Pair<Currency, Double>> mCurrencySubject = PublishSubject.create();

    private List<Currency> mList;

    public void setDataList(List<Currency> list) {
        if (list == null || list.size() == 0)
            throw new IllegalArgumentException("list can not be null or has an empty size");
        this.mList = list;
        this.notifyDataSetChanged();
    }


    public CurrencyPagerAdapter(Context context) {
        mContext=context;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public View getView(int position, View view, ViewGroup container) {
        ViewHolder holder;
        if (view != null) {
            holder = (ViewHolder) view.getTag();
        } else {
            view = mInflater.inflate(R.layout.item_currency_viewpager, container, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }

        Currency item = mList.get(position);

        holder.val.setTag(position);
        holder.currency = item;
        holder.position = position;
        holder.name.setText(item.getCurrency());
        holder.rate.setText("Rate: " + item.getRate());

        holder.getObservable().subscribe(currencyDoublePair -> {
            mCurrencySubject.onNext(currencyDoublePair);
        });

        return view;
    }

    public static Double parseDouble(CharSequence val) {
        if (val == null)
            return 0d;

        String s = val.toString();
        return Double.parseDouble(s);
    }


    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public Currency getItem(int pos){
        return mList.get(pos % mList.size());
    }

    private static class ViewHolder {
        public int position;
        Currency currency;
        TextView name;
        TextView rate;
        TextView amount;
        TextView val;

        public ViewHolder(View view) {
            name = (TextView) view.findViewById(R.id.currency_txt);
            rate = (TextView) view.findViewById(R.id.rate_txt);
            amount = (TextView) view.findViewById(R.id.amount_txt);
            val = (TextView) view.findViewById(R.id.val_edt);
        }

        public Observable<Pair<Currency, Double>> getObservable(){
            return RxTextView.textChanges(val)
                    .filter(charSequence -> !charSequence.toString().isEmpty())
                    .map(val -> new Pair<>(currency, parseDouble(val)));
        }
    }

    public Observable<Pair<Currency, Double>> getObservable(){
        return mCurrencySubject;
    }

}
