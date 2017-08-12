package com.inspiration.makrandpawar.quotesdukan.model;

import java.util.List;

public class OnThisDayEventsResponse {
    public String date;
    public Data data;
    public static class Data{
        public List<EventsClass> Events;
        public static class EventsClass{
            public String year;
            public String text;
        }
//        public List<BirthsClass> Births;
//        public static class BirthsClass{
//            public String year;
//            public String text;
//        }
//        public List<DeathsClass> Deaths;
//        public static class DeathsClass{
//            public String year;
//            public String text;
//        }
    }
}
