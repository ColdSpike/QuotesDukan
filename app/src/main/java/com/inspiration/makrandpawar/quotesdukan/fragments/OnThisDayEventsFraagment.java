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
import com.inspiration.makrandpawar.quotesdukan.adapter.OnThisDayEventsFragmentRecyclerAdapter;
import com.inspiration.makrandpawar.quotesdukan.model.OnThisDayEventsResponse;
import com.inspiration.makrandpawar.quotesdukan.rest.OnThisDayService;
import com.inspiration.makrandpawar.quotesdukan.rest.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OnThisDayEventsFraagment extends Fragment {
    public OnThisDayEventsFraagment() {
    }

    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.onthisdayevents_fragment, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.onthisdayeventsfragment_recyclerview);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));

        callOnThisDayEvents();

        return rootView;
    }

    private void callOnThisDayEvents() {
        RetrofitService retrofitService = new RetrofitService();
        OnThisDayService onThisDayService = retrofitService.getInstanceForOnThisDay().create(OnThisDayService.class);

        onThisDayService.getOnThisDayEvents().enqueue(new Callback<OnThisDayEventsResponse>() {
            @Override
            public void onResponse(Call<OnThisDayEventsResponse> call, Response<OnThisDayEventsResponse> response) {
                OnThisDayEventsFragmentRecyclerAdapter onThisDayEventsFragmentRecyclerAdapter = new OnThisDayEventsFragmentRecyclerAdapter(getActivity(),response.body().data.Events);
                recyclerView.setAdapter(onThisDayEventsFragmentRecyclerAdapter);
            }

            @Override
            public void onFailure(Call<OnThisDayEventsResponse> call, Throwable t) {

            }
        });
    }
}
