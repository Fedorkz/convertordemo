package net.pmellaaho.rxapp.ui;

import com.arellomobile.mvp.MvpPresenter;
import com.arellomobile.mvp.MvpView;

import net.pmellaaho.rxapp.model.Currency;
import net.pmellaaho.rxapp.model.CurrencyCode;

import java.util.HashMap;
import java.util.List;

/**
 * Created by FedorkZ on 06/02/2017.
 */

public abstract class ListFragmentPresenter extends MvpPresenter<ListFragmentPresenter.View> {
    public interface View extends MvpView {
        void showLoading();
        void hideLoading();
        void setRates(List<Currency> lst);
        void setUserMoney(HashMap<CurrencyCode, Double> wallet);
    }

    public abstract void resume();
    public abstract void pause();
    public abstract double calculateConvertation(Currency from, Currency to, double amount);//used to draw
    public abstract void setActiveConvertation(Currency from, Currency to, double amount);

}
