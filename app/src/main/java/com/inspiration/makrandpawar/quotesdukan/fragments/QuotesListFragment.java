package com.inspiration.makrandpawar.quotesdukan.fragments;

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

import com.inspiration.makrandpawar.quotesdukan.CardStackActivity;
import com.inspiration.makrandpawar.quotesdukan.QuotesDukan;
import com.inspiration.makrandpawar.quotesdukan.R;
import com.inspiration.makrandpawar.quotesdukan.adapter.QuotesListFragmentRecyclerAdapter;
import com.inspiration.makrandpawar.quotesdukan.model.QuoteListRealmObject;
import com.inspiration.makrandpawar.quotesdukan.model.QuotesListResponse;
import com.inspiration.makrandpawar.quotesdukan.rest.QuoteService;
import com.inspiration.makrandpawar.quotesdukan.rest.RetrofitService;
import com.irozon.sneaker.Sneaker;
import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomMenuButton;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuotesListFragment extends Fragment {
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

        final Realm realm = Realm.getInstance(Realm.getDefaultConfiguration());
        RealmResults<QuoteListRealmObject> result = realm.where(QuoteListRealmObject.class).findAll();
        if (result.size() > 0) {
            if (whatLayout == 1)
                recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
            else if (whatLayout == 0)
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            ArrayList<QuotesListResponse.Quote> quoteList = new ArrayList<>();
            QuotesListResponse.Quote quote;
            for (int i = 0; i < result.size(); i++) {
                quote = new QuotesListResponse.Quote();
                quote.body = result.get(i).getBody();
                quote.author = result.get(i).getAuthor();
                quoteList.add(quote);
            }
            QuotesListFragmentRecyclerAdapter quotesListFragmentRecyclerAdapter = new QuotesListFragmentRecyclerAdapter(getActivity(), quoteList);
            recyclerView.setAdapter(quotesListFragmentRecyclerAdapter);
        } else {
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
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Realm realm = Realm.getInstance(Realm.getDefaultConfiguration());
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.delete(QuoteListRealmObject.class);
            }
        });
    }

    private void callQuotesListService() {
        RetrofitService retrofitService = new RetrofitService();

        QuoteService quoteService = retrofitService.getInstanceForQuote().create(QuoteService.class);

        quoteService.getQuotes("Token token=c6e2025366085672e9a5b3d86f179b57").enqueue(new Callback<QuotesListResponse>() {
            @Override
            public void onResponse(Call<QuotesListResponse> call, final Response<QuotesListResponse> response) {
                if (whatLayout == 1)
                    recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                else if (whatLayout == 0)
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

                QuotesListFragmentRecyclerAdapter quotesListFragmentRecyclerAdapter = new QuotesListFragmentRecyclerAdapter(getActivity(), response.body().quotes);
                recyclerView.setAdapter(quotesListFragmentRecyclerAdapter);
                swipeRefreshLayout.setRefreshing(false);

                final Realm realm = Realm.getInstance(Realm.getDefaultConfiguration());
                realm.executeTransactionAsync(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        RealmResults<QuoteListRealmObject> result = realm.where(QuoteListRealmObject.class).findAll();
                        if (result.size() > 0) {
                            realm.delete(QuoteListRealmObject.class);
                        }
                        for (int i = 0; i < response.body().quotes.size(); i++) {
                            QuoteListRealmObject quoteListRealmObject = realm.createObject(QuoteListRealmObject.class);
                            quoteListRealmObject.setId(911);
                            quoteListRealmObject.setBody(response.body().quotes.get(i).body);
                            quoteListRealmObject.setAuthor(response.body().quotes.get(i).author);
                        }
                    }
                }, new Realm.Transaction.OnSuccess() {
                    @Override
                    public void onSuccess() {
                        realm.close();
                    }
                }, new Realm.Transaction.OnError() {
                    @Override
                    public void onError(Throwable error) {
                        realm.close();
                    }
                });
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
