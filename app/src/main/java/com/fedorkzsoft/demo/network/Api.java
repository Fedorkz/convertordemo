package com.fedorkzsoft.demo.network;

import com.fedorkzsoft.demo.network.model.Gesmes;

import retrofit2.http.GET;
import rx.Observable;

public interface Api {

    /**
     *
     */
    @GET("/stats/eurofxref/eurofxref-daily.xml")
    Observable<Gesmes> currencyRates();
}
