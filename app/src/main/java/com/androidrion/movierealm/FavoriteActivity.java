package com.androidrion.movierealm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class FavoriteActivity extends AppCompatActivity {

    Realm realm;
    RealmHelper realmHelper;
    RecyclerView recyclerView;
    MovieAdapter movieAdapter;
    List<MovieModel> movieModel;
    RelativeLayout mainpage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        mainpage = findViewById(R.id.back_button);

        recyclerView = findViewById(R.id.rvdata);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        RealmConfiguration configuration = new RealmConfiguration.Builder().build();
        realm = Realm.getInstance(configuration);

        realmHelper = new RealmHelper(realm);
        movieModel = new ArrayList<>();

        movieModel = realmHelper.getAllMahasiswa();

        show();
        mainpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        movieAdapter.notifyDataSetChanged();
        show();
    }

    public void show(){
        movieAdapter = new MovieAdapter(movieModel, this);
        recyclerView.setAdapter(movieAdapter);
    }

}