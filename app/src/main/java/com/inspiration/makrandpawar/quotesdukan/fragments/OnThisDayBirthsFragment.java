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
import com.inspiration.makrandpawar.quotesdukan.model.OnThisDayBirthsResponse;
import com.inspiration.makrandpawar.quotesdukan.rest.OnThisDayService;
import com.inspiration.makrandpawar.quotesdukan.rest.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OnThisDayBirthsFragment extends Fragment {
    public OnThisDayBirthsFragment() {
    }

    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.onthisdaybirths_fragment, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.onthisdaybirths_recyclerview);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));

        callOnThisDayBirths();

        return rootView;
    }

    private void callOnThisDayBirths() {
        RetrofitService retrofitService = new RetrofitService();

        OnThisDayService onThisDayService = retrofitService.getInstanceForOnThisDay().create(OnThisDayService.class);

        onThisDayService.getOnThisDayBirths().enqueue(new Callback<OnThisDayBirthsResponse>() {
            @Override
            public void onResponse(Call<OnThisDayBirthsResponse> call, Response<OnThisDayBirthsResponse> response) {
                OnThisDayBirthsRecyclerViewAdapter onThisDayBirthsRecyclerViewAdapter = new OnThisDayBirthsRecyclerViewAdapter(getActivity(),response.body().data.Births);
                recyclerView.setAdapter(onThisDayBirthsRecyclerViewAdapter);
            }

            @Override
            public void onFailure(Call<OnThisDayBirthsResponse> call, Throwable t) {

            }
        });

    }
}
