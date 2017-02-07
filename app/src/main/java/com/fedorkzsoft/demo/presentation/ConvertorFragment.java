package com.fedorkzsoft.demo.presentation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

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
        View root = inflater.inflate(R.layout.fragment_exchange, container, false);
        ButterKnife.bind(this, root);

        setHasOptionsMenu(true);
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initPager();
    }

    void initPager(){
        mPagerFrom.getObservable()
                .subscribe(currencyDoublePair -> {
                    mConvertorPresenter.setFromCurrency(currencyDoublePair.first);
                    mConvertorPresenter.setFromAmount(currencyDoublePair.second);
                    mPagerTo.updateViews();

                });

        mPagerTo.getObservable()
                .subscribe(currencyDoublePair -> {mConvertorPresenter.setToCurrency(currencyDoublePair.first);
                    mConvertorPresenter.setToCurrency(currencyDoublePair.first);
                    mConvertorPresenter.setToAmount(currencyDoublePair.second);
                    mPagerFrom.updateViews();
                });

        mPagerFrom.getScrollObservable()
                .subscribe(currency -> {
                    mConvertorPresenter.setFromCurrency(currency);
                    mPagerFrom.updateViews();
                });

        mPagerTo.getScrollObservable()
                .subscribe(currency -> {
                    mConvertorPresenter.setToCurrency(currency);
                    mPagerTo.updateViews();
                });

        mPagerTo.setCalcResolver(cur -> mConvertorPresenter.recalculateConvertationTo());
        mPagerFrom.setCalcResolver(cur -> mConvertorPresenter.recalculateConvertationFrom());

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
    public void showError(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showSuccess(String succes) {
        Toast.makeText(getContext(), "Successful transaction! " + succes, Toast.LENGTH_LONG).show();
    }

    @Override
    public void setRates(List<Currency> lst) {
        mPagerFrom.setDataList(lst);
        mPagerTo.setDataList(lst);
    }

    @Override
    public void setFromAmount(double amount) {
        mPagerFrom.updateViews();
//        mPagerFrom.setAmount(amount);
    }

    @Override
    public void setToAmount(double amount) {
        mPagerFrom.updateViews();
//        mPagerTo.setAmount(amount);
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

        if (id == R.id.action_submit) {
            mConvertorPresenter.applyTransaction();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
