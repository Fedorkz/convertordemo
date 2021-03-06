package com.fedorkzsoft.demo;

import android.app.Instrumentation;

import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.fedorkzsoft.demo.model.CurrenciesModel;
import com.fedorkzsoft.demo.presentation.MainActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Singleton;

import dagger.Component;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    CurrenciesModel mModel;

    @Singleton
    @Component(modules = MockNetworkModule.class)
    public interface MockNetworkComponent extends RxApp.NetworkComponent {
    }

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class,
            true,     // initialTouchMode
            false);   // launchActivity.

    @Before
    public void setUp() {
        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        RxApp app = (RxApp) instrumentation.getTargetContext()
                .getApplicationContext();

        MockNetworkComponent testComponent = DaggerMainActivityTest_MockNetworkComponent.builder()
                .mockNetworkModule(new MockNetworkModule())
                .build();
        app.setComponent(testComponent);
        mModel = testComponent.currenciesModel();
    }

    @Test
    public void listWithTwoContributors() {
//
//        // GIVEN
//        List<Contributor> tmpList = new ArrayList<>();
//        tmpList.add(new Contributor("Jesse", 600));
//        tmpList.add(new Contributor("Jake", 200));
//
//        Observable<List<Contributor>> testObservable = Observable.just(tmpList);
//
//        Mockito.when(mModel.getContributors(anyString(), anyString()))
//                .thenReturn(testObservable);
//
//        // WHEN
//        mActivityRule.launchActivity(new Intent());
//        onView(withId(R.id.startBtn)).perform(click());
//
//        // THEN
//        onView(ViewMatchers.nthChildOf(withId(R.id.recyclerView), 0))
//                .check(matches(hasDescendant(withText("Jesse"))));
//
//        onView(ViewMatchers.nthChildOf(withId(R.id.recyclerView), 0))
//                .check(matches(hasDescendant(withText("600"))));
//
//        onView(ViewMatchers.nthChildOf(withId(R.id.recyclerView), 1))
//                .check(matches(hasDescendant(withText("Jake"))));
//
//        onView(ViewMatchers.nthChildOf(withId(R.id.recyclerView), 1))
//                .check(matches(hasDescendant(withText("200"))));
    }

    @Test
    public void errorFromNetwork() {

//        // GIVEN
//        // create an Observable that emits nothing and then signals an error
//        Observable<List<Contributor>> errorEmittingObservable = Observable.error(new IllegalArgumentException());
//
//        Mockito.when(mModel.getContributors(anyString(), anyString()))
//                .thenReturn(errorEmittingObservable);
//
//        // WHEN
//        mActivityRule.launchActivity(new Intent());
//        onView(withId(R.id.startBtn)).perform(click());
//
//        // THEN
//        onView(withId(R.id.errorText))
//                .check(matches(isDisplayed()));
    }
}
