package com.inspiration.makrandpawar.quotesdukan.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.inspiration.makrandpawar.quotesdukan.QuotesDukan;
import com.inspiration.makrandpawar.quotesdukan.R;
import com.inspiration.makrandpawar.quotesdukan.adapter.OnThisDayBirthsRecyclerViewAdapter;
import com.inspiration.makrandpawar.quotesdukan.model.OnThisDayBirthsResponse;
import com.inspiration.makrandpawar.quotesdukan.rest.OnThisDayService;
import com.inspiration.makrandpawar.quotesdukan.rest.RetrofitService;
import com.irozon.sneaker.Sneaker;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OnThisDayBirthsFragment extends Fragment {
    private int onceLoaded = 0;

    public OnThisDayBirthsFragment() {
    }

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.onthisdaybirths_fragment, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.onthisdaybirths_recyclerview);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.onthisdaybirts_swipe);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (QuotesDukan.isConnectionAvailable) {
                    if (onceLoaded == 0) {
                        callOnThisDayBirths();
                        onceLoaded = 1;
                    }
                } else {
                    Sneaker.with(getActivity())
                            .setTitle("Error!!")
                            .setMessage("Please check your internet connection and refresh")
                            .setHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
                            .setDuration(4000)
                            .sneakError();
                    swipeRefreshLayout.setRefreshing(false);
                    onceLoaded = 0;
                }
            }
        });

        if (QuotesDukan.isConnectionAvailable) {
            callOnThisDayBirths();
            onceLoaded = 1;
        } else {
            Sneaker.with(getActivity())
                    .setTitle("Error!!")
                    .setMessage("Please check your internet connection and refresh")
                    .setHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
                    .setDuration(4000)
                    .sneakError();
            onceLoaded = 0;
        }

        return rootView;
    }

    private void callOnThisDayBirths() {
        RetrofitService retrofitService = new RetrofitService();

        OnThisDayService onThisDayService = retrofitService.getInstanceForOnThisDay().create(OnThisDayService.class);

        onThisDayService.getOnThisDayBirths().enqueue(new Callback<OnThisDayBirthsResponse>() {
            @Override
            public void onResponse(Call<OnThisDayBirthsResponse> call, Response<OnThisDayBirthsResponse> response) {
                OnThisDayBirthsRecyclerViewAdapter onThisDayBirthsRecyclerViewAdapter = new OnThisDayBirthsRecyclerViewAdapter(getActivity(), response.body().data.Births);
                recyclerView.setAdapter(onThisDayBirthsRecyclerViewAdapter);
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<OnThisDayBirthsResponse> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
            }
        });

    }
}
