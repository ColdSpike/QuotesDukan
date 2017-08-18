package com.inspiration.makrandpawar.quotesdukan.adapter;


import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.inspiration.makrandpawar.quotesdukan.R;
import com.inspiration.makrandpawar.quotesdukan.model.QuotesListResponse;
import com.irozon.sneaker.Sneaker;

import java.util.List;

public class QuotesListFragmentRecyclerAdapter extends RecyclerView.Adapter<QuotesListFragmentRecyclerAdapter.QuotesListFragmentViewHolder> {
    private List<QuotesListResponse.Quote> quotes;
    private LayoutInflater inflater;
    private Context context;

    public QuotesListFragmentRecyclerAdapter(Context context, List<QuotesListResponse.Quote> quotes) {
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
        holder.setQuote(quotes.get(position).body, quotes.get(position).author, context);
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

        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!holder.body.getText().toString().equals("") && !holder.author.getText().toString().equals("")) {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_TEXT, holder.body.getText().toString() + " - " + holder.author.getText().toString());
                    context.startActivity(Intent.createChooser(shareIntent, "Choose App To Share With"));
                } else {
                    Sneaker.with((Activity) context)
                            .setTitle("Error!!")
                            .setMessage("Please wait for the Quote to load")
                            .setHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
                            .setDuration(3000)
                            .sneakWarning();
                }
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
        private ImageView share;
        private TextView doubleQuoteTop, doubleQuoteBottom;

        public QuotesListFragmentViewHolder(View itemView) {
            super(itemView);

            body = (TextView) itemView.findViewById(R.id.quoteslistfragmentsinglerow_body);
            author = (TextView) itemView.findViewById(R.id.quoteslistfragmentsinglerow_author);
            share = (ImageView) itemView.findViewById(R.id.quoteslistfragmentsinglerow_share);
            doubleQuoteTop = (TextView) itemView.findViewById(R.id.quoteslistfragmentsinglerow_doublequotetop);
            doubleQuoteBottom = (TextView) itemView.findViewById(R.id.quoteslistfragmentsinglerow_doublequotebottom);
        }

        public void setQuote(String body, String author, Context context) {
            if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
                doubleQuoteBottom.setTextColor(Color.WHITE);
                doubleQuoteTop.setTextColor(Color.WHITE);
                share.setImageDrawable(context.getDrawable(R.drawable.ic_share_white_24dp));
            } else {
                doubleQuoteBottom.setTextColor(Color.BLACK);
                doubleQuoteTop.setTextColor(Color.BLACK);
                share.setImageDrawable(context.getDrawable(R.drawable.ic_share_black_24dp));
            }
            this.body.setText(body);
            this.author.setText(author);
        }
    }
}
