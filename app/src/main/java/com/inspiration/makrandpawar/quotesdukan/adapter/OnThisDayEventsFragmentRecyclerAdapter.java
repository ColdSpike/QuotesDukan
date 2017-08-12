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
import com.inspiration.makrandpawar.quotesdukan.model.OnThisDayEventsResponse;

import java.util.List;


public class OnThisDayEventsFragmentRecyclerAdapter extends RecyclerView.Adapter<OnThisDayEventsFragmentRecyclerAdapter.OnThisDayEventsFragmentViewHolder> {

    private LayoutInflater inflater;
    private Context context;
    private List<OnThisDayEventsResponse.Data.EventsClass> events;

    public OnThisDayEventsFragmentRecyclerAdapter(Context context, List<OnThisDayEventsResponse.Data.EventsClass> events) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.events = events;
    }

    @Override
    public OnThisDayEventsFragmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.onthisdayevents_singlerow, parent, false);
        OnThisDayEventsFragmentViewHolder viewHolder = new OnThisDayEventsFragmentViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final OnThisDayEventsFragmentViewHolder holder, int position) {
        holder.setEvent( events.get(position).text,events.get(position).year);
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
        return events.size();
    }

    public static class OnThisDayEventsFragmentViewHolder extends RecyclerView.ViewHolder {
        TextView text;
        TextView year;
        public OnThisDayEventsFragmentViewHolder(View itemView) {
            super(itemView);
            year = (TextView) itemView.findViewById(R.id.onthisdayeventssinglerow_year);
            text = (TextView) itemView.findViewById(R.id.onthisdayeventssinglerow_text);
        }

        public void setEvent(String text, String year) {
            this.text.setText(text);
            this.year.setText("-"+year);
        }
    }
}
