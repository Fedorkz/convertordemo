package com.fedorkzsoft.demo.presentation;

import android.os.Bundle;
import android.support.v4.util.Pair;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.fedorkzsoft.demo.ui.view.CurrencyPager;
import com.squareup.leakcanary.RefWatcher;

import com.fedorkzsoft.demo.R;
import com.fedorkzsoft.demo.RxApp;
import com.fedorkzsoft.demo.presentation.model.Currency;
import com.fedorkzsoft.demo.presentation.model.CurrencyCode;
import com.fedorkzsoft.demo.presentation.presenter.ConvertorFragmentPresenterImpl;

import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.functions.Action1;
import rx.subjects.PublishSubject;
import timber.log.Timber;

public class ConvertorFragment extends MvpAppCompatFragment implements ConvertorFragmentPresenter.View {

    @InjectPresenter
    ConvertorFragmentPresenterImpl mConvertorPresenter;

    PublishSubject<Currency> mCurrencySubject = PublishSubject.create();
    @Bind(R.id.progress) ProgressBar mProgress;
    @Bind(R.id.converter_lyt) View mConverterLyt;
    @Bind(R.id.pager_from) CurrencyPager mPagerFrom;
    @Bind(R.id.pager_to) CurrencyPager mPagerTo;

    public static ConvertorFragment newInstance() {
        ConvertorFragment fragment = new ConvertorFragment();
//        Bundle args = new Bundle();
//        fragment.setArguments(args);
        return fragment;
    }
    
    public ConvertorFragment() {
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_list, container, false);
        ButterKnife.bind(this, root);

        setHasOptionsMenu(true);
        return root;
    }
    void initPager(View v){
    }

    @Override
    public void onPause() {
        super.onPause();
        mConvertorPresenter.pause();
    }

    @Override
    public void onResume() {
        super.onResume();
        mConvertorPresenter.resume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Timber.d("unsubscribe");

        RefWatcher refWatcher = RxApp.getRefWatcher();
        refWatcher.watch(this);
    }

    @Override
    public void showLoading() {
        mConverterLyt.setVisibility(View.INVISIBLE);
        mProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        mConverterLyt.setVisibility(View.VISIBLE);
        mProgress.setVisibility(View.INVISIBLE);
    }

    @Override
    public void setRates(List<Currency> lst) {
        mPagerFrom.setDataList(lst);
        mPagerTo.setDataList(lst);

        mPagerFrom.getObservable()
                .subscribe(currencyDoublePair -> {
                    mConvertorPresenter.setFromCurrency(currencyDoublePair.first);
                    mConvertorPresenter.setFromAmount(currencyDoublePair.second);
                });

        mPagerTo.getObservable()
                .subscribe(currencyDoublePair -> {mConvertorPresenter.setToCurrency(currencyDoublePair.first);
                    mConvertorPresenter.setToAmount(currencyDoublePair.second);
                });
    }

    @Override
    public void setFromAmount(double amount) {
        mPagerFrom.setAmount(amount);
    }

    @Override
    public void setToAmount(double amount) {
        mPagerTo.setAmount(amount);
    }

    @Override
    public void setUserMoney(HashMap<CurrencyCode, Double> wallet) {

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_refresh) {
//            mModel.reset();
//            fetchData();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
