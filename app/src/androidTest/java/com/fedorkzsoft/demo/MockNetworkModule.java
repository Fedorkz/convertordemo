package com.fedorkzsoft.demo;

import com.fedorkzsoft.demo.model.CurrenciesModel;

import org.mockito.Mockito;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class MockNetworkModule {

    @Provides
    @Singleton
    CurrenciesModel provideContributorsModel() {
        return Mockito.mock(CurrenciesModel.class);
    }
}
