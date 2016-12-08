package com.example.what.smartmeeting.severcontroler;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.what.smartmeeting.R;
import com.example.what.smartmeeting.adapter.MeetingAdapter;
import com.example.what.smartmeeting.entity.Meetings;

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
 * Created by ngoc on 4/5/2016.
 */
public class LoadListHistoryMeetingFromSever extends AsyncTask<String, Meetings, ArrayList<Meetings>> {

    Fragment mfragment;
    SweetAlertDialog ccc;
    RecyclerView recyclerView;
    MeetingAdapter adt;
    ArrayList<Meetings> list;

    public LoadListHistoryMeetingFromSever(Fragment mfragment) {
        this.mfragment = mfragment;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        recyclerView = (RecyclerView) mfragment.getActivity().findViewById(R.id.rvHistory);
        list = new ArrayList<>();
        adt = new MeetingAdapter(list);
        recyclerView.setAdapter(adt);
        ccc = new SweetAlertDialog(mfragment.getContext(), SweetAlertDialog.PROGRESS_TYPE).setTitleText("Loading...");
        ccc.setCancelable(false);
        ccc.show();
    }

    @Override
    protected ArrayList<Meetings> doInBackground(String... params) {
        ArrayList<Meetings> mList = new ArrayList<>();
        String jsonText = "";
        try {

            URL url = new URL(params[0]);
            URLConnection con = url.openConnection();
            InputStream is = con.getInputStream();
            int byteCharacter;
            while ((byteCharacter = is.read()) != -1) {
                char c = (char) byteCharacter;
                jsonText += c;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            JSONArray listJson = new JSONArray(jsonText);
            for (int i=0;i<listJson.length();i++){
                JSONObject object = listJson.getJSONObject(i);
                int id = object.getInt("id");
                String name = object.getString("name");
                String creater = object.getString("creater");
                String description = object.getString("description");
                String place = object.getString("place");
                long time = object.getLong("time");
                Meetings meetings = new Meetings(id, name, creater, description, time, place);
                if (time<=System.currentTimeMillis()){
                    list.add(meetings);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mList;
    }


    @Override
    protected void onPostExecute(ArrayList<Meetings> meetingses) {
        super.onPostExecute(meetingses);
        ccc.cancel();
        list.addAll(meetingses);
        adt.notifyItemRangeInserted(0,list.size()-1);
    }
}
