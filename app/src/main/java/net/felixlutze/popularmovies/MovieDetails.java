package net.felixlutze.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class MovieDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);


        ImageView mBackdrop = findViewById(R.id.movie_backdrop);
        TextView mName = findViewById(R.id.movie_name);
        TextView mReleased = findViewById(R.id.movie_released);
        TextView mRating = findViewById(R.id.movie_rating);
        TextView mOverview = findViewById(R.id.movie_overview);

        Intent intent = getIntent();
        String backdrop = intent.getStringExtra("backdrop");
        String name = intent.getStringExtra("name");
        String released = intent.getStringExtra("released");
        String rating = intent.getStringExtra("rating");
        String overview = intent.getStringExtra("overview");

        this.setTitle(name);
        Glide.with(this).load(backdrop).placeholder(R.drawable.ic_launcher_foreground).into(mBackdrop);
        mName.setText(name);
        mReleased.setText(released);
        mRating.setText(rating);
        mOverview.setText(overview);

    }
}