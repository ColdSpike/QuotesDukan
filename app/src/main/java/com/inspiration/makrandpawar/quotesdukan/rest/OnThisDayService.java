package com.inspiration.makrandpawar.quotesdukan.rest;

import com.inspiration.makrandpawar.quotesdukan.model.OnThisDayBirthsResponse;
import com.inspiration.makrandpawar.quotesdukan.model.OnThisDayDeathsResponse;
import com.inspiration.makrandpawar.quotesdukan.model.OnThisDayEventsResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface OnThisDayService {
    @GET("date")
    Call<OnThisDayEventsResponse> getOnThisDayEvents();

    @GET("date")
    Call<OnThisDayBirthsResponse> getOnThisDayBirths();

    @GET("date")
    Call<OnThisDayDeathsResponse> getOnThisDayDeaths();
}
