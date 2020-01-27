package com.sierzega.pagingclean;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.sierzega.pagingclean.db.RepositoryProvider;
import com.sierzega.pagingclean.fragment.PaginationFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RepositoryProvider.getInstance().initDatabase(getApplicationContext());


        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();


        PaginationFragment fragment = new PaginationFragment();
        fragmentTransaction.add(R.id.content_main, fragment);
        fragmentTransaction.commit();




    }
}
