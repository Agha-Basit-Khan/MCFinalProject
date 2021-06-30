package com.aghabasit.finalprojectei;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView bottomNavigationView;
    private FrameLayout frameLayout;

    //Fragment

    private DashboardFragment dashboardFragment;
    private IncomeFragment incomeFragment;
    private ExpenseFragment expenseFragment;
    private StatsFragment statsFragment;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mAuth=FirebaseAuth.getInstance();
        Toolbar toolbar=findViewById(R.id.my_toolbar);
        toolbar.setTitle("Expense Manager");
        //setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        DrawerLayout drawerLayout=findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle= new ActionBarDrawerToggle
                (
                        this,
                        drawerLayout,
                        toolbar,
                        R.string.navigation_drawer_open,
                        R.string.navigation_drawer_close
                )
        {};
        toggle.setDrawerIndicatorEnabled(true);
        drawerLayout.addDrawerListener(toggle);


        toggle.syncState();

        NavigationView navigationView=findViewById(R.id.navView);
        navigationView.setNavigationItemSelectedListener(this);

        bottomNavigationView=findViewById(R.id.bottomNavbar);
        frameLayout=findViewById(R.id.main_frame);

        dashboardFragment=new DashboardFragment();
        incomeFragment=new IncomeFragment();
        expenseFragment=new ExpenseFragment();
        statsFragment=new StatsFragment();

        setFragment(dashboardFragment);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){

                    case R.id.dashboard:
                        setFragment(dashboardFragment);
                        return true;
                    case R.id.income:
                        setFragment(incomeFragment);
                        return true;
                    case R.id.expense:
                        setFragment(expenseFragment);
                        return true;
                    case R.id.stats:
                        setFragment(statsFragment);
                        return true;

                    default:
                        return false;


                }
            }
        });
    }
}