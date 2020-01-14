package com.example.constraintlayout.ui;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.constraintlayout.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class DashboardActivity extends AppCompatActivity {


    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment f = null;

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    f = new NotaFragment();
                case R.id.navigation_dashboard:
                    return true;
                case R.id.navigation_notifications:
                    return true;
            }

            if (f != null) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.contenedor, new NotaFragment())
                        .commit();
            }

            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notas);

        BottomNavigationView navigation = findViewById(R.id.nav_view);
        navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);


        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.contenedor, new NotaFragment())
                .commit();


    }


}
