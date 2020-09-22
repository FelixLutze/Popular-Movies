package net.felixlutze.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;


public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder>{
    private ArrayList<MovieItem> mMovieItemList;
    private Context context;

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView rank;

        public MovieViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.movie_image);
            rank = itemView.findViewById(R.id.movie_rank);
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

        Glide.with(context).load(currentMovieItem.getImageResource()).placeholder(R.drawable.ic_launcher_foreground).into(holder.image);
        holder.rank.setText("#" + (position+1));

        holder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(context, MovieDetails.class);
                intent.putExtra("name", currentMovieItem.getName());
                intent.putExtra("backdrop", currentMovieItem.getBackdrop());
                intent.putExtra("released", currentMovieItem.getReleased());
                intent.putExtra("rating", currentMovieItem.getRating());
                intent.putExtra("overview", currentMovieItem.getOverview());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMovieItemList.size();
    }
}