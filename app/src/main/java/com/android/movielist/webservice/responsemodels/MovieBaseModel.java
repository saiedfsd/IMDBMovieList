package com.android.movielist.webservice.responsemodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MovieBaseModel implements Serializable {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("poster")
    @Expose
    private String poster;
    @SerializedName("year")
    @Expose
    private String year;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("imdb_rating")
    @Expose
    private String imdbRating;
    @SerializedName("genres")
    @Expose
    private List<String> genres = new ArrayList<>();
    @SerializedName("images")
    @Expose
    private List<String> images = new ArrayList<>();


    public MovieBaseModel() {

    }

    public MovieBaseModel(int id, String title, String poster, String year,String country, String imdbRating, List<String> genres, List<String> images) {
        super();
        this.id = id;
        this.title = title;
        this.poster = poster;
        this.year = year;
        this.country = country;
        this.imdbRating = imdbRating;
        this.genres = genres;
        this.images = images;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getImdbRating() {
        return imdbRating;
    }

    public void setImdbRating(String imdbRating) {
        this.imdbRating = imdbRating;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(MovieBaseModel.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("id");
        sb.append('=');
        sb.append(this.id);
        sb.append(',');
        sb.append("title");
        sb.append('=');
        sb.append(((this.title == null)?"<null>":this.title));
        sb.append(',');
        sb.append("poster");
        sb.append('=');
        sb.append(((this.poster == null)?"<null>":this.poster));
        sb.append(',');
        sb.append("year");
        sb.append('=');
        sb.append(((this.year == null)?"<null>":this.year));
        sb.append(',');
        sb.append("country");
        sb.append('=');
        sb.append(((this.country == null)?"<null>":this.country));
        sb.append(',');
        sb.append("imdbRating");
        sb.append('=');
        sb.append(((this.imdbRating == null)?"<null>":this.imdbRating));
        sb.append(',');
        sb.append("genres");
        sb.append('=');
        sb.append(((this.genres == null)?"<null>":this.genres));
        sb.append(',');
        sb.append("images");
        sb.append('=');
        sb.append(((this.images == null)?"<null>":this.images));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

}
