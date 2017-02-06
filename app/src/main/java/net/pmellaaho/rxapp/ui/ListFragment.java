package net.pmellaaho.rxapp.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.squareup.leakcanary.RefWatcher;

import net.pmellaaho.rxapp.R;
import net.pmellaaho.rxapp.RxApp;
import net.pmellaaho.rxapp.model.Currency;
import net.pmellaaho.rxapp.model.CurrencyCode;
import net.pmellaaho.rxapp.network.model.Gesmes;
import net.pmellaaho.rxapp.ui.presenter.ListFragmentPresenterImpl;

import java.util.HashMap;
import java.util.List;

import rx.Subscriber;
import timber.log.Timber;

public class ListFragment extends MvpAppCompatFragment implements ListFragmentPresenter.View {

    @InjectPresenter
    ListFragmentPresenterImpl mListPresenter;

    private RecyclerView mList;
    private CurrenciesAdapter mAdapter;
    private ProgressBar mProgress;

    public static ListFragment newInstance(String owner, String repo) {
        ListFragment fragment = new ListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    
    public ListFragment() {
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_list, container, false);
        mProgress = (ProgressBar) root.findViewById(R.id.progress);

        mList = (RecyclerView) root.findViewById(R.id.recyclerView);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mList.setLayoutManager(layoutManager);
//        mList.addItemDecoration(new DividerItemDecoration(getActivity()));

        mAdapter = new CurrenciesAdapter();
        mList.setAdapter(mAdapter);
//        mList.setItemAnimator(new DefaultItemAnimator());

//        mModel = ((RxApp)getActivity().getApplication()).component().currenciesModel();
        setHasOptionsMenu(true);
        return root;
    }

    @Override
    public void onPause() {
        super.onPause();
        mListPresenter.pause();
    }

    @Override
    public void onResume() {
        super.onResume();
        mListPresenter.resume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Timber.d("unsubscribe");

        RefWatcher refWatcher = RxApp.getRefWatcher();
        refWatcher.watch(this);
    }

    @Override
    public void showLoading() {
        mList.setVisibility(View.INVISIBLE);
        mProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        mList.setVisibility(View.VISIBLE);
        mProgress.setVisibility(View.INVISIBLE);
    }

    @Override
    public void setRates(List<Currency> lst) {
        mAdapter.setData(lst);
    }

    @Override
    public void setUserMoney(HashMap<CurrencyCode, Double> wallet) {

    }

    private class ContributorsSubscriber extends Subscriber<Gesmes> {

        @Override
        public void onNext(Gesmes contributors) {
            Timber.d("received data from model");
//            mAdapter.setData(contributors);
        }

        @Override
        public void onCompleted() {
            Timber.d("request completed");
            mProgress.setVisibility(View.INVISIBLE);
            mList.setVisibility(View.VISIBLE);
        }

        @Override
        public void onError(Throwable e) {
            Timber.e(e, "request failed");
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_refresh) {
//            mModel.reset();
//            fetchData();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
