package com.android.movielist;

import androidx.databinding.Bindable;

public class FilterModel {

    private String filterName;
    private boolean isSelect;

    public FilterModel() {
    }

    public String getFilterName() {
        return filterName;
    }

    public void setFilterName(String filterName) {
        this.filterName = filterName;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setIsSelect(boolean isSelect) {
        this.isSelect = isSelect;
    }
}
