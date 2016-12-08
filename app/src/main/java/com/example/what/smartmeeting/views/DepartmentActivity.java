package com.example.what.smartmeeting.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.what.smartmeeting.R;
import com.example.what.smartmeeting.adapter.SectionsPagerAdapter;
import com.example.what.smartmeeting.service.AccountLogin;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class DepartmentActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department);
        setUpToolbar();
        setUpTabLayout();
        setUpNav();
        setUpFab();
    }

    private void setUpFab() {
        fab = (FloatingActionButton) findViewById(R.id.fabAdd);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DepartmentActivity.this,CreateMeetingActivity.class));
            }
        });
        if(AccountLogin.account_logined.getPosition()==3&&AccountLogin.account_logined.getPosition()==4){
            fab.setVisibility(View.GONE);
        }
    }

    private void setUpNav() {

    }

    private void setUpTabLayout() {
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOffscreenPageLimit(2);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    private void setUpToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Phòng "+AccountLogin.account_logined.getDepartment_name());
        toolbar.setNavigationIcon(R.drawable.ic_action_three);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        CircleImageView imgView = (CircleImageView) navigationView.getHeaderView(0).findViewById(R.id.cimgAva);
        TextView tvNavName = (TextView) navigationView.getHeaderView(0).findViewById(R.id.tvNavName);
        tvNavName.setText(AccountLogin.account_logined.getName());
        TextView tvNavPos = (TextView) navigationView.getHeaderView(0).findViewById(R.id.tvNavPosition);
        tvNavPos.setText(AccountLogin.account_logined.getPosition_name());
        Picasso.with(this).load(AccountLogin.account_logined.getPhoto_profile_url()).into(imgView);

        navigationView.setNavigationItemSelectedListener(this);
//
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        switch (item.getItemId()){
            case R.id.nav_camera:
                startActivity(new Intent(DepartmentActivity.this,DepartmentActivity.class));
                break;
            case R.id.nav_gallery:
                startActivity(new Intent(DepartmentActivity.this, ProfileMemberActivity.class));
                break;
        }
        return true;
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }


}
