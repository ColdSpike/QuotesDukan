package com.example.makrandpawar.quotesdemo;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.style.QuoteSpan;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    TextView title;
    TextView content;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        title = (TextView) findViewById(R.id.title);
        content = (TextView) findViewById(R.id.content);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
               callService();
            }
        });
        callService();
    }

    private void callService(){
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://quotesondesign.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        QuoteService quoteService = retrofit.create(QuoteService.class);

        quoteService.getQuote().enqueue(new Callback<List<QuoteResponse>>() {
            @Override
            public void onResponse(Call<List<QuoteResponse>> call, Response<List<QuoteResponse>> response) {
                List<QuoteResponse> quoteResponse = response.body();
                title.setText(quoteResponse.get(0).title);
                content.setText(Html.fromHtml(quoteResponse.get(0).content));
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<List<QuoteResponse>> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(MainActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }
}
