package com.example.makrandpawar.quotesdukan.model;

import java.util.List;

public class QuotesListResponse {
    public List<Quote> quotes;
    public static class Quote{
        public String body;
        public String author;
    }
}
