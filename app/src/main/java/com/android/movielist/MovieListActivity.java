package com.android.movielist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.room.Room;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.android.movielist.databinding.ActivityMovieListBinding;
import com.android.movielist.db.ImdbMoviesDB;
import com.android.movielist.db.entities.GenreEntity;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MovieListActivity extends AppCompatActivity {


    private  SearchFilters searchFilters;
    @BindView(R.id.btn_collapse_filters)
    public ImageButton btnFiltersCollapse;
    @BindView(R.id.btn_search)
    public ImageButton btnSearch;
    @BindView(R.id.btn_sort)
    public ImageButton btnSort;
    @BindView(R.id.btn_genres)
    public ImageButton btnGenres;
    @BindView(R.id.filters_parent)
    public ConstraintLayout filtersParent;

    private boolean filtersIsCollapsed = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMovieListBinding activityBinding = DataBindingUtil.setContentView(this, R.layout.activity_movie_list);
        setSearchFilterModel(new SearchFilters());
        addFilters();
        activityBinding.setSearchFilters(searchFilters);
        ButterKnife.bind(this);
        btnFiltersCollapse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (filtersIsCollapsed){
                    filtersIsCollapsed = false;
                    btnFiltersCollapse.setImageResource(R.drawable.arrow_up_ico);
                    filtersParent.setVisibility(View.VISIBLE);
                }else{
                    filtersIsCollapsed = true;
                    btnFiltersCollapse.setImageResource(R.drawable.arrow_down_ico);
                    filtersParent.setVisibility(View.GONE);
                }
            }
        });

        ImdbMoviesDB db = Room
                .databaseBuilder(getApplicationContext(), ImdbMoviesDB.class, "imdb.db")
                .build();

        db.genreDao().getGenreById(1)
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GenreEntity>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.e("FSD","onSubscribe");
                    }

                    @Override
                    public void onNext(@NonNull GenreEntity genreEntity) {
                        Log.e("FSD","onNext");
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e("FSD","onError :" + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.e("FSD","onComplete");
                    }
                });
    }


    private void addFilters() {
        String[] filtersArray = getResources().getStringArray(R.array.search_filters);
        for(int i = 0 ; i < filtersArray.length; i++){
            FilterModel filterModel = new FilterModel();
            filterModel.setFilterName(filtersArray[i]);
            filterModel.setIsSelect(false);
            searchFilters.addFilter(filterModel);
        }
    }

    public SearchFilters getSearchFilterModel() {
        return searchFilters;
    }

    public void setSearchFilterModel(SearchFilters searchFilters) {
        this.searchFilters = searchFilters;
    }

    public void onFilterItemClickListener(FilterModel filterModel){
        filterModel.setIsSelect(!filterModel.isSelect());
    }

}