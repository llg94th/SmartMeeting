package com.example.what.smartmeeting.views;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.what.smartmeeting.entity.Departments;
import com.example.what.smartmeeting.receiver.InternetReceiver;
import com.example.what.smartmeeting.service.AccountLogin;
import com.example.what.smartmeeting.service.JSONPaser;
import com.example.what.smartmeeting.R;
import com.example.what.smartmeeting.entity.Account;
import com.example.what.smartmeeting.preference.LoginPreference;
import com.example.what.smartmeeting.service.RegistrationIntentService;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, InternetReceiver.ChangeInternetState {
    private EditText etAcc;
    private EditText etPass;
    private static String URL_LOGIN = "http://hungntph04073.esy.es/get_account_login.php";
    private static String TAG_SUSCESS = "suscess";
    private static String TAG_ID = "id";
    private static String TAG_PASSWORD = "password";
    private String id;
    private String password;
    private int suscess;
    private ProgressDialog progressDialog;
    private LoginPreference preference;
    private Button btnLogin;
    private CoordinatorLayout coordinatorLayout;
    private Snackbar snackbar;
    InternetReceiver internetReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        innt();
        checklogin();
        Intent i = new Intent("checkinternet");
        sendBroadcast(i);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new loginAsync().execute();

            }
        });

    }

    private void checklogin() {

        boolean isLogined = preference.islogined();
        if (isLogined) {
            Account accLogin = preference.getLogined();
            id = accLogin.getId();
            password = accLogin.getPassword();
            etAcc.setText(id);
            etPass.setText(password);
            new loginAsync().execute();

        }
    }

    private void innt() {
        etAcc = (EditText) findViewById(R.id.etAccount);
        etPass = (EditText) findViewById(R.id.etPass);
        preference = new LoginPreference(getBaseContext());
        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Đăng nhập");
        progressDialog.setCancelable(false);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setTextColor(Color.WHITE);
        InternetReceiver.setChangeInternetState(this);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.relativeLayout);
        snackbar = Snackbar
                .make(coordinatorLayout, "không kết nối mạng", Snackbar.LENGTH_INDEFINITE);
        etPass.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId== EditorInfo.IME_ACTION_DONE){
                    new loginAsync().execute();
                }

                return false;
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }


    public class loginAsync extends AsyncTask<Void, Void, Integer> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
            id = etAcc.getText().toString();
            password = etPass.getText().toString();
        }

        @Override
        protected Integer doInBackground(Void... params) {
            int suscess = -1;
            JSONPaser jsonPaser = new JSONPaser();
            Account account = null;
            Departments departments = null;
            List<NameValuePair> valuePairs = new ArrayList<NameValuePair>();
            valuePairs.add(new BasicNameValuePair(TAG_ID, id));
            valuePairs.add(new BasicNameValuePair(TAG_PASSWORD, password));
            JSONObject jsonObject = jsonPaser.makeHttpRequest(URL_LOGIN, valuePairs);
            String json = "";
            JSONArray acccount;
            int jsonchar;
            try {
                if ((suscess = jsonObject.getInt(TAG_SUSCESS)) == 0) {
                    acccount = jsonObject.getJSONArray("acccount");
                    for (int i = 0; i < acccount.length(); i++) {
                        JSONObject object = acccount.getJSONObject(i);
                        String name = object.getString("name");
                        String email = object.getString("email");
                        String url = object.getString("email");
                        int department_id = object.getInt("department_id");
                        int position_id = object.getInt("position_id");
                        String department_name = object.getString("department_name");
                        String position_name = object.getString("position_name");
                        String photo_url = object.getString("photo_url");
                        int is_fisrt = object.getInt("is_first");
                        boolean is_Firstbl = true;
                        if (is_fisrt == 1) {
                            is_Firstbl = false;
                        }
                        if (is_fisrt == 1) {
                            suscess = 1;
                        }
                        AccountLogin.account_logined = new Account(id, email, password, name, department_id, position_id, is_Firstbl, position_name, photo_url, department_name);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return suscess;
        }

        @Override
        protected void onPostExecute(Integer aBoolean) {
            super.onPostExecute(aBoolean);
            progressDialog.dismiss();
            if (aBoolean == 1) {
                Intent intent = new Intent(LoginActivity.this, DepartmentActivity.class);
                Intent intent2 = new Intent(LoginActivity.this, RegistrationIntentService.class);
                intent2.putExtra("user",id);
                preference.setLogined(new Account(id, password));
                startService(intent2);
                startActivity(intent);

            } else if (aBoolean == 2) {
                Toast.makeText(getBaseContext(), "Sai tài khoản ,mật khẩu", Toast.LENGTH_SHORT).show();
            } else if (aBoolean == 0) {
                Intent intent = new Intent(LoginActivity.this, VerifyInfoActivity.class);
                intent.putExtra("isFirst", true);
                startActivity(intent);
                preference.setLogined(new Account(id, password));
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onChageInternetState(boolean isOffline) {
        if (isOffline) {
            btnLogin.setEnabled(true);
            snackbar.dismiss();
            checklogin();
        } else {
            snackbar.show();
            btnLogin.setEnabled(false);
        }
    }
}
