package com.example.what.smartmeeting.severcontroler;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.widget.TextView;

import com.example.what.smartmeeting.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by ngoc on 4/12/2016.
 */
public class LoadDepartmentInfo extends AsyncTask<String, Void, LoadDepartmentInfo.DepartmentInfo> {

    TextView tvName, tvBoss, tvEmail, tvAccount, tvMeeting;
    Fragment mfragment;
    SweetAlertDialog ccc;
    final String GET_INFO_URL = "http://hungntph04073.esy.es/get-department-info.php?department_id=";

    public LoadDepartmentInfo(Fragment mfragment) {
        this.mfragment = mfragment;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        tvName = (TextView) mfragment.getActivity().findViewById(R.id.tvName);
        tvBoss = (TextView) mfragment.getActivity().findViewById(R.id.tvMasterName);
        tvEmail = (TextView) mfragment.getActivity().findViewById(R.id.tvEmail);
        tvMeeting = (TextView) mfragment.getActivity().findViewById(R.id.tvTotalMeeting);
        tvAccount = (TextView) mfragment.getActivity().findViewById(R.id.tvTotalMem);
        ccc = new SweetAlertDialog(mfragment.getContext(), SweetAlertDialog.PROGRESS_TYPE).setTitleText("Loading...");
        ccc.setCancelable(false);
        ccc.show();
    }

    @Override
    protected DepartmentInfo doInBackground(String... maPhong) {
        String stringurl = GET_INFO_URL + maPhong[0];
        DepartmentInfo info =null;
        try {
            String result = "";
            URL url = new URL(stringurl);
            InputStream is = url.openStream();
            int byteCharacter;
            while ((byteCharacter = is.read()) != -1) {
                char c = (char) byteCharacter;
                result += c;
            }
            JSONObject object = new JSONObject(result);
            String name = object.getString("name");
            String boss = object.getString("boss");
            String email = object.getString("email");
            String total_account = object.getString("total_account");
            String total_meeting = object.getString("total_meeting");
            info = new DepartmentInfo(name, boss, email, total_account, total_meeting);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return info;
    }

    @Override
    protected void onPostExecute(DepartmentInfo info) {
        super.onPostExecute(info);
        if (info!=null){
            tvName.setText("Phòng "+info.name);
            tvBoss.setText(info.boss);
            tvEmail.setText(info.email);
            tvAccount.setText(info.total_account);
            tvMeeting.setText(info.total_meeting);
            ccc.cancel();
        }
    }

    public class DepartmentInfo {
        String name;
        String boss;
        String email;
        String total_account;
        String total_meeting;

        public DepartmentInfo(String name, String boss, String email, String total_account, String total_meeting) {
            this.name = name;
            this.boss = boss;
            this.email = email;
            this.total_account = total_account;
            this.total_meeting = total_meeting;
        }
    }
}
