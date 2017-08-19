package com.inspiration.makrandpawar.quotesdukan.fragments;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatDelegate;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.inspiration.makrandpawar.quotesdukan.QuotesDukan;
import com.inspiration.makrandpawar.quotesdukan.R;
import com.inspiration.makrandpawar.quotesdukan.model.FavouriteRealmObject;
import com.inspiration.makrandpawar.quotesdukan.model.QotdResponse;
import com.inspiration.makrandpawar.quotesdukan.rest.QuoteService;
import com.inspiration.makrandpawar.quotesdukan.rest.RetrofitService;
import com.irozon.sneaker.Sneaker;

import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class QotdFragment extends android.support.v4.app.Fragment {
    private FloatingActionButton floatingActionButtonShare;
    private Realm realm;

    public QotdFragment() {
    }

    private TextView body;
    private TextView author;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressBar progressBar;
    private TextView doubleQuoteTop, doubleQuoteBottom;
    private ToggleButton favourite;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.qotd_fragment, container, false);

        body = (TextView) rootView.findViewById(R.id.qotdfragment_body);
        author = (TextView) rootView.findViewById(R.id.qotdfragment_author);
        doubleQuoteTop = (TextView) rootView.findViewById(R.id.qotdfragment_doublequotetop);
        doubleQuoteBottom = (TextView) rootView.findViewById(R.id.qotdfragment_doublequotebottom);
        favourite = (ToggleButton) rootView.findViewById(R.id.qotdfragment_favourite);
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.qotdfragment_swipe);
        progressBar = (ProgressBar) rootView.findViewById(R.id.qotdfragment_progressbar);

        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            doubleQuoteBottom.setTextColor(Color.WHITE);
            doubleQuoteTop.setTextColor(Color.WHITE);
            favourite.setBackgroundDrawable(getActivity().getDrawable(R.drawable.ic_star_border_white_24dp));
        } else {
            doubleQuoteBottom.setTextColor(Color.BLACK);
            doubleQuoteTop.setTextColor(Color.BLACK);
            favourite.setBackgroundDrawable(getActivity().getDrawable(R.drawable.ic_star_border_black_24dp));
        }

        if (savedInstanceState != null) {
            body.setText(savedInstanceState.get("QOTD-BODY").toString());
            author.setText(savedInstanceState.get("QOTD-AUTHOR").toString());
        } else {
            progressBar.setVisibility(View.VISIBLE);
            if (QuotesDukan.isConnectionAvailable)
                callQotdService();
            else {
                Sneaker.with(getActivity())
                        .setTitle("Error!!")
                        .setMessage("Please check your internet connection and refresh")
                        .setHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
                        .setDuration(4000)
                        .sneakError();
                swipeRefreshLayout.setRefreshing(false);
            }
        }

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (QuotesDukan.isConnectionAvailable)
                    callQotdService();
                else {
                    Sneaker.with(getActivity())
                            .setTitle("Error!!")
                            .setMessage("Please check your internet connection and refresh")
                            .setHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
                            .setDuration(4000)
                            .sneakError();
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });

        body.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(getActivity().CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("simple text", body.getText().toString() + " :- " + author.getText().toString());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(getActivity(), "Copied to clipboard", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        floatingActionButtonShare = (FloatingActionButton) rootView.findViewById(R.id.qotdfragment_share);
        floatingActionButtonShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!body.getText().toString().equals("") && !author.getText().toString().equals("")) {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_TEXT, body.getText().toString() + " - " + author.getText().toString());
                    startActivity(Intent.createChooser(shareIntent, "Choose App To Share With"));
                } else {
                    Sneaker.with(getActivity())
                            .setTitle("Error!!")
                            .setMessage("Please wait for the Quote to load")
                            .setHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
                            .setDuration(3000)
                            .sneakWarning();
                }
            }
        });

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        realm = Realm.getInstance(Realm.getDefaultConfiguration());
        setFavouriteToggle();
    }

    private void setFavouriteToggle() {
        favourite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (!body.getText().toString().equals("") && !author.getText().toString().equals("")) {
                        realm.executeTransactionAsync(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                RealmResults<FavouriteRealmObject> result = realm.where(FavouriteRealmObject.class).findAll();
                                if (checkIfQuoteExsists(result)) {
                                    FavouriteRealmObject favouriteRealmObject = realm.createObject(FavouriteRealmObject.class);
                                    favouriteRealmObject.setBody(body.getText().toString());
                                    favouriteRealmObject.setAuthor(author.getText().toString());
                                }
                                //favourite.setRating(1);
                            }
                        }, new Realm.Transaction.OnSuccess() {
                            @Override
                            public void onSuccess() {
                                favourite.setBackgroundDrawable(getActivity().getDrawable(R.drawable.ic_star_gold_24dp));
                            }
                        }, new Realm.Transaction.OnError() {
                            @Override
                            public void onError(Throwable error) {
                                error.printStackTrace();
                            }
                        });
                    } else {
                        Sneaker.with(getActivity())
                                .setTitle("Error!!")
                                .setMessage("Please wait for the Quote to load")
                                .setHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
                                .setDuration(3000)
                                .sneakWarning();
                    }
                } else {
                    if (!body.getText().toString().equals("") && !author.getText().toString().equals("")) {
                        realm.executeTransactionAsync(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                RealmResults<FavouriteRealmObject> result = realm.where(FavouriteRealmObject.class).equalTo("body", body.getText().toString()).equalTo("author", author.getText().toString()).findAll();
                                if (result != null || result.size() != 0) {
                                    result.get(0).deleteFromRealm();
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(getActivity(), "Removed From Favourites", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                                // favourite.setRating(0);
                            }
                        }, new Realm.Transaction.OnSuccess() {
                            @Override
                            public void onSuccess() {
                                if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
                                    favourite.setBackgroundDrawable(getActivity().getDrawable(R.drawable.ic_star_border_white_24dp));
                                } else {
                                    favourite.setBackgroundDrawable(getActivity().getDrawable(R.drawable.ic_star_border_black_24dp));
                                }
                            }
                        }, new Realm.Transaction.OnError() {
                            @Override
                            public void onError(Throwable error) {
                                error.printStackTrace();
                            }
                        });
                    } else {
                        Sneaker.with(getActivity())
                                .setTitle("Error!!")
                                .setMessage("Please wait for the Quote to load")
                                .setHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
                                .setDuration(3000)
                                .sneakWarning();
                    }
                }
            }
        });
    }

    private boolean checkIfQuoteExsists(RealmResults<FavouriteRealmObject> result) {
        for (int i = 0; i < result.size(); i++) {
            if (result.get(i).getBody().equals(body.getText().toString()) && result.get(i).getAuthor().equals(author.getText().toString())) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "Already Added To Favourites", Toast.LENGTH_SHORT).show();
                    }
                });
                return false;
            }
        }
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getActivity(), "Added To Favourites", Toast.LENGTH_SHORT).show();
            }
        });
        return true;
    }

    @Override
    public void onStop() {
        super.onStop();
        realm.close();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (!body.getText().toString().equals("") || !author.getText().toString().equals("")) {
            outState.putString("QOTD-BODY", body.getText().toString());
            outState.putString("QOTD-AUTHOR", author.getText().toString());
        }
        super.onSaveInstanceState(outState);
    }

    private void callQotdService() {
        RetrofitService retrofitService = new RetrofitService();

        QuoteService quoteService = retrofitService.getInstanceForQuote().create(QuoteService.class);

        quoteService.getQotd().enqueue(new Callback<QotdResponse>() {
            @Override
            public void onResponse(Call<QotdResponse> call, Response<QotdResponse> response) {
                progressBar.setVisibility(View.GONE);
                body.setText(response.body().quote.body);
                author.setText(response.body().quote.author);
                swipeRefreshLayout.setRefreshing(false);
                favourite.setChecked(false);
                if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
                    favourite.setBackgroundDrawable(getActivity().getDrawable(R.drawable.ic_star_border_white_24dp));
                } else {
                    favourite.setBackgroundDrawable(getActivity().getDrawable(R.drawable.ic_star_border_black_24dp));
                }
            }

            @Override
            public void onFailure(Call<QotdResponse> call, Throwable t) {
                t.printStackTrace();
                Sneaker.with(getActivity())
                        .setTitle("Error!!")
                        .setMessage("Please check your internet connection and refresh")
                        .setHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
                        .setDuration(4000)
                        .sneakError();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
}
