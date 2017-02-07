package com.fedorkzsoft.demo;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import com.fedorkzsoft.demo.model.CurrenciesModel;
import com.fedorkzsoft.demo.network.NetworkModule;
import com.fedorkzsoft.demo.presentation.presenter.ConvertorFragmentPresenterImpl;

import javax.inject.Singleton;

import dagger.Component;
import timber.log.Timber;

public class RxApp extends Application {

    @Singleton
    @Component(modules = NetworkModule.class)
    public interface NetworkComponent {
        CurrenciesModel currenciesModel();
        void inject(ConvertorFragmentPresenterImpl presenter);
    }

    private NetworkComponent mComponent = null;

    private RefWatcher mRefWatcher;
    public static RefWatcher getRefWatcher() {
        return RxApp.get().mRefWatcher;

    }
    private static RxApp sInstance;
    public static RxApp get() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        if (mComponent == null) {
            mComponent = DaggerRxApp_NetworkComponent.create();
        }

        sInstance = (RxApp) getApplicationContext();
        mRefWatcher = LeakCanary.install(this);
        Timber.plant(new Timber.DebugTree());
    }

    public NetworkComponent component() {
        return mComponent;
    }

    // This allows providing mock NetworkComponent from test
    public void setComponent(NetworkComponent component) {
        mComponent = component;
    }
}
