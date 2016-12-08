package com.example.what.smartmeeting.views;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.what.smartmeeting.R;
import com.example.what.smartmeeting.entity.Meetings;
import com.example.what.smartmeeting.service.AccountLogin;
import com.example.what.smartmeeting.severcontroler.AddNewMeeting;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;

import cn.pedant.SweetAlert.SweetAlertDialog;

//import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by what on 29/03/2016.
 */
public class CreateMeetingActivity extends AppCompatActivity {


    private Button btnNext;
    private Button btnPrevious;
    private Toolbar toolbar;
    private ViewFlipper viewFlipper;
    private DatePicker datePicker;
    private TimePicker timePicker;
    private Calendar cal;
    private EditText etName;
    private EditText etDes;
    private EditText etPlace;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_meeting_activity);
        innt();
        btnNext.setOnClickListener(new BtbNextListenner());
        btnPrevious.setOnClickListener(new BtnPreListener());
        etPlace.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE&&validateContent()) {
                    viewFlipper.showNext();
                    checkPosition();
                    View view = CreateMeetingActivity.this.getCurrentFocus();
                    if (view != null) {
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                }
                return false;
            }
        });
    }

    private void showDialog(final Meetings meeting) {
        String content = meeting.getTime() + " phòng: " + meeting.getPlace() + "\nNội dung:" + meeting.getDescription();
        new SweetAlertDialog(this, SweetAlertDialog.NORMAL_TYPE)
                .setTitleText(meeting.getName())
                .setContentText(content)
                .setConfirmText("OK").setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.cancel();
                addMeeting(meeting);
            }
        })
                .setCancelText("Hủy").setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.cancel();
            }
        })
                .show();
    }


    private void checkPosition() {
        if (viewFlipper.getDisplayedChild() == 0) {
            btnPrevious.setVisibility(View.GONE);
            btnNext.setText("Tiếp");
            getSupportActionBar().setTitle("Tạo cuộc họp");
            return;
        }
        if (viewFlipper.getDisplayedChild() == 1) {
            btnPrevious.setVisibility(View.VISIBLE);
            btnNext.setVisibility(View.VISIBLE);
            btnNext.setText("Tiếp");
            getSupportActionBar().setTitle("Chọn ngày");
            return;
        }
        if (viewFlipper.getDisplayedChild() == 2) {
            btnNext.setText("Hoàn tất");
            getSupportActionBar().setTitle("Chọn giờ");
        }
    }

    private void innt() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        btnNext = (Button) findViewById(R.id.btnNext);
        btnPrevious = (Button) findViewById(R.id.btnPrevious);
        viewFlipper = (ViewFlipper) findViewById(R.id.viewFliper);
        datePicker = (DatePicker) findViewById(R.id.datePicker);
        timePicker = (TimePicker) findViewById(R.id.timePicker);
        etName = (EditText) findViewById(R.id.etNameMeeting);
        etDes = (EditText) findViewById(R.id.etDescription);
        etPlace = (EditText) findViewById(R.id.etPlace);
        cal = Calendar.getInstance();
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        checkPosition();
    }

    public long getTimeSetted() {
        cal.set(Calendar.YEAR, datePicker.getYear());
        cal.set(Calendar.MONTH, datePicker.getMonth());
        cal.set(Calendar.DAY_OF_MONTH, datePicker.getDayOfMonth());
        cal.set(Calendar.HOUR, timePicker.getCurrentHour());
        cal.set(Calendar.MINUTE, timePicker.getCurrentMinute());
        return cal.getTimeInMillis();
    }

    private class BtbNextListenner implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if (viewFlipper.getDisplayedChild() == 2) {
                Meetings meeting = new Meetings(0, etName.getText().toString(), AccountLogin.account_logined.getId(), etDes.getText().toString(), getTimeSetted(), etPlace.getText().toString());
                addMeeting(meeting);
            } else if(validateContent()){
                viewFlipper.showNext();
                checkPosition();
            }
        }
    }

    private class BtnPreListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            viewFlipper.showPrevious();
            checkPosition();
        }
    }

    private boolean validateContent() {
        if (etName.getText().toString().length() < 10) {
            etName.setError("Tên cuộc họp lớn hơn 10 kí tự");
            return false;
        } else if (etDes.getText().toString().length() < 20) {
            etDes.setError("Nội dung cuộc họp lớn hơn 20 kí tự");
            return false;
        } else if (etPlace.getText().toString().length() < 3) {
            etPlace.setError("Địa điểm lớn hơn 2 kí tự");
            return false;
        } else {
            return true;
        }
    }
    private void addMeeting(Meetings meeting){
        new AddNewMeeting(CreateMeetingActivity.this).execute(meeting);
    }
}