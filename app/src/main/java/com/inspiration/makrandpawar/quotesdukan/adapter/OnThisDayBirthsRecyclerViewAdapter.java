package com.inspiration.makrandpawar.quotesdukan.adapter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.inspiration.makrandpawar.quotesdukan.R;
import com.inspiration.makrandpawar.quotesdukan.model.OnThisDayBirthsResponse;
import com.inspiration.makrandpawar.quotesdukan.model.OnThisDayDeathsResponse;

import java.util.List;


public class OnThisDayBirthsRecyclerViewAdapter extends RecyclerView.Adapter<OnThisDayBirthsRecyclerViewAdapter.OnThisDayBirthsRecyclerViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private List<OnThisDayBirthsResponse.Data.BirthsClass> births;

    public OnThisDayBirthsRecyclerViewAdapter(Context context, List<OnThisDayBirthsResponse.Data.BirthsClass> births) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.births = births;
    }

    @Override
    public OnThisDayBirthsRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.onthisdaybirths_singlerow, parent, false);
        OnThisDayBirthsRecyclerViewHolder holder = new OnThisDayBirthsRecyclerViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final OnThisDayBirthsRecyclerViewHolder holder, int position) {
        holder.setBirth(births.get(position).text,births.get(position).year);
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) context.getSystemService(context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("simple text", holder.text.getText().toString()+" :- "+holder.year.getText().toString());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(context, "Copied to clipboard", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return births.size();
    }

    public static class OnThisDayBirthsRecyclerViewHolder extends RecyclerView.ViewHolder {
        private TextView text;
        private TextView year;
        public OnThisDayBirthsRecyclerViewHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.onthisdaybirthssinglerow_text);
            year = (TextView) itemView.findViewById(R.id.onthisdaybirthssinglerow_year);
        }

        public void setBirth(String text, String year) {
            this.text.setText(text);
            this.year.setText("-"+year);
        }
    }
}
