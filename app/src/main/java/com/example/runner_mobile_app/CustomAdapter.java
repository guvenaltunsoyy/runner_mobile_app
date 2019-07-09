package com.example.runner_mobile_app;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CustomAdapter extends BaseAdapter {
    private LayoutInflater userInflater;
    private List<Runner> userList;

    public CustomAdapter(Activity activity, List<Runner> userList) {
        userInflater = (LayoutInflater) activity.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        this.userList = userList;
    }

    @Override
    public int getCount() {
        return userList.size();
    }

    @Override
    public Object getItem(int i) {
        return userList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View lineView;
        lineView = userInflater.inflate(R.layout.custom_layout, null);
        TextView textViewUserName = (TextView) lineView.findViewById(R.id.textViewUserName);
        TextView textViewRunCount = (TextView) lineView.findViewById(R.id.textViewSystemClock);
        TextView textViewTitle=lineView.findViewById(R.id.textViewTitle);
        ImageView imageViewUserPicture = (ImageView) lineView.findViewById(R.id.imageViewUserPicture);

        Runner user = userList.get(i);
        textViewUserName.setText(user.getUsername());
        textViewRunCount.setText(String.valueOf(user.getRuncount()));
        textViewTitle.setText(user.getTitle());
        byte[] decodedString = Base64.decode(user.getImage(), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        imageViewUserPicture.setImageBitmap(decodedByte);
        ///imageViewUserPicture.setImageResource(R.drawable.women_profile);
        return lineView;
    }
}

