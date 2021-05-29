package com.android.movielist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.android.movielist.databinding.ActivityMovieListBinding;

import butterknife.BindView;
import butterknife.ButterKnife;

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