package com.example.what.smartmeeting.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.what.smartmeeting.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import com.example.what.smartmeeting.entity.Account;

/**
 * Created by what on 30/03/2016.
 */
public class AvatarAdapter extends RecyclerView.Adapter<AvatarAdapter.MyviewHolder> {
    private Clicklistener clicklistener;
    private ArrayList<Account> list;
    public AvatarAdapter(ArrayList<Account> list) {
        this.list = list;
    }

    @Override
    public MyviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_avatar_member, parent, false);
        return new MyviewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyviewHolder holder, int position) {
        Account account = list.get(position);
        holder.img.setImageBitmap(account.loadImage());
    }

    public void setclicklistener(Clicklistener clicklistener){
        this.clicklistener =clicklistener;
    }

    public class MyviewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CircleImageView img;
        TextView tvDepartment;
        TextView tvEmail;
        TextView tvName;
        TextView tvPosition;
        CircleImageView imgProfile;

        public MyviewHolder(View itemView) {
            super(itemView);
            img = (CircleImageView) itemView.findViewById(R.id.imgAvatar);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clicklistener.Itemclicked(v,getPosition());
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public interface Clicklistener{
        public void Itemclicked(View view,int position);
    }
}