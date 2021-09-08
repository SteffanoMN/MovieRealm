package com.androidrion.movierealm;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recview;
    private MovieAdapter adapter;
    private ProgressBar progressBar;
    MovieModel movieModels;
    ConstraintLayout rootLayout;
    EditText search;
    CharSequence searchresult="";
    RelativeLayout favorite;

    void getData() {
        AndroidNetworking.get("https://api.themoviedb.org/3/movie/upcoming?api_key=b9dfca59664f58a8ac10cb5506272133&language=en-US&page=1")
                .addPathParameter("pageNumber", "0")
                .addQueryParameter("limit", "3")
                .addHeaders("token", "1234")
                .setTag("test")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray result = response.getJSONArray("results");
                            for (int i = 0; i < result.length(); i++) {
                                JSONObject movieObject = result.getJSONObject(i);
                                movieModels.setOriginal_title(movieObject.getString("original_title"));
                                movieModels.setRelease_date(movieObject.getString("release_date"));
                                movieModels.setOverview(movieObject.getString("overview"));
                                movieModels.setPoster_path(movieObject.getString("poster_path"));
                            }
                            search.addTextChangedListener(new TextWatcher() {
                                @Override
                                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                                }

                                @Override
                                public void onTextChanged(CharSequence s, int start, int before, int count) {
                                    adapter.getFilter().filter(s);
                                    searchresult = s;
                                }

                                @Override
                                public void afterTextChanged(Editable s) {
                                }
                            });
                            adapter.setOnItemClickListener(new MovieAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(int position) {
                                    Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                                    intent.putExtra("original_title", movieModels.getOriginal_title());
                                    intent.putExtra("poster_path", movieModels.getPoster_path());
                                    intent.putExtra("release date", movieModels.getRelease_date());
                                    intent.putExtra("overview", movieModels.getOverview());
                                    startActivity(intent);
                                }
                            });
                            progressBar.setVisibility(View.GONE);
                        } catch (JSONException e) {
                            Log.d("error", e.toString());
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("error", anError.toString());
                    }
                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_main);
        recview = (RecyclerView) findViewById(R.id.rvdata);
        rootLayout = (ConstraintLayout) findViewById(R.id.rootlayout);

        search = (EditText) findViewById(R.id.search_input);
        progressBar = findViewById(R.id.progress_bar);
        getData();

        favorite = findViewById(R.id.favorite_button);
        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FavoriteActivity.class);
                startActivity(intent);
            }
        });
    }
}