package com.example.what.smartmeeting.views;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.what.smartmeeting.R;
import com.example.what.smartmeeting.preference.LoginPreference;
import com.example.what.smartmeeting.service.AccountLogin;
import com.example.what.smartmeeting.service.JsonImage;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by what on 28/03/2016.
 */
public class VerifyInfoActivity extends AppCompatActivity {

    public final static String URL_EDIT_INFO = "http://hungntph04073.esy.es/change_info_account.php";
    private Button btnNext;
    private Button btnPrevious;
    private Button btnFinish;
    private Button btnTakeGadenlly;
    private Button btnTakeNew;
    private Toolbar toolbar;
    private ViewFlipper viewFlipper;
    private EditText etName;
    private EditText etEmail;
    private ProgressDialog progressDialog;
    private LoginPreference preference;
    private String imgFile;
    private CircleImageView imgAvatar;
    private InputStream inputStream;
    private TextView tvName1;
    private TextView tvEmail1;
    private TextView tvImage1;
    private int suscess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verifyinfo);
        innt();
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
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewFlipper.getDisplayedChild() == 2) {
                    Toast.makeText(getBaseContext(), "Hoan Tat dang nhap", Toast.LENGTH_SHORT).show();
                    return;
                }
                viewFlipper.showNext();
                checkPosition();
            }
        });
        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewFlipper.showPrevious();
                checkPosition();
            }
        });
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Uploadimage().execute();

            }
        });
        btnTakeNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePicture.resolveActivity(getPackageManager()) != null) {
                    // Create the File where the photo should go
                    startActivityForResult(takePicture, 0);

                }

            }
        });
        btnTakeGadenlly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto, 1);
            }
        });

    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        return image;
    }

    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 0:
                if (resultCode == RESULT_OK) {
                    Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
                    File destination = new File(Environment.getExternalStorageDirectory(),
                            System.currentTimeMillis() + ".jpg");
                    FileOutputStream fo;
                    try {
                        destination.createNewFile();
                        fo = new FileOutputStream(destination);
                        fo.write(bytes.toByteArray());
                        fo.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    imgFile = destination.getPath();
                    imgAvatar.setImageBitmap(thumbnail);
                }
                break;
            case 1:
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    imgAvatar.setImageURI(uri);
                    imgFile = getPath(uri);
                }
                break;
        }
    }

    public class Uploadimage extends AsyncTask<Void, Void, Void> {
        String id;
        String email;
        String name;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
            id = preference.getLogined().getId();
            name = etName.getText().toString();
            email = etEmail.getText().toString();
        }

        @Override
        protected Void doInBackground(Void... params) {
            JsonImage json = new JsonImage();
            JSONObject jsonObject = null;
            InputStream inputStream = json.makeHttpRequest(URL_EDIT_INFO, imgFile, name, id, email);
            int jsonchar;
            String result = null;
            try {

                while ((jsonchar = inputStream.read()) != -1) {
                    result += (char) jsonchar;
                }
                jsonObject = new JSONObject(result);
                suscess = jsonObject.getInt("suscess");
                if (suscess == 0) {
                    AccountLogin.account_logined.setPhoto_profile_url(jsonObject.getString("url_upload"));
                }
//                client.getConnectionManager().shutdown();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();
            startActivity(new Intent(VerifyInfoActivity.this,DepartmentActivity.class));
        }
    }

    private void checkPosition() {
        if (viewFlipper.getDisplayedChild() == 0) {
            btnPrevious.setVisibility(View.GONE);
            getSupportActionBar().setTitle("Tên");
            return;
        }
        if (viewFlipper.getDisplayedChild() == 1) {
            btnPrevious.setVisibility(View.VISIBLE);
            btnNext.setVisibility(View.VISIBLE);
            btnFinish.setVisibility(View.GONE);
            btnNext.setText("Next");
            getSupportActionBar().setTitle("Email");
            return;
        }
        if (viewFlipper.getDisplayedChild() == 2) {
            btnNext.setVisibility(View.GONE);
            btnFinish.setVisibility(View.VISIBLE);
            getSupportActionBar().setTitle("Ảnh đai diện");
        }
    }

    private void innt() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        btnNext = (Button) findViewById(R.id.btnNext);
        imgAvatar = (CircleImageView) findViewById(R.id.imgAvatar);

        btnFinish = (Button) findViewById(R.id.btnFinish);
        btnPrevious = (Button) findViewById(R.id.btnPrevious);
        btnTakeGadenlly = (Button) findViewById(R.id.btnTakeGadenlly);
        btnTakeNew = (Button) findViewById(R.id.btnTakenew);
        viewFlipper = (ViewFlipper) findViewById(R.id.viewFliper);
        etEmail = (EditText) findViewById(R.id.etMail);
        etName = (EditText) findViewById(R.id.etName);
        progressDialog = new ProgressDialog(VerifyInfoActivity.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Đang tải");
        progressDialog.setCancelable(false);
        preference = new LoginPreference(getBaseContext());
        tvName1 = (TextView) findViewById(R.id.tvName1);
        tvImage1 = (TextView) findViewById(R.id.tvImage1);
        tvEmail1 = (TextView) findViewById(R.id.tvEmail1);
        tvEmail1.setTextColor(Color.parseColor("#00695C"));
        tvName1.setTextColor(Color.parseColor("#00695C"));
        tvImage1.setTextColor(Color.parseColor("#00695C"));


        imgAvatar.setImageBitmap(AccountLogin.account_logined.getBitmap());
        etEmail.setText(AccountLogin.account_logined.getEmail());
        etName.setText(AccountLogin.account_logined.getName());
    }
}
