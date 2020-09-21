package net.felixlutze.popularmovies;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

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
    private String movieSorting = "popular";

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

        populateUi(movieSorting);
    }

    public void populateUi(String movieSorting) {
        //clear list by default
        movieItemList.clear();
        mRequestQueue = Volley.newRequestQueue(this);
        parseJSON(movieSorting);

        //Set Activity title based on set sorting
        switch (movieSorting) {
            case "rating":
                MainActivity.this.setTitle("Movies by rating");
                break;

            case "popular":
                MainActivity.this.setTitle("Movies by popularity");
                break;
        }
    }

    public void parseJSON(String movieSorting) {

        String apiKey = "API-KEY";
        String url = "https://api.themoviedb.org/3/discover/movie?api_key="+apiKey+"&sort_by=" ;

        switch (movieSorting) {
            case "rating":
                url = url.concat("vote_average.desc&include_adult=true&page=1");
                break;

            case "popular":
                url = url.concat("popularity.desc&include_adult=true&page=1");
                break;
        }

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
                                String backdrop = movie.getString("backdrop_path");
                                String released = movie.getString("release_date");
                                if (released.isEmpty())
                                    released = "n/a";
                                String overview = movie.getString("overview");
                                if (overview.isEmpty())
                                    overview = "n/a";

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_menu, menu);
        return true;
    }

    // COMPLETED (10) Override onOptionsItemSelected
    // COMPLETED (11) Within this method, get the ID from the MenuItem
    // COMPLETED (12) If the ID equals R.id.action_refresh, create and set a new adapter on the RecyclerView and return true
    // COMPLETED (13) For now, for all other IDs, return super.onOptionsItemSelected
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId = item.getItemId();

        switch (itemId) {
            case R.id.movie_rating:
                movieSorting = "rating";
                populateUi(movieSorting);
                break;
            case R.id.movie_popular:
                movieSorting = "popular";
                populateUi(movieSorting);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}