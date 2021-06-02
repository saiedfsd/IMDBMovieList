package com.android.movielist;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.widget.TextViewCompat;

import butterknife.OnClick;

public class FilterItemView extends LinearLayout implements View.OnClickListener {

    private boolean isSelected;
    private String filterTitleString;
    private ImageButton btnDeselect;
    private AppCompatTextView filterTitleView;
    private int selectColor,deselectColor;

    public FilterItemView(Context context) {
        super(context);
        init(context,null);
    }

    public FilterItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public FilterItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    public FilterItemView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context,attrs);
    }

    private void init(Context context, AttributeSet attrs){
        View.inflate(context, R.layout.item_filter, this);
        btnDeselect = findViewById(R.id.btn_deselect_filter);
        filterTitleView = findViewById(R.id.txt_filter_name);

        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.FilterItemView, 0, 0);
        filterTitleString = a.getString(R.styleable.FilterItemView_filterTitle);
        isSelected = a.getBoolean(R.styleable.FilterItemView_defaultSelectionState,false);
        selectColor = a.getColor(R.styleable.FilterItemView_selectColor, getResources().getColor(android.R.color.holo_red_light));
        deselectColor = a.getColor(R.styleable.FilterItemView_deselectColor, getResources().getColor(android.R.color.holo_green_light));
        a.recycle();

        filterTitleView.setText(filterTitleString);
//        filterTitleView.setOnClickListener(this);
//        btnDeselect.setOnClickListener(this);
        setOnClickListener(this::onClick);

        if (isSelected)
            setSelectedState();
        else
            setDeselectedState();
    }

    @Override
    public void onClick(View v) {
        if (!isSelected)
            setSelectedState();
        else
            setDeselectedState();
    }

    private void setSelectedState(){
        isSelected = true;
        filterTitleView.setTextColor(selectColor);
        btnDeselect.setVisibility(VISIBLE);
        setBackground(ResourcesCompat.getDrawable(getResources(),R.drawable.item_select_bg,null));

    }

    private void setDeselectedState(){
        isSelected = false;
        filterTitleView.setTextColor(deselectColor);
        btnDeselect.setVisibility(GONE);
        setBackground(ResourcesCompat.getDrawable(getResources(),R.drawable.item_deselect_bg,null));

    }

    @Override
    public boolean isSelected() {
        return isSelected;
    }

    @Override
    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getFilterTitleString() {
        return filterTitleString;
    }

    public void setFilterTitleString(String filterTitleString) {
        this.filterTitleString = filterTitleString;
    }
}
