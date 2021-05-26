package com.android.movielist;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

public class FilterModel extends BaseObservable {

    @Bindable
    private String filterName;

    @Bindable
    private boolean isSelect;

    public FilterModel() {
    }

    public String getFilterName() {
        return filterName;
    }

    public void setFilterName(String filterName) {
        this.filterName = filterName;
        notifyPropertyChanged(BR.filterName);
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setIsSelect(boolean isSelect) {
        this.isSelect = isSelect;
        notifyPropertyChanged(BR.isSelect);
    }
}
