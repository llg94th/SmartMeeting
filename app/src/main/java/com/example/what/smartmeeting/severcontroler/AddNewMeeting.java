package com.example.what.smartmeeting.severcontroler;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.example.what.smartmeeting.entity.Account;
import com.example.what.smartmeeting.entity.Meetings;
import com.example.what.smartmeeting.service.AccountLogin;
import com.example.what.smartmeeting.service.SendNotifyIntentService;
import com.example.what.smartmeeting.views.DepartmentActivity;
import com.example.what.smartmeeting.views.MeetingDetailActivity;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class AddNewMeeting extends AsyncTask<Meetings, Void, Meetings> {
    private final String ADD_MEETING_URL = "http://hungntph04073.esy.es/add-meeting.php";
    private Context context;
    private SweetAlertDialog ccc;

    public AddNewMeeting(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        ccc = new SweetAlertDialog(context,SweetAlertDialog.PROGRESS_TYPE).setTitleText("Đang thực hiện");
        ccc.show();
    }

    @Override
    protected Meetings doInBackground(Meetings... params) {
        Meetings meeting = params[0];
        String data = ADD_MEETING_URL + "?name=" + meeting.getName() + "&creater=" + meeting.getCreater() + "&description=" + meeting.getDescription() + "&time="
                + meeting.getTimeOnMiliseconds() + "&place=" + meeting.getPlace()+"&department_id="+2;
        String turl = data.replace(" ", "%20").replace("\n","%5Cn");
        String result = "";
        try {
            URL url = new URL(turl);
            InputStream is = url.openStream();
            int byteCharacter;
            while ((byteCharacter = is.read()) != -1) {
                char c = (char) byteCharacter;
                result += c;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        if (result.equals("true")){
            return meeting;
        }else {
            return null;
        }
    }

    @Override
    protected void onPostExecute(final Meetings aBoolean) {
        super.onPostExecute(aBoolean);
        if (aBoolean!=null){
            ccc.setTitleText("Thành công").setConfirmText("OK").setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    sweetAlertDialog.cancel();
                    Intent intent = new Intent(context, SendNotifyIntentService.class);
                    intent.putExtra("creater",aBoolean.getCreater());
                    intent.putExtra("name",aBoolean.getName());
                    intent.putExtra("time", aBoolean.getTimeOnMiliseconds());
                    intent.putExtra("dep_id", AccountLogin.account_logined.getDepartment());
                    context.startService(intent);
                    Intent intent1 = new Intent(context, DepartmentActivity.class);
                    context.startActivity(intent1);
                }
            }).changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
        }else {
            ccc.setTitleText("Có lỗi xảy ra").setConfirmText("OK").setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    sweetAlertDialog.cancel();
                }
            }).changeAlertType(SweetAlertDialog.WARNING_TYPE);
        }
    }
}
