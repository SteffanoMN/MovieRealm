package com.androidrion.movierealm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnSave, btnDelete, backbutton;
    Realm realm;
    RealmHelper realmHelper;
    MovieModel movieModel;
    Bundle bundle = getIntent().getExtras();
    int numStatus = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        btnSave = findViewById(R.id.save);
        btnDelete = findViewById(R.id.delete);
        backbutton = findViewById(R.id.backbutton);

        //Set up Realm
        Realm.init(DetailActivity.this);
        RealmConfiguration configuration = new RealmConfiguration.Builder().build();
        realm = Realm.getInstance(configuration);

        btnSave.setOnClickListener(this);
        btnDelete.setOnClickListener(this);

        TextView movie_name = findViewById(R.id.movie_name);
        TextView movie_release_date = findViewById(R.id.movie_release_date);
        TextView movie_overview = findViewById(R.id.movie_desc);
        ImageView movie_img = findViewById(R.id.movie_image);

        if (numStatus == 0) {
            btnSave.setVisibility(View.VISIBLE);
            btnDelete.setVisibility(View.INVISIBLE);
        } else if (numStatus == 1) {
            btnSave.setVisibility(View.INVISIBLE);
            btnDelete.setVisibility(View.VISIBLE);
        }

        if (bundle != null) {
            String title = bundle.getString("original_title");
            String date = bundle.getString("release_date");
            String desc = bundle.getString("overview");
            String image = bundle.getString("poster_path");

            movie_name.setText(title);
            movie_release_date.setText(date);
            movie_overview.setText(desc);

            Picasso.get().load(image).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher_round).into(movie_img);
        }
    }

        @Override
        public void onClick (View v){
            if (v == btnSave) {
                movieModel = new MovieModel();
                movieModel.setOriginal_title( bundle.getString("original_title"));
                movieModel.setRelease_date(bundle.getString("release_date"));
                movieModel.setOverview(bundle.getString("overview"));
                movieModel.setPoster_path(bundle.getString("poster_path"));

                realmHelper = new RealmHelper(realm);
                realmHelper.save(movieModel);
                numStatus = 1;

                Toast.makeText(DetailActivity.this, "Berhasil Disimpan!", Toast.LENGTH_SHORT).show();
            } else if (v == btnDelete) {
                realmHelper.delete();
                Toast.makeText(DetailActivity.this, "Delete Success", Toast.LENGTH_SHORT).show();
                finish();
            }
        }

    }

