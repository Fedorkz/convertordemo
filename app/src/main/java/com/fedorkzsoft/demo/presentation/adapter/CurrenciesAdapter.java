package com.fedorkzsoft.demo.presentation.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fedorkzsoft.demo.R;
import com.fedorkzsoft.demo.databinding.ListItemBinding;
import com.fedorkzsoft.demo.presentation.model.Currency;

import java.util.ArrayList;
import java.util.List;

public class CurrenciesAdapter extends RecyclerView.Adapter<CurrenciesAdapter.ViewHolder> {
    private List<Currency> mDataset = new ArrayList<>();
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int pos);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ListItemBinding mBinding;
        private final View v;

        public ViewHolder(final View view, final ListItemBinding binding) {
            super(view);
            mBinding = binding;
            v = view;
        }

        @UiThread
        public void bind(final Currency currency) {
            mBinding.setCurrency(currency);
            ((TextView)v.findViewById(R.id.itemLogin)).setText(currency.getCurrency());
//            mBinding.setListener(this);
        }

        public void onItemClick(View view) {
            if (mListener != null) {
                mListener.onItemClick(getAdapterPosition());
            }
        }
    }

    @Override
    public CurrenciesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final ListItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.list_item,
                parent, false);
        return new ViewHolder(binding.getRoot(), binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(mDataset.get(position));
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public void setData(List<Currency> items) {
        mDataset.clear();
        mDataset.addAll(items);
        notifyDataSetChanged();
    }

    public void setListener(OnItemClickListener listener) {
        mListener = listener;
    }
}
