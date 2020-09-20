package net.felixlutze.popularmovies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder>{
    private ArrayList<MovieItem> mMovieItemList;
    private Context context;

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView name;
        public TextView released;
        public TextView rating;

        public MovieViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.movie_image);
            name = itemView.findViewById(R.id.movie_name);
            released = itemView.findViewById(R.id.movie_released);
            rating = itemView.findViewById(R.id.movie_rating);
        }
    }

    public MovieAdapter(ArrayList<MovieItem> movieItemList, Context context) {
        mMovieItemList = movieItemList;
        this.context = context;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list_item, parent, false);

        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        final MovieItem currentMovieItem = mMovieItemList.get(position);

        Picasso.get().load(currentMovieItem.getImageResource()).into(holder.image);
        holder.name.setText(currentMovieItem.getName() + " #" + (position+1));
        holder.released.setText(currentMovieItem.getReleased());
        holder.rating.setText(currentMovieItem.getRating());


        holder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(context , currentMovieItem.getName(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mMovieItemList.size();
    }
}