package com.example.what.smartmeeting.views;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.example.what.smartmeeting.R;
import com.example.what.smartmeeting.adapter.AvatarAdapter;
import com.example.what.smartmeeting.adapter.MeetingAdapter;
import com.example.what.smartmeeting.entity.Account;
import com.example.what.smartmeeting.entity.Meetings;
import com.example.what.smartmeeting.service.AccountLogin;
import com.example.what.smartmeeting.service.JSONPaser;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * resize ảnh và padding
 */
public class ProfileMemberInRom extends AppCompatActivity implements AvatarAdapter.Clicklistener {
    public static final String GET_MEMBER_ID = "http://hungntph04073.esy.es/get_photo_id_department.php";
    public static final String TAG_SUSCESS = "suscess";
    public static final String TAG_NAME = "name";
    public static final String TAG_EMAIL = "email";
    public static final String TAG_PHOTO_URL = "photo_url";
    public static final String TAG_POSITION_NAME = "position_name";
    public static final String TAG_DEPARTMENT_NAME = "department_name";

    private AvatarAdapter adapter;
    private ArrayList<Account> list;
    private Toolbar toolbar;
    private MeetingAdapter adapter1;
    private RecyclerView rcvAvatar;
    private ArrayList<Account> listAcc;
    private RecyclerView rcvMeeting;
    private TextView tvName;
    private TextView tvPosition;
    private TextView tvDepartment;
    private TextView tvEmail;
    private CircleImageView imgProfile;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_member_inrom);
        innt();
        setUpToolBar();


        RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(getBaseContext());
        rcvMeeting.setLayoutManager(layoutManager1);
        adapter1 = new MeetingAdapter(Meetings.createListDummyData());
        rcvMeeting.setItemAnimator(new DefaultItemAnimator());
        rcvMeeting.setAdapter(adapter1);
        rcvMeeting.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getBaseContext(), LinearLayoutManager.HORIZONTAL, false);
        rcvAvatar.setLayoutManager(layoutManager);
        rcvAvatar.setItemAnimator(new DefaultItemAnimator());
        rcvAvatar.setHasFixedSize(true);
        new LoadMember().execute();
    }

    private void setUpToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Profile ");
    }

    private void innt() {
        rcvAvatar = (RecyclerView) findViewById(R.id.rcvAvatar);
        rcvMeeting = (RecyclerView) findViewById(R.id.rvMeeting);
        tvDepartment = (TextView) findViewById(R.id.tvDepartment);
        tvEmail = (TextView) findViewById(R.id.tvEmail);
        tvName = (TextView) findViewById(R.id.tvName);
        tvPosition = (TextView) findViewById(R.id.tvPosition);
        imgProfile = (CircleImageView) findViewById(R.id.imgProfile);
        tvName.setTextColor(Color.parseColor("#FFFFFF"));
        tvEmail.setTextColor(Color.parseColor("#FFFFFF"));
        tvPosition.setTextColor(Color.parseColor("#FFFFFF"));
        tvDepartment.setTextColor(Color.parseColor("#FFFFFF"));
        progressDialog = new ProgressDialog(ProfileMemberInRom.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Xin chờ...");
        progressDialog.setCancelable(false);
    }

    private ArrayList<Account> getListMember() {
        return null;
    }

    public class LoadMember extends AsyncTask<Void, Void, ArrayList<Account>> {
        int department_id;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            department_id = AccountLogin.account_logined.getDepartment();
            listAcc = new ArrayList<Account>();
            progressDialog.show();
        }

        @Override
        protected ArrayList<Account> doInBackground(Void... params) {

            JSONPaser jsonPaser = new JSONPaser();
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("department_id", String.valueOf(department_id)));
            JSONObject jsonObject = jsonPaser.makeHttpRequest(GET_MEMBER_ID, nameValuePairs);
            Log.d("DÂTA", jsonObject.toString());
            try {
                if (jsonObject.getInt(TAG_SUSCESS) == 0) {
                    JSONArray jsonArray = jsonObject.getJSONArray("account");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonAcc = jsonArray.getJSONObject(i);
                        String name = jsonAcc.getString(TAG_NAME);
                        String email = jsonAcc.getString(TAG_EMAIL);
                        String photo_url = jsonAcc.getString(TAG_PHOTO_URL);
                        String position = jsonAcc.getString(TAG_POSITION_NAME);
                        String department = jsonAcc.getString(TAG_DEPARTMENT_NAME);
                        Account account = new Account(email, name, position, department, photo_url);
                        listAcc.add(account);
                    }
                    Log.d("SIZE", listAcc.size() + "  -------------");
                }
                return null;
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return listAcc;
        }
        @Override
        protected void onPostExecute(ArrayList<Account> accounts) {
            super.onPostExecute(accounts);
            adapter = new AvatarAdapter(listAcc);
            adapter.setclicklistener(ProfileMemberInRom.this);
            rcvAvatar.setAdapter(adapter);
//            Log.d("SIZE", accounts.size()+"  -------------");
            tvPosition.setText("Chức vụ :"+listAcc.get(0).getPosition_name());
            tvDepartment.setText("Phòng :"+listAcc.get(0).getDepartment_name());
            tvEmail.setText("Email :"+listAcc.get(0).getEmail());
            tvName.setText(listAcc.get(0).getName());
            imgProfile.setImageBitmap(listAcc.get(0).getBitmap());
            progressDialog.dismiss();
        }
    }

    @Override
    public void Itemclicked(View view, int position) {
        tvPosition.setText("Chức vụ :"+listAcc.get(position).getPosition_name());
        tvDepartment.setText("Phòng :"+listAcc.get(position).getDepartment_name());
        tvEmail.setText("Email :"+listAcc.get(position).getEmail());
        tvName.setText(listAcc.get(position).getName());
        imgProfile.setImageBitmap(listAcc.get(position).getBitmap());
    }

}
