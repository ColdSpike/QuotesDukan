package com.example.makrandpawar.quotesdukan;

import android.annotation.SuppressLint;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.example.makrandpawar.quotesdukan.adapter.CardStackActivityAdapter;
import com.example.makrandpawar.quotesdukan.adapter.QuotesFragmentRecyclerAdapter;
import com.example.makrandpawar.quotesdukan.model.QuotesListResponse;
import com.example.makrandpawar.quotesdukan.rest.QuoteService;
import com.example.makrandpawar.quotesdukan.rest.RetrofitService;
import com.wenchao.cardstack.CardStack;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CardStackActivity extends AppCompatActivity {

    CardStack cardStack;
    CardStackActivityAdapter cardStackActivityAdapter;
    SwipeRefreshLayout swipeRefreshLayout;
    private int flg = 1;
    private int cnt = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_card_stack);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.cardstackactivity_swipe);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                callQuotesListService();
            }
        });

        cardStack = (CardStack) findViewById(R.id.cardstackactivity_cardstack);
        cardStack.setVisibility(View.GONE);

        callQuotesListService();


    }

    private void setCardStak() {
        cardStack.setContentResource(R.layout.cardstackactivity_singlecard);
        cardStack.setStackMargin(20);
        cardStack.setVisibility(View.GONE);


        cardStack.setListener(new CardStack.CardEventListener() {
            @Override
            public boolean swipeEnd(int i, float v) {
                if (flg == 0) {
                    Toast.makeText(CardStackActivity.this, "REFRESH TO LOAD MORE QUOTES", Toast.LENGTH_SHORT).show();
                }
                return v > 300 && flg == 1 ? true : false;
            }

            @Override
            public boolean swipeStart(int i, float v) {
                return false;
            }

            @Override
            public boolean swipeContinue(int i, float v, float v1) {
                return false;
            }

            @Override
            public void discarded(int i, int i1) {
                cnt++;
                if (cnt == 24) {
                    flg = 0;
                    Toast.makeText(CardStackActivity.this, "REFRESH TO LOAD MORE QUOTES", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void topCardTapped() {

            }
        });
    }

    private void callQuotesListService() {
        RetrofitService retrofitService = new RetrofitService();

        QuoteService quoteService = retrofitService.getInstance().create(QuoteService.class);

        quoteService.getQuotes("Token token=c6e2025366085672e9a5b3d86f179b57").enqueue(new Callback<QuotesListResponse>() {
            @Override
            public void onResponse(Call<QuotesListResponse> call, Response<QuotesListResponse> response) {
                flg = 1;
                cnt = 0;

                setCardStak();

                if (cardStackActivityAdapter != null)
                    cardStackActivityAdapter.clear();
                cardStackActivityAdapter = new CardStackActivityAdapter(CardStackActivity.this, 0);
                for (int i = 0; i < 25; i++) {
                    cardStackActivityAdapter.add(response.body().quotes.get(i));
                }
                cardStack.setAdapter(cardStackActivityAdapter);
                cardStack.setVisibility(View.VISIBLE);
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(CardStackActivity.this, "Refresh's remaining: " + response.headers().get("Rate-Limit-Remaining"), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<QuotesListResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }


}
