package com.fedorkzsoft.demo.presentation;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.fedorkzsoft.demo.R;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            return;
        }

        FragmentManager manager = getSupportFragmentManager();
        ConvertorFragment fragment = new ConvertorFragment();
        manager.beginTransaction()
                .add(R.id.fragment_container, fragment).commit();
    }
}
