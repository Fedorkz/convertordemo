package net.pmellaaho.rxapp.ui.presenter;

import com.arellomobile.mvp.InjectViewState;

import net.pmellaaho.rxapp.RxApp;
import net.pmellaaho.rxapp.model.CurrenciesModel;
import net.pmellaaho.rxapp.model.Currency;
import net.pmellaaho.rxapp.network.model.Gesmes;
import net.pmellaaho.rxapp.ui.ListFragmentPresenter;

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
public class ListFragmentPresenterImpl extends ListFragmentPresenter{
    private Subscription mSubscription;
    @Inject CurrenciesModel mModel;

    private CompositeSubscription mSubscriptions = new CompositeSubscription();

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
    public double calculateConvertation(Currency from, Currency to, double amount){
        return 0;
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
