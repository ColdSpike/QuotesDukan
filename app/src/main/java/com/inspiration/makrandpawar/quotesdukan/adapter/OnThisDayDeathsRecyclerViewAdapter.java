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
import com.inspiration.makrandpawar.quotesdukan.model.OnThisDayDeathsResponse;

import java.util.List;


public class OnThisDayDeathsRecyclerViewAdapter extends RecyclerView.Adapter<OnThisDayDeathsRecyclerViewAdapter.OnThisDayDeathsRecyclerViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private List<OnThisDayDeathsResponse.Data.DeathsClass> deaths;

    public OnThisDayDeathsRecyclerViewAdapter(Context context, List<OnThisDayDeathsResponse.Data.DeathsClass> deaths) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.deaths = deaths;
    }

    @Override
    public OnThisDayDeathsRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.onthisdaybirths_singlerow, parent, false);
        OnThisDayDeathsRecyclerViewHolder holder = new OnThisDayDeathsRecyclerViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final OnThisDayDeathsRecyclerViewHolder holder, int position) {
        holder.setDeath(deaths.get(position).text, deaths.get(position).year);
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
        return deaths.size();
    }

    public static class OnThisDayDeathsRecyclerViewHolder extends RecyclerView.ViewHolder {
        private TextView text;
        private TextView year;
        public OnThisDayDeathsRecyclerViewHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.onthisdaybirthssinglerow_text);
            year = (TextView) itemView.findViewById(R.id.onthisdaybirthssinglerow_year);
        }

        public void setDeath(String text, String year) {
            this.text.setText(text);
            this.year.setText("-"+year);
        }
    }
}
