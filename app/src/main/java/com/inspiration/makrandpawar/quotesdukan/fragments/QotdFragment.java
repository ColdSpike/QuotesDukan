package com.inspiration.makrandpawar.quotesdukan.fragments;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.inspiration.makrandpawar.quotesdukan.QuotesDukan;
import com.inspiration.makrandpawar.quotesdukan.R;
import com.inspiration.makrandpawar.quotesdukan.model.QotdResponse;
import com.inspiration.makrandpawar.quotesdukan.rest.QuoteService;
import com.inspiration.makrandpawar.quotesdukan.rest.RetrofitService;
import com.irozon.sneaker.Sneaker;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class QotdFragment extends android.support.v4.app.Fragment {
    public QotdFragment() {
    }

    private TextView body;
    private TextView author;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.qotd_fragment, container, false);

        body = (TextView) rootView.findViewById(R.id.qotdfragment_body);
        author = (TextView) rootView.findViewById(R.id.qotdfragment_author);
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.qotdfragment_swipe);
        progressBar = (ProgressBar) rootView.findViewById(R.id.qotdfragment_progressbar);

        progressBar.setVisibility(View.VISIBLE);

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

        body.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(getActivity().CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("simple text", body.getText().toString()+" :- "+author.getText().toString());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(getActivity(), "Copied to clipboard", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        return rootView;
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
