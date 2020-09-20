package net.felixlutze.popularmovies;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RequestQueue mRequestQueue;
    private ArrayList<MovieItem> movieItemList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Setup RecyclerView for movie List
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView = findViewById(R.id.movies_view);
        mRecyclerView.setLayoutManager(mLayoutManager);
        //Increase Performance
        mRecyclerView.setHasFixedSize(true);

        populateUi();
    }

    public void populateUi() {
        mRequestQueue = Volley.newRequestQueue(this);
        parseJSON("https://api.themoviedb.org/3/discover/movie?api_key=a79ca4a1a3f64471019801edc4668e08&language=en-US&sort_by=popularity.desc&include_adult=true&include_video=true&page=1");
    }

    public void parseJSON(String url) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            //Parsing each object in JSON Array called results
                            JSONArray jsonArray = response.getJSONArray("results");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject movie = jsonArray.getJSONObject(i);
                                String name = movie.getString("title");
                                String image = movie.getString("poster_path");
                                String rating = movie.getString("vote_average");
                                String released = movie.getString("release_date");

                                String backdrop = movie.getString("backdrop_path");
                                String overview = movie.getString("overview");

                                movieItemList.add(new MovieItem("https://image.tmdb.org/t/p/w342/"+image, name, rating, released, backdrop, overview));
                            }

                            RecyclerView.Adapter mAdapter = new MovieAdapter(movieItemList, MainActivity.this);
                            mRecyclerView.setAdapter(mAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mRequestQueue.add(request);
    }
}