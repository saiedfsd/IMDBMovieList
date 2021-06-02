package com.android.movielist.costumview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;

import com.android.movielist.R;

public class MovieDetailView extends ConstraintLayout {

    private String propertyString,propertyDescString;
    private AppCompatTextView property,propertyDesc;
    private int propertyColor,propertyDescColor;

    public MovieDetailView(Context context) {
        super(context);
        init(context,null);
    }

    public MovieDetailView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public MovieDetailView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    public MovieDetailView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context,attrs);
    }

    private void init(Context context, AttributeSet attrs){
        View.inflate(context, R.layout.item_movie_detail, this);
        property = findViewById(R.id.txt_movie_property);
        propertyDesc = findViewById(R.id.txt_movie_property_desc);

        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.MovieDetailView, 0, 0);
        propertyString = a.getString(R.styleable.MovieDetailView_moviePropertyName);
        propertyDescString = a.getString(R.styleable.MovieDetailView_moviePropertyDesc);
        propertyColor = a.getColor(R.styleable.MovieDetailView_propertyNameColor, getResources().getColor(android.R.color.white));
        propertyDescColor = a.getColor(R.styleable.MovieDetailView_propertyDescColor, getResources().getColor(android.R.color.white));
        a.recycle();

        property.setText(propertyString);
        propertyDesc.setText(propertyDescString);

        property.setTextColor(propertyColor);
        propertyDesc.setTextColor(propertyDescColor);
    }

    public String getPropertyString() {
        return propertyString;
    }

    public void setPropertyString(String propertyString) {
        this.propertyString = propertyString;
    }

    public String getPropertyDescString() {
        return propertyDescString;
    }

    public void setPropertyDescString(String propertyDescString) {
        this.propertyDescString = propertyDescString;
    }
}
