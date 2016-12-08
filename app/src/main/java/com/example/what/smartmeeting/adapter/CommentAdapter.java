package com.example.what.smartmeeting.adapter;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.what.smartmeeting.R;
import com.example.what.smartmeeting.entity.Comments;
import com.example.what.smartmeeting.entity.MeetingDocument;
import com.example.what.smartmeeting.entity.Meetings;
import com.example.what.smartmeeting.views.LoginActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

    public ArrayList<Comments> commentsArrayList;
    public Meetings meeting;
    public MeetingDocument document;

    public CommentAdapter(ArrayList<Comments> commentsArrayList, Meetings cuochop, MeetingDocument doc) {
        this.commentsArrayList = commentsArrayList;
        this.meeting = cuochop;
        this.document = doc;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        final Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        CommentAdapter.ViewHolder viewHolder;

        if (position == 0) {
            View convertView = inflater.inflate(R.layout.first_item_comment, null);
            convertView.findViewById(R.id.btnDownloadDocument).setOnClickListener(new DocumentOnClick(context));
            convertView.findViewById(R.id.btnEditDocument).setOnClickListener(new DocumentOnClick(context));
            convertView.findViewById(R.id.btnViewDocument).setOnClickListener(new DocumentOnClick(context));
            viewHolder = new ViewHolder(convertView);
            if (commentsArrayList != null & commentsArrayList.size() > 0){
                Comments comment = commentsArrayList.get(position);
                new LoadAva(viewHolder.itemView).execute(comment.getAccount_id());
                new LoadName(viewHolder.itemView).execute(comment.getAccount_id());
            }
        } else {
            View convertView = inflater.inflate(R.layout.item_comment, null);
            viewHolder = new ViewHolder(convertView);
            Comments comment = commentsArrayList.get(position);
            new LoadAva(viewHolder.itemView).execute(comment.getAccount_id());
            new LoadName(viewHolder.itemView).execute(comment.getAccount_id());
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (commentsArrayList != null & commentsArrayList.size() > 0) {
            Comments comment = commentsArrayList.get(position);
            if (position == 0) {
                holder.tvMeetingTime.setText(meeting.getTime().substring(0, 5));
                holder.tvMeetingDate.setText(meeting.getTime().substring(6));
                holder.tvMeetingPlace.setText(meeting.getPlace());
                holder.tvMeetingRate.setText(getMeetingRate(commentsArrayList));
                new LoadCreaterName(holder.itemView).execute(meeting.getCreater());
                holder.tvMeetingDes.setText(meeting.getDescription());
                if (document != null) {
                    holder.tvDocumentTitle.setText(document.getName());
                } else {
                    holder.tvDocumentTitle.setText("Chưa có tài liệu nào");
                }
            }
            holder.tvTime.setText(comment.getTime());
            holder.tvContent.setText(comment.getContent());

        } else {
            if (position == 0) {
                holder.tvMeetingTime.setText(meeting.getTime().substring(0, 5));
                holder.tvMeetingDate.setText(meeting.getTime().substring(6));
                holder.tvMeetingPlace.setText(meeting.getPlace());
                holder.tvMeetingRate.setText(getMeetingRate(commentsArrayList));
                new LoadCreaterName(holder.itemView).execute(meeting.getCreater());
                holder.tvMeetingDes.setText(meeting.getDescription());
                if (document != null) {
                    holder.tvDocumentTitle.setText(document.getName());
                } else {
                    holder.tvDocumentTitle.setText("Chưa có tài liệu nào");
                }
            }
            holder.tvName.setText("Chưa có bình luận");
            holder.imgAvatar.setVisibility(View.GONE);
            holder.tvTime.setVisibility(View.GONE);
            holder.tvContent.setVisibility(View.GONE);

        }


    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        if (commentsArrayList != null) {
            if (commentsArrayList.size() == 0) {
                return 1;
            } else {
                return commentsArrayList.size();
            }
        } else {
            return 1;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView imgAvatar;
        TextView tvContent;
        TextView tvTime;
        TextView tvName;
        TextView tvMeetingTime, tvMeetingDate, tvMeetingPlace, tvMeetingRate, tvMeetingCreater, tvMeetingDes, tvDocumentTitle;
        ImageButton btnDownloadDocument, btnEditDocument;

        public ViewHolder(View itemView) {
            super(itemView);
            imgAvatar = (CircleImageView) itemView.findViewById(R.id.imgAvatar);
            tvContent = (TextView) itemView.findViewById(R.id.tvContent);
            tvTime = (TextView) itemView.findViewById(R.id.tvTime);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvMeetingTime = (TextView) itemView.findViewById(R.id.tvMeetingTime);
            tvMeetingDate = (TextView) itemView.findViewById(R.id.tvMeetingDate);
            tvMeetingPlace = (TextView) itemView.findViewById(R.id.tvMeetingPlace);
            tvMeetingRate = (TextView) itemView.findViewById(R.id.tvMeetingRate);
            tvMeetingCreater = (TextView) itemView.findViewById(R.id.tvMeetingCreater);
            tvMeetingDes = (TextView) itemView.findViewById(R.id.tvMeetingDes);
            tvDocumentTitle = (TextView) itemView.findViewById(R.id.tvDocumentTitle);
            btnDownloadDocument = (ImageButton) itemView.findViewById(R.id.btnDownloadDocument);
            btnEditDocument = (ImageButton) itemView.findViewById(R.id.btnEditDocument);
        }
    }

    private class LoadCreaterName extends AsyncTask<String, Void, String> {

        View view;
        TextView tvCreater;

        public LoadCreaterName(View view) {
            this.view = view;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            tvCreater = (TextView) this.view.findViewById(R.id.tvMeetingCreater);
        }

        @Override
        protected String doInBackground(String... params) {
            String info_url = "http://hungntph04073.esy.es/get-account-info-by-id.php?id=" + params[0];
            String jsonText = "";
            try {
                URL url = new URL(info_url);
                URLConnection con = url.openConnection();
                InputStream is = con.getInputStream();
                int byteCharacter2;
                while ((byteCharacter2 = is.read()) != -1) {
                    char c = (char) byteCharacter2;
                    jsonText += c;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            String name = "";
            try {
                JSONObject jsonObject = new JSONObject(jsonText);
                name = jsonObject.getString("name");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return name;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (tvCreater != null) {
                tvCreater.setText(s);
            }

        }
    }

    private class LoadName extends AsyncTask<String, Void, String> {

        View view;
        TextView tvname;

        public LoadName(View view) {
            this.view = view;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            tvname = (TextView) this.view.findViewById(R.id.tvName);
        }

        @Override
        protected String doInBackground(String... params) {
            String info_url = "http://hungntph04073.esy.es/get-account-info-by-id.php?id=" + params[0];
            String jsonText = "";
            try {
                URL url = new URL(info_url);
                URLConnection con = url.openConnection();
                InputStream is = con.getInputStream();
                int byteCharacter2;
                while ((byteCharacter2 = is.read()) != -1) {
                    char c = (char) byteCharacter2;
                    jsonText += c;
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            String name = "";
            try {
                JSONObject jsonObject = new JSONObject(jsonText);
                name = jsonObject.getString("name");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return name;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (tvname != null) {
                tvname.setText(s);
            }
        }
    }

    private class LoadAva extends AsyncTask<String, Void, Bitmap> {

        View view;
        CircleImageView imgAva;
        ProgressBar pbLoading;

        public LoadAva(View v) {
            this.view = v;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            imgAva = (CircleImageView) view.findViewById(R.id.imgAvatar);imgAva.setVisibility(View.GONE);
            pbLoading = (ProgressBar) view.findViewById(R.id.pbLoading);pbLoading.setVisibility(View.VISIBLE);
        }

        @Override
        protected Bitmap doInBackground(String... params) {

            String acc_info_url = "http://hungntph04073.esy.es/get-account-info-by-id.php?id=" + params[0];
            String jsonText = "";
            Bitmap bm = null;
            try {
                URL url = new URL(acc_info_url);
                URLConnection connection = url.openConnection();
                InputStream is = connection.getInputStream();
                int byteCharacter;
                while ((byteCharacter = is.read()) != -1) {
                    char c = (char) byteCharacter;
                    jsonText += c;
                }
                JSONObject jsonAccount = new JSONObject(jsonText);
                String photo_url = jsonAccount.getString("photo_url");
                URL ava_url = new URL(photo_url);
                InputStream ava_is = ava_url.openStream();
                bm = BitmapFactory.decodeStream(ava_is);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return bm;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            imgAva.setImageBitmap(bitmap);
            pbLoading.setVisibility(View.GONE);
            imgAva.setVisibility(View.VISIBLE);

        }
    }

    private String getMeetingRate(ArrayList<Comments> list) {
        int n = 0;
        int tong = 0;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getValue() != 0) {
                tong = tong + list.get(i).getValue();
                n++;
            }
        }
        String txtRate;
        if (n != 0) {
            int rate = tong / n;
            switch (rate) {
                case 1:
                    txtRate = "Tồi tệ";
                    break;
                case 2:
                    txtRate = "Trung bình";
                    break;
                case 3:
                    txtRate = "Bình thường";
                    break;
                case 4:
                    txtRate = "Tôt";
                    break;
                case 5:
                    txtRate = "Rất tốt";
                    break;
                default:
                    txtRate = "Chưa đánh giá";
                    break;
            }
        } else {
            txtRate = "Chưa đánh giá";
        }
        return txtRate;
    }

    public ArrayList<Comments> getListComment() {
        return commentsArrayList;
    }

    private class DocumentOnClick implements View.OnClickListener {

        Context mContext;

        public DocumentOnClick(Context mContext) {
            this.mContext = mContext;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnDownloadDocument:
                    if (document != null) {
                        try {
                            Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(document.getDoc_url()));
                            mContext.startActivity(myIntent);
                        } catch (ActivityNotFoundException e) {
                            e.printStackTrace();
                        }
                        break;
                    }else {
                        new SweetAlertDialog(mContext, SweetAlertDialog.WARNING_TYPE).setTitleText("Lỗi").setContentText("Đã bảo không có tài liệu mà").setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.cancel();
                            }
                        }).show();
                        break;
                    }

                case R.id.btnEditDocument:
                    new SweetAlertDialog(mContext, SweetAlertDialog.WARNING_TYPE).setTitleText("Lỗi").setContentText("Bạn chưa được cấp quyền sửa").setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.cancel();
                        }
                    }).show();
                    break;
                case R.id.btnViewDocument:
                    new SweetAlertDialog(mContext, SweetAlertDialog.WARNING_TYPE).setTitleText("Lỗi").setContentText("Chức năng đang được xây dựng..").setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.cancel();
                        }
                    }).show();
                    break;
                default:
                    break;
            }
        }
    }
}
