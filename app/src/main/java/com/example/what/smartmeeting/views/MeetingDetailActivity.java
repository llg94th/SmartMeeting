package com.example.what.smartmeeting.views;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.what.smartmeeting.R;
import com.example.what.smartmeeting.adapter.CommentAdapter;
import com.example.what.smartmeeting.entity.MeetingDocument;
import com.example.what.smartmeeting.entity.Comments;
import com.example.what.smartmeeting.entity.Meetings;
import com.example.what.smartmeeting.severcontroler.LoadListComment;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;
import jp.wasabeef.recyclerview.animators.LandingAnimator;


public class MeetingDetailActivity extends AppCompatActivity {

    private RecyclerView listView;
    private int rate;
    String macuochop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting_detail);
        setUpToolbar();
        setUpRecyclerView();
        macuochop = getIntent().getAction();
        new LoadListComment(MeetingDetailActivity.this).execute( macuochop);
        Log.d("MY_WATCH", "macuochop: " + macuochop);
        rate = 0;
        init();

    }

    private void init() {
        ImageButton btnAdd = (ImageButton) findViewById(R.id.btnComment);
        ImageButton btnStar = (ImageButton) findViewById(R.id.btnAddRate);
        btnStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LayoutInflater inflater = LayoutInflater.from(MeetingDetailActivity.this);
                View view = inflater.inflate(R.layout.dialog_five_star, null);
                view.findViewById(R.id.btnStar1).setOnClickListener(new StarClick());
                view.findViewById(R.id.btnStar2).setOnClickListener(new StarClick());
                view.findViewById(R.id.btnStar3).setOnClickListener(new StarClick());
                view.findViewById(R.id.btnStar4).setOnClickListener(new StarClick());
                view.findViewById(R.id.btnStar5).setOnClickListener(new StarClick());
                AlertDialog.Builder builder = new AlertDialog.Builder(MeetingDetailActivity.this);
                builder.setView(view).setTitle("Đánh giá").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText textView = (EditText) findViewById(R.id.etComment);
                int id = 0;
                String content = textView.getText().toString();
                String account_id = "hungnt";
                int value = rate;
                int meeting_id = Integer.valueOf(macuochop);
                long time = System.currentTimeMillis();
                if (content.length() != 0) {
                    Comments cmt = new Comments(id, content, account_id, value, meeting_id, time);
                    new addNewComment(cmt).execute(cmt);
                } else {
                    textView.setError("Chưa nhập bình luận");
                }
            }
        });
    }

    private void setUpRecyclerView() {
        listView = (RecyclerView) findViewById(R.id.lvComment);
        listView.setItemAnimator(new LandingAnimator());
        listView.setLayoutManager(new LinearLayoutManager(this));

    }

    private void setUpToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(R.string.meeting_detail);
    }

    private class StarClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            View view = v.getRootView();
            TextView tvRate = (TextView) view.findViewById(R.id.tvMeetingRate);
            ImageButton star1 = (ImageButton) view.findViewById(R.id.btnStar1);
            ImageButton star2 = (ImageButton) view.findViewById(R.id.btnStar2);
            ImageButton star3 = (ImageButton) view.findViewById(R.id.btnStar3);
            ImageButton star4 = (ImageButton) view.findViewById(R.id.btnStar4);
            ImageButton star5 = (ImageButton) view.findViewById(R.id.btnStar5);
            switch (id) {
                case R.id.btnStar1:
                    star1.setImageResource(R.drawable.ic_star_fill);
                    star2.setImageResource(R.drawable.ic_star);
                    star3.setImageResource(R.drawable.ic_star);
                    star4.setImageResource(R.drawable.ic_star);
                    star5.setImageResource(R.drawable.ic_star);
                    tvRate.setText("Rất không hài lòng");
                    rate = 1;
                    break;
                case R.id.btnStar2:
                    star1.setImageResource(R.drawable.ic_star_fill);
                    star2.setImageResource(R.drawable.ic_star_fill);
                    star3.setImageResource(R.drawable.ic_star);
                    star4.setImageResource(R.drawable.ic_star);
                    star5.setImageResource(R.drawable.ic_star);
                    tvRate.setText("Không hài lòng");
                    rate = 2;
                    break;
                case R.id.btnStar3:
                    star1.setImageResource(R.drawable.ic_star_fill);
                    star2.setImageResource(R.drawable.ic_star_fill);
                    star3.setImageResource(R.drawable.ic_star_fill);
                    star4.setImageResource(R.drawable.ic_star);
                    star5.setImageResource(R.drawable.ic_star);
                    tvRate.setText("Bình thường");
                    rate = 3;
                    break;
                case R.id.btnStar4:
                    star1.setImageResource(R.drawable.ic_star_fill);
                    star2.setImageResource(R.drawable.ic_star_fill);
                    star3.setImageResource(R.drawable.ic_star_fill);
                    star4.setImageResource(R.drawable.ic_star_fill);
                    star5.setImageResource(R.drawable.ic_star);
                    tvRate.setText("Hài lòng");
                    rate = 4;
                    break;
                case R.id.btnStar5:
                    star1.setImageResource(R.drawable.ic_star_fill);
                    star2.setImageResource(R.drawable.ic_star_fill);
                    star3.setImageResource(R.drawable.ic_star_fill);
                    star4.setImageResource(R.drawable.ic_star_fill);
                    star5.setImageResource(R.drawable.ic_star_fill);
                    tvRate.setText("Rất hài lòng");
                    rate = 5;
                    break;
            }
        }
    }

    private class addNewComment extends AsyncTask<Comments, Void, Integer> {
        SweetAlertDialog ccc;
        Comments com;

        public addNewComment(Comments com) {
            this.com = com;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ccc = new SweetAlertDialog(MeetingDetailActivity.this, SweetAlertDialog.PROGRESS_TYPE).setTitleText("Sending...");
            ccc.show();
        }

        @Override
        protected Integer doInBackground(Comments... params) {
            Comments cmt = params[0];
            String txtUrl = "http://hungntph04073.esy.es/add-comment.php?content=" + cmt.getContent() + "&account_id=" + cmt.getAccount_id() + "&value=" + cmt.getValue() + "&meeting_id=" + cmt.getMeeting_id() + "&time=" + System.currentTimeMillis();
            String txtUrlEncoded = txtUrl.replace(" ", "%20").replace("\n","%5Cn");
            try {
                String result = "";
                URL url = new URL(txtUrlEncoded);
                InputStream is = url.openStream();
                int byteCharacter;
                while ((byteCharacter = is.read()) != -1) {
                    char c = (char) byteCharacter;
                    result += c;
                }
                if (result.equals("true")) {
                    return 0;
                } else {
                    return 1;
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return 2;
            } catch (IOException e) {
                e.printStackTrace();
                return 3;
            }

        }

        @Override
        protected void onPostExecute(Integer value) {
            super.onPostExecute(value);
            switch (value) {
                case 0:
                    addCommentToList(com);
                    ccc.cancel();
                    break;
                case 1:
                    ccc.changeAlertType(SweetAlertDialog.WARNING_TYPE);
                    ccc.setTitleText("Có lỗi ở sever");
                    break;
                case 2:
                    ccc.changeAlertType(SweetAlertDialog.WARNING_TYPE);
                    ccc.setTitleText("Có lỗi MalformedURLException");
                    break;
                case 3:
                    ccc.changeAlertType(SweetAlertDialog.WARNING_TYPE);
                    ccc.setTitleText("Có lỗi IOException");
                    break;
            }

        }
    }

    private void addCommentToList(Comments comment) {
        EditText textView = (EditText) findViewById(R.id.etComment);
        CommentAdapter adapter = (CommentAdapter) listView.getAdapter();
        if (adapter.getListComment().size() > 0) {
            adapter.getListComment().add(comment);
            textView.setText("");
            adapter.notifyItemInserted(adapter.getListComment().size() - 1);
            listView.smoothScrollToPosition(adapter.getListComment().size() - 1);
        } else {
            Meetings meetings = adapter.meeting;
            MeetingDocument document = adapter.document;
            ArrayList<Comments> list = adapter.getListComment();
            list.add(comment);
            CommentAdapter newAdapter = new CommentAdapter(list, meetings, document);
            listView.setAdapter(newAdapter);
        }
        rate = 0;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(
                R.anim.xin_to,
                R.anim.xin_from);
    }
}
