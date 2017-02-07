package com.fedorkzsoft.demo.presentation.presenter;

import com.arellomobile.mvp.InjectViewState;

import com.fedorkzsoft.demo.RxApp;
import com.fedorkzsoft.demo.model.CurrenciesModel;
import com.fedorkzsoft.demo.presentation.model.Currency;
import com.fedorkzsoft.demo.presentation.ConvertorFragmentPresenter;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

/**
 * Created by FedorkZ on 06/02/2017.
 */
@InjectViewState
public class ConvertorFragmentPresenterImpl extends ConvertorFragmentPresenter {
    private Subscription mSubscription;

    @Inject CurrenciesModel mModel;

    private CompositeSubscription mSubscriptions = new CompositeSubscription();
    private Currency mFromCurrency;
    private Currency mToCurrency;
    private double mToAmount;
    private double mFromAmount;
    private boolean mIsForwardConversion;

    @Override
    public void attachView(View view) {
        RxApp.get().component().inject(this);

        super.attachView(view);
    }

    @Override
    public void resume() {
        startUpdates();
        getViewState().showLoading();
    }

    @Override
    public void pause() {
        stopUpdates();
    }

    @Override
    public void setFromCurrency(Currency from) {
        mIsForwardConversion = true;
        mFromCurrency = from;
    }

    @Override
    public void setToCurrency(Currency to) {
        mIsForwardConversion = false;
        mToCurrency = to;
    }

    @Override
    public void setFromAmount(double from) {
        mIsForwardConversion = true;
        mFromAmount = from;
        if (mFromCurrency != null && mToCurrency != null) {
            double v = calculateConvertation(mFromCurrency, mToCurrency, mFromAmount);
            if (mToAmount != v) {
                mToAmount = v;
                getViewState().setToAmount(mToAmount);
            }
        }

    }

    @Override
    public void setToAmount(double to) {
        mIsForwardConversion = false;
        mToAmount = to;
        if (mFromCurrency != null && mToCurrency != null) {
            double v = calculateConvertation(mToCurrency, mFromCurrency, mToAmount);
            if (mFromAmount != v) {
                mFromAmount = v;
                getViewState().setFromAmount(mFromAmount);
            }
        }
    }

    @Override
    public double calculateConvertationFrom(Currency from, double amount) {
        if (mToCurrency == null || from == null)
            return 0;

        return calculateConvertation(from, mToCurrency, amount);
    }

    @Override
    public double calculateConvertationTo(Currency to, double amount) {
        if (mFromCurrency == null || to == null)
            return 0;

        return calculateConvertation(to, mFromCurrency, amount);
    }

    @Override
    public double calculateConvertation(Currency from, Currency to, double amount){
        if (from == null || to == null)
            return  0;
        return from.getRate() / to.getRate() * amount;
    }

    @Override
    public void setActiveConvertation(Currency from, Currency to, double amount) {

    }

    protected void startUpdates(){
        Observable<Long> values = Observable.interval(0, 5, TimeUnit.SECONDS);
        mSubscription = values.subscribe(v -> scheduleUpdate(),
                e -> System.out.println("Error: " + e),
                () -> System.out.println("Completed")
        );
    }

    protected void stopUpdates(){
        if (mSubscription != null && !mSubscription.isUnsubscribed())
            mSubscription.unsubscribe();
    }

    private void scheduleUpdate() {
        mSubscriptions.add(mModel.getContributors()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ContributorsSubscriber()));
    }

    private class ContributorsSubscriber extends Subscriber<List<Currency>> {

        @Override
        public void onNext(List<Currency> currencies) {
            Timber.d("received data from model");
            getViewState().hideLoading();
            getViewState().setRates(currencies);
        }

        @Override
        public void onCompleted() {
            Timber.d("request completed");
//            mProgress.setVisibility(android.view.View.INVISIBLE);
//            mList.setVisibility(android.view.View.VISIBLE);
        }

        @Override
        public void onError(Throwable e) {
            Timber.e(e, "request failed");
        }
    }

}
