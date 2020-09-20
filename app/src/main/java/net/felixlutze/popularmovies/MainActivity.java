package net.felixlutze.popularmovies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;

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
        ArrayList<MovieItem> movieItemList = new ArrayList<>();

        movieItemList.add(new MovieItem("https://image.shutterstock.com/image-photo/photo-old-movie-projector-260nw-92369284.jpg", "Example Movie", "Rating..."));
        movieItemList.add(new MovieItem("https://image.shutterstock.com/image-vector/online-cinema-art-movie-watching-260nw-586719869.jpg", "Example Movie", "Rating..."));
        movieItemList.add(new MovieItem("https://image.shutterstock.com/image-photo/photo-old-movie-projector-260nw-92369284.jpg", "Example Movie", "Rating..."));
        movieItemList.add(new MovieItem("https://image.shutterstock.com/image-vector/online-cinema-art-movie-watching-260nw-586719869.jpg", "Example Movie", "Rating..."));
        movieItemList.add(new MovieItem("https://image.shutterstock.com/image-photo/photo-old-movie-projector-260nw-92369284.jpg", "Example Movie", "Rating..."));
        movieItemList.add(new MovieItem("https://image.shutterstock.com/image-vector/online-cinema-art-movie-watching-260nw-586719869.jpg", "Example Movie", "Rating..."));

        RecyclerView.Adapter mAdapter = new MovieAdapter(movieItemList, MainActivity.this);
        mRecyclerView.setAdapter(mAdapter);
    }
}