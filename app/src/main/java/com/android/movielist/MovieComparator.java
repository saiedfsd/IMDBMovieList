package com.android.movielist;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.android.movielist.webservice.responsemodels.MovieBaseModel;

import org.jetbrains.annotations.NotNull;

public class MovieComparator extends DiffUtil.ItemCallback<MovieBaseModel> {
    @Override
    public boolean areItemsTheSame(@NonNull @NotNull MovieBaseModel oldItem, @NonNull @NotNull MovieBaseModel newItem) {
        return oldItem.getId() == newItem.getId();
    }

    @Override
    public boolean areContentsTheSame(@NonNull @NotNull MovieBaseModel oldItem, @NonNull @NotNull MovieBaseModel newItem) {
        return oldItem.getTitle().equals(newItem.getTitle());
    }
}
