package com.inspiration.makrandpawar.quotesdukan.rest;

import com.inspiration.makrandpawar.quotesdukan.model.QotdResponse;
import com.inspiration.makrandpawar.quotesdukan.model.QuotesListResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface QuoteService {
    @GET("qotd/")
    Call<QotdResponse> getQotd();

    @GET("quotes/")
    Call<QuotesListResponse> getQuotes(@Header("authorization") String auth);
}
