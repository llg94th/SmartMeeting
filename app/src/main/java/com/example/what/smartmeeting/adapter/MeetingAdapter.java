package com.example.what.smartmeeting.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.example.what.smartmeeting.R;
import com.example.what.smartmeeting.entity.Meetings;
import com.example.what.smartmeeting.views.MeetingDetailActivity;

import java.util.ArrayList;


public class MeetingAdapter extends RecyclerView.Adapter<MeetingAdapter.ViewHolder> {
    private ArrayList<Meetings> meetingsArrayList;

    public MeetingAdapter(ArrayList<Meetings> meetingsArrayList) {
        this.meetingsArrayList = meetingsArrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View meetingView = inflater.inflate(R.layout.item_meeting, parent, false);
        return new ViewHolder(meetingView, context);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Meetings meetings = meetingsArrayList.get(position);
        holder.tvTile.setText(meetings.getName());
        holder.tvTime.setText(meetings.getTime() + " phòng: " + meetings.getPlace());
        holder.tvDes.setText(meetings.getDescription());
        String txtDate = meetings.getTime().substring(6, 8);
        ColorGenerator generator = ColorGenerator.MATERIAL;
        int color1 = generator.getRandomColor();
        TextDrawable drawable = TextDrawable.builder()
                .buildRound(txtDate, color1);
        holder.img.setImageDrawable(drawable);
    }

    @Override
    public int getItemCount() {
        if (meetingsArrayList != null) {
            return meetingsArrayList.size();
        } else {
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTile, tvTime, tvDes;
        private Context mContext;
        public ImageView img;

        public ViewHolder(final View itemView, Context context) {
            super(itemView);
            mContext = context;
            tvTile = (TextView) itemView.findViewById(R.id.tvTile);
            tvTime = (TextView) itemView.findViewById(R.id.tvTime);
            tvDes = (TextView) itemView.findViewById(R.id.tvDes);
            img = (ImageView) itemView.findViewById(R.id.img1);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(mContext, MeetingDetailActivity.class);
                    i.setAction(meetingsArrayList.get(getLayoutPosition()).getId()+"");
                    i.putExtra("meeting_id", meetingsArrayList.get(getLayoutPosition()).getId());
                    Activity mActivity = (Activity) mContext;

                    mActivity.startActivity(i);
                    mActivity.overridePendingTransition(
                            R.anim.xout_from,
                            R.anim.xout_to);
                }
            });
        }
    }
}
