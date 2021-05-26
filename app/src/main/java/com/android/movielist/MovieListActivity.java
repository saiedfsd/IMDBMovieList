package com.android.movielist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.android.movielist.databinding.ActivityMovieListBinding;

public class MovieListActivity extends AppCompatActivity {


    private  SearchFilters searchFilters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMovieListBinding activityBinding = DataBindingUtil.setContentView(this, R.layout.activity_movie_list);
        setSearchFilterModel(new SearchFilters());
        addFilters();
        activityBinding.setSearchFilters(searchFilters);
        activityBinding.setClickListener(this);
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