package net.pmellaaho.rxapp.model;

import net.pmellaaho.rxapp.network.GitHubApi;
import net.pmellaaho.rxapp.network.model.NCurrency;
import net.pmellaaho.rxapp.network.model.NCurrencyConverter;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.schedulers.Schedulers;
import rx.subjects.AsyncSubject;

@Singleton
public class CurrenciesModel {

    private final GitHubApi mApi;

    // Implement cache using An AsyncSubject which emits the last value
    // (and only the last value) emitted by the source Observable,
    // and only after that source Observable completes.
    private AsyncSubject<List<Currency>> mAsyncSubject;

    @Inject
    public CurrenciesModel(GitHubApi api) {
        mApi = api;
    }

    public void reset() {
        mAsyncSubject = null;
    }

    public Observable<List<Currency>> getRequest() {
        return mAsyncSubject;
    }

    public Observable<List<Currency>> getContributors() {

        if (mAsyncSubject == null) {
            mAsyncSubject = AsyncSubject.create();

            mApi.currencyRates()
                .subscribeOn(Schedulers.io())
                .map(gesmes -> {
                    List<NCurrency> currency = gesmes.getCube().getCube().getCurrency();
                    return NCurrencyConverter.convertList(currency);
                }).subscribe(mAsyncSubject);
        }
        return mAsyncSubject;
    }
}