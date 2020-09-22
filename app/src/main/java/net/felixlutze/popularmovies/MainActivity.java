package net.felixlutze.popularmovies;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
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
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProgressBar = findViewById(R.id.progressBar);
        //Setup RecyclerView for movie List
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this,2);
        mRecyclerView = findViewById(R.id.movies_view);
        mRecyclerView.setLayoutManager(mLayoutManager);
        //Increase Performance
        mRecyclerView.setHasFixedSize(true);

        populateUi(movieSorting);
    }

    public void populateUi(String movieSorting) {
        mProgressBar.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);

        //clear list by default
        mRequestQueue = Volley.newRequestQueue(this);
        parseJSON(movieSorting);

        //Set Activity title based on set sorting
        switch (movieSorting) {
            case "rating":
                MainActivity.this.setTitle("Highest rated Movies");
                break;

            case "popular":
                MainActivity.this.setTitle("Most popular Movies");
                break;
        }
    }

    public void parseJSON(String movieSorting) {
        //Get your API-Key at https://api.themoviedb.org
        String apiKey = "API-KEY";
        String url = null;

        switch (movieSorting) {
            case "rating":
                url = "https://api.themoviedb.org/3/movie/top_rated?api_key="+apiKey;
                break;

            case "popular":
                url = "https://api.themoviedb.org/3/movie/popular?api_key="+apiKey;
                break;
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            //Clear movie list by default
                            movieItemList.clear();
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

                                movieItemList.add(new MovieItem("https://image.tmdb.org/t/p/w500/"+image, name, rating, released, "https://image.tmdb.org/t/p/w1280/"+backdrop, overview));
                            }
                            RecyclerView.Adapter mAdapter = new MovieAdapter(movieItemList, MainActivity.this);
                            mRecyclerView.setAdapter(mAdapter);

                            mProgressBar.setVisibility(View.GONE);
                            mRecyclerView.setVisibility(View.VISIBLE);

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
            case R.id.movie_reload:
                populateUi(movieSorting);
                Toast.makeText(MainActivity.this, "Reloaded movies", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}