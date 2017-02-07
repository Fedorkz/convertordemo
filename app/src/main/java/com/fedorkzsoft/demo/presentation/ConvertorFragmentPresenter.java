package com.fedorkzsoft.demo.presentation;

import android.support.v4.util.Pair;

import com.arellomobile.mvp.MvpPresenter;
import com.arellomobile.mvp.MvpView;

import com.fedorkzsoft.demo.presentation.model.Currency;
import com.fedorkzsoft.demo.presentation.model.CurrencyCode;

import java.util.HashMap;
import java.util.List;

import rx.Observable;

/**
 * Created by FedorkZ on 06/02/2017.
 */

public abstract class ConvertorFragmentPresenter extends MvpPresenter<ConvertorFragmentPresenter.View> {
    public interface View extends MvpView {
        void showLoading();
        void hideLoading();

        void showError(String error);// Here should be some error object
        void showSuccess(String error);// Here should be some error object

        void setRates(List<Currency> lst);
        void setUserMoney(HashMap<CurrencyCode, Double> wallet);

        void setFromAmount(double amount);
        void setToAmount(double amount);
    }

    public abstract void resume();
    public abstract void pause();

    public abstract void setFromCurrency(Currency from);
    public abstract void setToCurrency(Currency to);

    public abstract void setFromAmount(double from);
    public abstract void setToAmount(double to);

    public abstract double calculateConvertationFrom(Currency from, double amount);//used to draw
    public abstract double calculateConvertationTo(Currency to, double amount);//used to draw

    public abstract double recalculateConvertationFrom();//used to draw
    public abstract double recalculateConvertationTo();//used to draw

    public abstract double calculateConvertation(Currency from, Currency to, double amount);//used to draw
    public abstract void setActiveConvertation(Currency from, Currency to, double amount);

    public abstract void applyTransaction();
}
