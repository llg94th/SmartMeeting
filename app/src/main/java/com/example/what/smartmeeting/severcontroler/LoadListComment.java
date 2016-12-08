package com.example.what.smartmeeting.severcontroler;

import android.app.Activity;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.what.smartmeeting.R;
import com.example.what.smartmeeting.adapter.CommentAdapter;
import com.example.what.smartmeeting.entity.Comments;
import com.example.what.smartmeeting.entity.MeetingDocument;
import com.example.what.smartmeeting.entity.Meetings;
import com.example.what.smartmeeting.views.MeetingDetailActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by ngoc on 4/7/2016.
 */
public class LoadListComment extends AsyncTask<String, Void, CommentAdapter> {

    Activity activity;
    Meetings meeting;
    MeetingDocument document;
    RecyclerView recyclerView;
    SweetAlertDialog ccc;
    ArrayList<Comments> listComment;

    public LoadListComment(Activity activity) {
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        recyclerView = (RecyclerView) activity.findViewById(R.id.lvComment);
        meeting = null;
        document = null;
        listComment = new ArrayList<>();
        ccc = new SweetAlertDialog(activity, SweetAlertDialog.PROGRESS_TYPE).setTitleText("Loading...");
        ccc.setCancelable(false);
        ccc.show();
    }

    @Override
    protected CommentAdapter doInBackground(String... params) {
        String getMeetingInfo_URL = "http://hungntph04073.esy.es/get-meeting-info.php?id=" + params[0];
        String getlistCmt_URL = "http://hungntph04073.esy.es/get-list-comment-by-meeting-id.php?id=" + params[0];
        String getDocument_URL = "http://hungntph04073.esy.es/get-document-by-meeting-id.php?id=" + params[0];

        String jsonText1 = "";
        String jsonText2 = "";
        String jsonText3 = "";
        try {
            URL url1 = new URL(getMeetingInfo_URL);
            URLConnection con1 = url1.openConnection();
            InputStream is1 = con1.getInputStream();
            int byteCharacter1;
            while ((byteCharacter1 = is1.read()) != -1) {
                char c = (char) byteCharacter1;
                jsonText1 += c;
            }

            URL url2 = new URL(getlistCmt_URL);
            URLConnection con2 = url2.openConnection();
            InputStream is2 = con2.getInputStream();
            int byteCharacter2;
            while ((byteCharacter2 = is2.read()) != -1) {
                char c = (char) byteCharacter2;
                jsonText2 += c;
            }
            URL url3 = new URL(getDocument_URL);
            URLConnection con3 = url3.openConnection();
            InputStream is3 = con3.getInputStream();
            int byteCharacter3;
            while ((byteCharacter3 = is3.read()) != -1) {
                char c = (char) byteCharacter3;
                jsonText3 += c;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            JSONObject jsonMeeting = new JSONObject(jsonText1);
            meeting = new Meetings(jsonMeeting.getInt("id"), jsonMeeting.getString("name"), jsonMeeting.getString("creater"), jsonMeeting.getString("description"), jsonMeeting.getLong("time"), jsonMeeting.getString("place"));


        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            JSONArray listJsonCommnet = new JSONArray(jsonText2);
            for (int i = 0; i < listJsonCommnet.length(); i++) {
                JSONObject jsonComment = listJsonCommnet.getJSONObject(i);
                listComment.add(new Comments(jsonComment.getInt("id"), jsonComment.getString("content"), jsonComment.getString("account_id"), jsonComment.getInt("value"), jsonComment.getInt("meeting_id"), jsonComment.getLong("time")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            JSONObject jsonDocument = new JSONObject(jsonText3);
            document = new MeetingDocument(jsonDocument.getInt("id"), jsonDocument.getString("name"), jsonDocument.getString("url"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        CommentAdapter adt = new CommentAdapter(listComment, meeting, document);
        return adt;
    }

    @Override
    protected void onPostExecute(CommentAdapter adt) {
        super.onPostExecute(adt);
        if (adt.meeting!=null){
            recyclerView.setAdapter(adt);
            ccc.cancel();
        }else {
            ccc.setTitleText("Có lỗi xảy ra!").changeAlertType(SweetAlertDialog.WARNING_TYPE);
        }
    }
}
