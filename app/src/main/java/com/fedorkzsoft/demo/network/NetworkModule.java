package com.fedorkzsoft.demo.network;


import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

@Module
public class NetworkModule {

    @Provides
    Api provideApi() {
        final String ENDPOINT = "http://www.ecb.europa.eu/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ENDPOINT)
//                .client(okHttpClient)
                .addConverterFactory(SimpleXmlConverterFactory.create())
//                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        return retrofit.create(Api.class);
    }
}
