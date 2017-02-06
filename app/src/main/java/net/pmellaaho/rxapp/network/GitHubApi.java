package net.pmellaaho.rxapp.network;

import net.pmellaaho.rxapp.network.model.Gesmes;

import retrofit2.http.GET;
import rx.Observable;

public interface GitHubApi {

    /**
     * See https://developer.github.com/v3/repos/#list-contributors
     */
    @GET("/stats/eurofxref/eurofxref-daily.xml")
    Observable<Gesmes> currencyRates();
}
