package com.example.makrandpawar.quotesdemo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by MAKRAND PAWAR on 8/9/2017.
 */

public interface QuoteService {
    @GET("/wp-json/posts?filter[orderby]=rand")
    Call<List<QuoteResponse>> getQuote();
}
