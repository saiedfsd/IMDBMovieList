package com.android.movielist;

import android.content.Context;

import androidx.paging.PagingState;
import androidx.paging.rxjava3.RxPagingSource;

import com.android.movielist.webservice.apiservices.MovieApiService;
import com.android.movielist.webservice.responsemodels.MovieBaseModel;
import com.android.movielist.webservice.responsemodels.MoviePageListModel;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MovieDataSource extends RxPagingSource<Integer, MovieBaseModel> {

    private MovieApiService movieApiService;

    @Inject
    public MovieDataSource(MovieApiService movieApiService) {
        this.movieApiService = movieApiService;
    }

    @NotNull
    @Override
    public Single<LoadResult<Integer, MovieBaseModel>> loadSingle(@NotNull LoadParams<Integer> loadParams) {
        Integer nextPageNumber = loadParams.getKey();
        if (nextPageNumber == null) {
            nextPageNumber = 1;
        }


        return movieApiService.getMoviesListByPage(nextPageNumber)
                .subscribeOn(Schedulers.io())
                .map(this::toLoadResult)
                .onErrorReturn(LoadResult.Error::new);
    }

    private LoadResult<Integer, MovieBaseModel> toLoadResult(MoviePageListModel moviePageListModel) {

        return new LoadResult.Page<Integer, MovieBaseModel>(
                moviePageListModel.getData(),
                null, // Only paging forward.
                Integer.parseInt(moviePageListModel.getMetadata().getCurrentPage()) + 1,
                LoadResult.Page.COUNT_UNDEFINED,
                LoadResult.Page.COUNT_UNDEFINED);
    }

    @Nullable
    @Override
    public Integer getRefreshKey(@NotNull PagingState<Integer, MovieBaseModel> pagingState) {
        Integer anchorPosition = pagingState.getAnchorPosition();
        if (anchorPosition == null) {
            return null;
        }

        LoadResult.Page<Integer, MovieBaseModel> anchorPage = pagingState.closestPageToPosition(anchorPosition);
        if (anchorPage == null) {
            return null;
        }

        Integer prevKey = anchorPage.getPrevKey();
        if (prevKey != null) {
            return prevKey + 1;
        }

        Integer nextKey = anchorPage.getNextKey();
        if (nextKey != null) {
            return nextKey - 1;
        }

        return null;
    }


}
