package com.android.movielist;

import java.util.ArrayList;
import java.util.logging.Filter;

public class SearchFilters {
    private ArrayList<FilterModel> filterModels = new ArrayList<>();
    public SearchFilters() {

    }

    public void deleteFilter(String filterName){
        for (FilterModel filterModel: filterModels){
            if (filterModel.getFilterName().equals(filterName))
                filterModels.remove(filterModel);
        }
    }

    public void addFilter(FilterModel filterModel){
        filterModels.add(filterModel);
    }

    public FilterModel getFilterByIndex(int index){
        return filterModels.get(index);
    }

    public FilterModel getFilterByName(String filterName){
        for (int i = 0; i < filterModels.size(); i++){
            if (filterModels.get(i).getFilterName().equals(filterName))
                return filterModels.get(i);
        }
        return null;
    }
}
