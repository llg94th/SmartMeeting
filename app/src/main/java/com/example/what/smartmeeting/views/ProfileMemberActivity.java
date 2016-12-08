package com.example.what.smartmeeting.views;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.what.smartmeeting.R;
import com.example.what.smartmeeting.adapter.MeetingAdapter;
import com.example.what.smartmeeting.entity.Meetings;
import com.example.what.smartmeeting.service.AccountLogin;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by what on 25/03/2016.
 */
public class ProfileMemberActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView tvName;
    private TextView tvPosition;
    private TextView tvDepartment;
    private TextView tvEmail;
    private RecyclerView rvMeeting;
    private MeetingAdapter adapter;
    private Meetings meetings;
    private CircleImageView imgProfile;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_nember);
        innt();
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Profile ");
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getBaseContext());
        rvMeeting.setLayoutManager(layoutManager);

        rvMeeting.setItemAnimator(new DefaultItemAnimator());
        adapter = new MeetingAdapter(Meetings.createListDummyData());
        rvMeeting.setAdapter(adapter);
        FloatingActionButton actionButton = (FloatingActionButton) findViewById(R.id.fabEdit);
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProfileMemberActivity.this,VerifyInfoActivity.class);
                startActivity(i);
            }
        });


    }


    private void innt() {
        toolbar = (Toolbar) findViewById(R.id.toolbar1);
        rvMeeting = (RecyclerView) findViewById(R.id.rvMeeting);
        tvDepartment = (TextView) findViewById(R.id.tvDepartment);
        tvEmail = (TextView) findViewById(R.id.tvEmail);
        tvName = (TextView) findViewById(R.id.tvName);
        tvPosition = (TextView) findViewById(R.id.tvPosition);
        imgProfile = (CircleImageView) findViewById(R.id.imgProfile);
        tvName.setTextColor(Color.parseColor("#FFFFFF"));
        tvEmail.setTextColor(Color.parseColor("#FFFFFF"));
        tvPosition.setTextColor(Color.parseColor("#FFFFFF"));
        tvDepartment.setTextColor(Color.parseColor("#FFFFFF"));

        tvPosition.setText("Chức vụ :" + AccountLogin.account_logined.getPosition_name());
        tvDepartment.setText("Phòng :" + AccountLogin.account_logined.getDepartment_name());
        tvEmail.setText("Email :"+AccountLogin.account_logined.getEmail());
        tvName.setText(AccountLogin.account_logined.getName());
        imgProfile.setImageBitmap(AccountLogin.account_logined.getBitmap());

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


}
