package com.android.movielist.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.movielist.R;
import com.android.movielist.interfaces.IMovieItemActionListener;
import com.android.movielist.webservice.responsemodels.MovieBaseModel;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MoviesRecyclerAdapter extends RecyclerView.Adapter<MoviesRecyclerAdapter.MovieBaseViewHolder> {

    private List<MovieBaseModel> movieModels;
    private Context context;
    private IMovieItemActionListener itemActionListener;

    public MoviesRecyclerAdapter(IMovieItemActionListener itemActionListener, Context context) {
        this.movieModels = new ArrayList<>();
        this.context = context;
        this.itemActionListener = itemActionListener;
    }

    @Override
    public MovieBaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = ((LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(R.layout.item_movie,parent);

        return new MovieBaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesRecyclerAdapter.MovieBaseViewHolder holder, int position) {
        holder.movieRate.setText(movieModels.get(position).getImdbRating());
        String title = String.format("%s (%s)",movieModels.get(position).getTitle(),movieModels.get(position).getYear());
        holder.movieRate.setText(title);

        holder.btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemActionListener != null)
                    itemActionListener.onShareMovieItem(movieModels.get(position));
            }
        });
        holder.btnFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemActionListener != null)
                    itemActionListener.onFavoriteMovieItem(movieModels.get(position),position);
            }
        });

        holder.imgPoster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemActionListener != null)
                    itemActionListener.onMovieItemClickListener(movieModels.get(position),position);
            }
        });

        Target target = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                holder.imgPoster.setImageBitmap(bitmap);
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                holder.imgPoster.setImageDrawable(errorDrawable);
                Log.e("fsd",e.getMessage());
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
                holder.imgPoster.setImageDrawable(placeHolderDrawable);
            }
        };
        Picasso.get().load(movieModels.get(position).getPoster()).error(R.drawable.image_holder)
                .placeholder(R.drawable.image_holder)
                .into(target);

    }

    @Override
    public int getItemCount() {
        return movieModels.size();
    }

    public void loadMovies(List<MovieBaseModel> moreMovies){
        movieModels.addAll(moreMovies);
        notifyDataSetChanged();
    }

    public void removeMovieItems(){
        movieModels.clear();
        notifyDataSetChanged();
    }

    class MovieBaseViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.txt_movie_rate)
        AppCompatTextView movieRate;
        @BindView(R.id.txt_movie_title)
        AppCompatTextView movieTitle;
        @BindView(R.id.btn_share)
        ImageButton btnShare;
        @BindView(R.id.btn_favorite)
        ImageButton btnFavorite;
        @BindView(R.id.img_movie_poster)
        ImageView imgPoster;

        public MovieBaseViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(itemView);
        }
    }
}
