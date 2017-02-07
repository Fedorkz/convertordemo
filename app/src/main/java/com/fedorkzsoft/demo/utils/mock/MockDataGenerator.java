package com.fedorkzsoft.demo.utils.mock;


import com.fedorkzsoft.demo.presentation.model.Currency;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RxRead on 2015/9/24.
 */
public class MockDataGenerator {
    public static String[] CURS = {"EUR", "USD", "RUR", "CZK", "AUD", "FOX", "EUR", "ZBD"};

    public static List<Currency> getViewPagerData(){
        List<Currency> list=new ArrayList<>();
        Currency item=null;
        for(int i=0;i<8;i++){
            item=new Currency();
            item.setRate(i * 1.12 + 1);
            item.setCurrency(CURS[i]);
            list.add(item);
        }
        return list;
    }

    public static List<Currency> getViewPagerData2(){
        return getViewPagerData();
    }
}
