package net.pmellaaho.rxapp.network;


import net.pmellaaho.rxapp.R;
import net.pmellaaho.rxapp.RxApp;

import java.io.IOException;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;
import timber.log.Timber;

@Module
public class NetworkModule {

    @Provides
    GitHubApi provideGitHubApi() {
        final String ENDPOINT = "http://www.ecb.europa.eu/";
//        OkHttpClient okHttpClient = new OkHttpClient();

        //LOGS
//        okHttpClient.networkInterceptors().add(chain -> {
//            Request request = chain.request();
//            Timber.d("Sending request: " + request.url() + " with headers: " + request.headers());
//            return chain.proceed(request);
//        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ENDPOINT)
//                .client(okHttpClient)
                .addConverterFactory(SimpleXmlConverterFactory.create())
//                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        return retrofit.create(GitHubApi.class);
    }
}
