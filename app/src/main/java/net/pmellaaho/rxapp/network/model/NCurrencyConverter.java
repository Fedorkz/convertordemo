package net.pmellaaho.rxapp.network.model;

import net.pmellaaho.rxapp.model.Currency;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by FedorkZ on 06/02/2017.
 */

public class NCurrencyConverter {
    public static List<Currency> convertList(List<NCurrency> nLst) {
        if (nLst == null)
            return null;

        List<Currency> lst = new ArrayList<>();
        for (NCurrency nCurrency : nLst){
            lst.add(convert(nCurrency));
        }

        return lst;
    }

    public static Currency convert(NCurrency nCurrency){
        if (nCurrency == null)
            return null;

        return new Currency(nCurrency.getCurrency(), nCurrency.getRate());
    }

}
