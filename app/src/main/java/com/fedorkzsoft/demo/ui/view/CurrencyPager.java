package com.fedorkzsoft.demo.ui.view;

import android.content.Context;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.zanlabs.widget.infiniteviewpager.InfiniteViewPager;
import com.zanlabs.widget.infiniteviewpager.indicator.PageIndicator;

import com.fedorkzsoft.demo.R;
import com.fedorkzsoft.demo.presentation.model.Currency;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;

/**
 * Created by FedorkZ on 07/02/2017.
 */

public class CurrencyPager extends RelativeLayout{

    @Bind(R.id.viewpager) InfiniteViewPager mViewPager;
    @Bind(R.id.indicator) PageIndicator mCircleIndicator;
    private CurrencyPagerAdapter mPagerAdapter;

    public CurrencyPager(Context context) {
        super(context);
        init(context);
    }

    public CurrencyPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CurrencyPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.view_currency_pager, this, true);

        ButterKnife.bind(this, this);
        initPager();
    }

    void initPager(){
        mPagerAdapter = new CurrencyPagerAdapter(getContext());
        mViewPager.setAdapter(mPagerAdapter);

//        mViewPager.setAutoScrollTime(5000);
        mViewPager.stopAutoScroll();
        mCircleIndicator.setViewPager(mViewPager);

        setupPagerTransformation();

        setupPagerPageChangeListener();
        mCircleIndicator.setViewPager(mViewPager);
    }

    public Observable<Pair<Currency, Double>> getObservable(){
        return mPagerAdapter.getObservable();
    }

    private void setupPagerPageChangeListener() {
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener(){

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
//                if (mOnCurrencyChangeListener != null){}
//                    mOnCurrencyChangeListener.currencyChanged(mPagerAdapter.getItem(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setupPagerTransformation() {
        mViewPager.setPageTransformer(false, (view, position) -> {
            int pageWidth = view.getWidth();

            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0);

            } else if (position <= 1) { // [-1,1]
                view.setAlpha(1);

                View msg = view.findViewById(R.id.val_edt);
                if (msg != null) {
                    float translationX = (float) ((position) * 1.5 * pageWidth);
                    msg.setTranslationX(translationX);
                }
                View title = view.findViewById(R.id.currency_txt);
                if (title != null) {
                    float translationX = (float) ((position) * 0.5 * pageWidth);
                    title.setTranslationX(translationX);
                }

            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0);
            }
        });
    }

    public void addOnPageChangeListener(ViewPager.OnPageChangeListener lst){
        mViewPager.addOnPageChangeListener(lst);
    }

    public void setDataList(List<Currency> list){
        mPagerAdapter.setDataList(list);
    }

    boolean mMuteVal = false;
    public void setAmount(double amount) {
        mMuteVal = true;
        View viewWithTag = mViewPager.findViewWithTag(mViewPager.getCurrentItem());
        if (viewWithTag != null && viewWithTag instanceof EditText){
            ((EditText)viewWithTag).setText(String.valueOf(amount));
        }
        mMuteVal = false;
    }
}
