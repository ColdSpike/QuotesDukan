package com.inspiration.makrandpawar.quotesdukan;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.inspiration.makrandpawar.quotesdukan.adapter.FavouritesActivityRecyclerViewAdapter;
import com.inspiration.makrandpawar.quotesdukan.model.FavouriteRealmObject;

import io.realm.Realm;

public class FavouritesActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Realm realm;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);

        toolbar = (Toolbar) findViewById(R.id.favouritesactivity_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Favourites");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        realm = Realm.getInstance(Realm.getDefaultConfiguration());

        recyclerView = (RecyclerView) findViewById(R.id.favouritesactivity_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new FavouritesActivityRecyclerViewAdapter(realm.where(FavouriteRealmObject.class).findAll(), this));
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        recyclerView.setAdapter(null);
        realm.close();
    }
}
