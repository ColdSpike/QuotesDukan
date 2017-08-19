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
import com.inspiration.makrandpawar.quotesdukan.model.FavouriteRealmObject;
import com.irozon.sneaker.Sneaker;

import io.realm.OrderedRealmCollection;
import io.realm.Realm;
import io.realm.RealmRecyclerViewAdapter;
import io.realm.RealmResults;

public class FavouritesActivityRecyclerViewAdapter extends RealmRecyclerViewAdapter<FavouriteRealmObject, FavouritesActivityRecyclerViewAdapter.FavouritesActivityViewHolder> {

    private final Context context;

    public FavouritesActivityRecyclerViewAdapter(OrderedRealmCollection<FavouriteRealmObject> data, Context context) {
        super(data, true);
        this.context = context;
    }

    @Override
    public FavouritesActivityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.favouritesactivity_singlerow, parent, false);
        return new FavouritesActivityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final FavouritesActivityViewHolder holder, int position) {
        FavouriteRealmObject favouriteRealmObject = getItem(position);
        holder.body.setText(favouriteRealmObject.getBody());
        holder.author.setText(favouriteRealmObject.getAuthor());

        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            holder.doubleQuoteBottom.setTextColor(Color.WHITE);
            holder.doubleQuoteTop.setTextColor(Color.WHITE);
            holder.share.setImageDrawable(context.getDrawable(R.drawable.ic_share_white_24dp));
            holder.unfavourite.setImageDrawable(context.getDrawable(R.drawable.ic_delete_forever_white_24dp));
        } else {
            holder.doubleQuoteBottom.setTextColor(Color.BLACK);
            holder.doubleQuoteTop.setTextColor(Color.BLACK);
            holder.share.setImageDrawable(context.getDrawable(R.drawable.ic_share_black_24dp));
            holder.unfavourite.setImageDrawable(context.getDrawable(R.drawable.ic_delete_forever_black_24dp));
        }

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

        holder.unfavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Realm realm = Realm.getInstance(Realm.getDefaultConfiguration());
                realm.executeTransactionAsync(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        RealmResults<FavouriteRealmObject> result = realm.where(FavouriteRealmObject.class).equalTo("body", holder.body.getText().toString()).equalTo("author", holder.author.getText().toString()).findAll();
                        if (result != null || result.size() != 0) {
                            result.get(0).deleteFromRealm();
                        }
                        // favourite.setRating(0);
                    }
                }, new Realm.Transaction.OnSuccess() {
                    @Override
                    public void onSuccess() {
                        realm.close();
                    }
                }, new Realm.Transaction.OnError() {
                    @Override
                    public void onError(Throwable error) {
                        error.printStackTrace();
                        realm.close();
                    }
                });
            }
        });

    }

    public static class FavouritesActivityViewHolder extends RecyclerView.ViewHolder {
        private TextView body, author, doubleQuoteTop, doubleQuoteBottom;
        private ImageView share, unfavourite;

        public FavouritesActivityViewHolder(View itemView) {
            super(itemView);

            body = (TextView) itemView.findViewById(R.id.favouritesactivitysinglerow_body);
            author = (TextView) itemView.findViewById(R.id.favouritesactivitysinglerow_author);
            doubleQuoteBottom = (TextView) itemView.findViewById(R.id.favouritesactivitysinglerow_doublequotebottom);
            doubleQuoteTop = (TextView) itemView.findViewById(R.id.favouritesactivitysinglerow_doublequotetop);
            share = (ImageView) itemView.findViewById(R.id.favouritesactivitysinglerow_share);
            unfavourite = (ImageView) itemView.findViewById(R.id.favouritesactivitysinglerow_unfavourite);
        }
    }

}
