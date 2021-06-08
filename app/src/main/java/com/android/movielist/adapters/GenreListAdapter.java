package com.android.movielist.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.movielist.R;
import com.android.movielist.webservice.responsemodels.GenreModel;

import java.util.ArrayList;
import java.util.List;

public class GenreListAdapter extends RecyclerView.Adapter<GenreListAdapter.GenreItemViewHolder> {

    private Context context;
    private List<GenreModel> genreModels;
    private IGenreItemSelectionListener genreItemSelectionListener;

    public GenreListAdapter(Context context, IGenreItemSelectionListener genreItemSelectionListener) {
        this.context = context;
        this.genreItemSelectionListener = genreItemSelectionListener;
        genreModels = new ArrayList<>();
    }

    @NonNull
    @Override
    public GenreItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(R.layout.item_genre,parent);

        return new GenreItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GenreListAdapter.GenreItemViewHolder holder, int position) {
        holder.txtGenreTitle.setText(genreModels.get(position).getName());

        holder.txtGenreTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (genreItemSelectionListener != null)
                    genreItemSelectionListener.onGenreItemSelect(genreModels.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return genreModels.size();
    }

    public void loadGenres(List<GenreModel> genreModels){
        genreModels.addAll(genreModels);
        notifyDataSetChanged();
    }

    class GenreItemViewHolder extends RecyclerView.ViewHolder{

        TextView txtGenreTitle;
        public GenreItemViewHolder(@NonNull View itemView) {
            super(itemView);
            txtGenreTitle = itemView.findViewById(R.id.txt_genre_name);
        }
    }

    public interface IGenreItemSelectionListener{
        void onGenreItemSelect(GenreModel genreModel);
    }
}
