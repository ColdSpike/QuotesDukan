package com.inspiration.makrandpawar.quotesdukan;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.inspiration.makrandpawar.quotesdukan.adapter.QuotesFragmentRecyclerAdapter;
import com.inspiration.makrandpawar.quotesdukan.model.QuotesListResponse;
import com.inspiration.makrandpawar.quotesdukan.rest.QuoteService;
import com.inspiration.makrandpawar.quotesdukan.rest.RetrofitService;
import com.irozon.sneaker.Sneaker;
import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomMenuButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuotesListFragment extends Fragment {
    private List<QuotesListResponse.Quote> backupQuotes;
    private int whatLayout = 0;

    public QuotesListFragment() {
    }

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private BoomMenuButton bmb;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.quoteslist_fragment, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.quotesfragment_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.quoteslistfragment_swipe);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (QuotesDukan.isConnectionAvailable)
                    callQuotesListService();
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

        bmb = (BoomMenuButton) rootView.findViewById(R.id.quoteslistfragment_bmb);

        createBmb();

        return rootView;
    }

    private void createBmb() {
        HamButton.Builder builder = new HamButton.Builder()
                .normalImageRes(R.drawable.ic_menu_black_24dp)
                .normalText("Linear Cards")
                .containsSubText(true)
                .subNormalText("Quotes will be arranged vertically in linear fashion")
                .textSize(16)
                .typeface(Typeface.DEFAULT_BOLD)
                .subTextSize(14)
                .rippleEffect(true)
                .shadowEffect(true)
                .shadowColor(Color.parseColor("#ee000000"))
                .listener(new OnBMClickListener() {
                    @Override
                    public void onBoomButtonClick(int index) {
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                        whatLayout = 0;
                    }
                });

        HamButton.Builder builder1 = new HamButton.Builder()
                .normalImageRes(R.drawable.ic_dashboard_black_24dp)
                .normalText("Grid Cards")
                .containsSubText(true)
                .subNormalText("Quotes will be arranged in a grid")
                .textSize(16)
                .typeface(Typeface.DEFAULT_BOLD)
                .subTextSize(14)
                .rippleEffect(true)
                .shadowEffect(true)
                .shadowColor(Color.parseColor("#ee000000"))
                .listener(new OnBMClickListener() {
                    @Override
                    public void onBoomButtonClick(int index) {
                        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                        whatLayout = 1;
                    }
                });

        HamButton.Builder builder2 = new HamButton.Builder()
                .normalImageRes(R.drawable.ic_view_carousel_black_24dp)
                .normalText("Display Mode")
                .containsSubText(true)
                .subNormalText("Quotes will be arranged one behind the other")
                .textSize(16)
                .typeface(Typeface.DEFAULT_BOLD)
                .subTextSize(14)
                .rippleEffect(true)
                .shadowEffect(true)
                .shadowColor(Color.parseColor("#ee000000"))
                .listener(new OnBMClickListener() {
                    @Override
                    public void onBoomButtonClick(int index) {
                        Intent cardStackIntent = new Intent(getActivity(), CardStackActivity.class);
                        cardStackIntent.putExtra("DATA", "data");
                        startActivity(cardStackIntent);
                    }
                });
        bmb.addBuilder(builder);
        bmb.addBuilder(builder1);
        bmb.addBuilder(builder2);
        if (QuotesDukan.isConnectionAvailable)
            callQuotesListService();
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

    private void callQuotesListService() {
        RetrofitService retrofitService = new RetrofitService();

        QuoteService quoteService = retrofitService.getInstance().create(QuoteService.class);

        quoteService.getQuotes("Token token=c6e2025366085672e9a5b3d86f179b57").enqueue(new Callback<QuotesListResponse>() {
            @Override
            public void onResponse(Call<QuotesListResponse> call, Response<QuotesListResponse> response) {
                backupQuotes = response.body().quotes;
                if (whatLayout == 1)
                    recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                else if (whatLayout == 0)
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

                QuotesFragmentRecyclerAdapter quotesFragmentRecyclerAdapter = new QuotesFragmentRecyclerAdapter(getActivity(), response.body().quotes);
                recyclerView.setAdapter(quotesFragmentRecyclerAdapter);
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<QuotesListResponse> call, Throwable t) {
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
