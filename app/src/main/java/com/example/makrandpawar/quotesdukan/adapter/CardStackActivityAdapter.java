package com.example.makrandpawar.quotesdukan.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.makrandpawar.quotesdukan.R;
import com.example.makrandpawar.quotesdukan.model.QuotesListResponse;

import java.util.Random;

public class CardStackActivityAdapter extends ArrayAdapter<QuotesListResponse.Quote>{
    private final Context contex;

    public CardStackActivityAdapter(@NonNull Context context, @LayoutRes int resource) {
        super(context, resource);
        this.contex=context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TextView body = (TextView) convertView.findViewById(R.id.cardstackactivitysinglecard_body);
        TextView author = (TextView) convertView.findViewById(R.id.cardstackactivitysinglecard_author);
        body.setText(getItem(position).body);
        author.setText(getItem(position).author);

        int[] androidColors = contex.getResources().getIntArray(R.array.androidcolors);
        int randomAndroidColor = androidColors[new Random().nextInt(androidColors.length)];
        RelativeLayout relativeLayout = (RelativeLayout) convertView.findViewById(R.id.cardstackactivitysinglecard_rr);
        relativeLayout.setBackgroundColor(randomAndroidColor);

        return convertView;
    }
}
