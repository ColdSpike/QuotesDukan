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
import com.inspiration.makrandpawar.quotesdukan.model.QuotesListResponse;

import java.util.List;

public class QuotesFragmentRecyclerAdapter extends RecyclerView.Adapter<QuotesFragmentRecyclerAdapter.QuotesListFragmentViewHolder> {
    private List<QuotesListResponse.Quote> quotes;
    private LayoutInflater inflater;
    private Context context;

    public QuotesFragmentRecyclerAdapter(Context context, List<QuotesListResponse.Quote> quotes) {
        this.quotes = quotes;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public QuotesListFragmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.quoteslistfragment_singlerow, parent, false);
        QuotesListFragmentViewHolder viewHolder = new QuotesListFragmentViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final QuotesListFragmentViewHolder holder, int position) {
        holder.setQuote(quotes.get(position).body, quotes.get(position).author);
        holder.body.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) context.getSystemService(context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("simple text", holder.body.getText().toString() + " :- " + holder.author.getText().toString());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(context, "Copied to clipboard", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return quotes.size();
    }

    public static class QuotesListFragmentViewHolder extends RecyclerView.ViewHolder {
        private TextView body;
        private TextView author;

        public QuotesListFragmentViewHolder(View itemView) {
            super(itemView);

            body = (TextView) itemView.findViewById(R.id.quoteslistfragmentsinglerow_body);
            author = (TextView) itemView.findViewById(R.id.quoteslistfragmentsinglerow_author);
        }

        public void setQuote(String body, String author) {
            this.body.setText(body);
            this.author.setText(author);
        }
    }
}
