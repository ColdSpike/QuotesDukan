package com.inspiration.makrandpawar.quotesdukan.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.inspiration.makrandpawar.quotesdukan.R;
import com.inspiration.makrandpawar.quotesdukan.adapter.OnThisDayBirthsRecyclerViewAdapter;
import com.inspiration.makrandpawar.quotesdukan.adapter.OnThisDayDeathsRecyclerViewAdapter;
import com.inspiration.makrandpawar.quotesdukan.model.OnThisDayBirthsResponse;
import com.inspiration.makrandpawar.quotesdukan.model.OnThisDayDeathsResponse;
import com.inspiration.makrandpawar.quotesdukan.rest.OnThisDayService;
import com.inspiration.makrandpawar.quotesdukan.rest.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OnThisDayDeathsFragment extends Fragment {
    public OnThisDayDeathsFragment() {
    }

    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.onthisdaydeaths_fragment, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.onthisdaydeaths_recyclerview);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));

        callOnThisDayDeaths();

        return rootView;
    }

    private void callOnThisDayDeaths() {
        RetrofitService retrofitService = new RetrofitService();

        OnThisDayService onThisDayService = retrofitService.getInstanceForOnThisDay().create(OnThisDayService.class);

        onThisDayService.getOnThisDayDeaths().enqueue(new Callback<OnThisDayDeathsResponse>() {
            @Override
            public void onResponse(Call<OnThisDayDeathsResponse> call, Response<OnThisDayDeathsResponse> response) {
                OnThisDayDeathsRecyclerViewAdapter onThisDayDeathsRecyclerViewAdapter = new OnThisDayDeathsRecyclerViewAdapter(getActivity(),response.body().data.Deaths);
                recyclerView.setAdapter(onThisDayDeathsRecyclerViewAdapter);
            }

            @Override
            public void onFailure(Call<OnThisDayDeathsResponse> call, Throwable t) {

            }
        });

    }
}
