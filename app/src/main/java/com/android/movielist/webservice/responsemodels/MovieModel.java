package com.android.movielist.webservice.responsemodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MovieModel extends MovieBaseModel implements Serializable {

    @SerializedName("rated")
    @Expose
    private String rated;
    @SerializedName("released")
    @Expose
    private String released;
    @SerializedName("runtime")
    @Expose
    private String runtime;
    @SerializedName("director")
    @Expose
    private String director;
    @SerializedName("writer")
    @Expose
    private String writer;
    @SerializedName("actors")
    @Expose
    private String actors;
    @SerializedName("plot")
    @Expose
    private String plot;
    @SerializedName("awards")
    @Expose
    private String awards;
    @SerializedName("metascore")
    @Expose
    private String metascore;
    @SerializedName("imdb_votes")
    @Expose
    private String imdbVotes;
    @SerializedName("imdb_id")
    @Expose
    private String imdbId;
    @SerializedName("type")
    @Expose
    private String type;


    public MovieModel() {

    }

    public MovieModel(int id, String title, String poster, String year, String rated, String released, String runtime, String director, String writer, String actors, String plot, String country, String awards, String metascore, String imdbRating, String imdbVotes, String imdbId, String type, List<String> genres, List<String> images) {
        super(id,title,poster,year,country,imdbRating,genres,images);
        this.rated = rated;
        this.released = released;
        this.runtime = runtime;
        this.director = director;
        this.writer = writer;
        this.actors = actors;
        this.plot = plot;
        this.awards = awards;
        this.metascore = metascore;
        this.imdbVotes = imdbVotes;
        this.imdbId = imdbId;
        this.type = type;
    }

    public String getRated() {
        return rated;
    }

    public void setRated(String rated) {
        this.rated = rated;
    }

    public String getReleased() {
        return released;
    }

    public void setReleased(String released) {
        this.released = released;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getAwards() {
        return awards;
    }

    public void setAwards(String awards) {
        this.awards = awards;
    }

    public String getMetascore() {
        return metascore;
    }

    public void setMetascore(String metascore) {
        this.metascore = metascore;
    }

    public String getImdbVotes() {
        return imdbVotes;
    }

    public void setImdbVotes(String imdbVotes) {
        this.imdbVotes = imdbVotes;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(MovieModel.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("id");
        sb.append('=');
        sb.append(getId());
        sb.append(',');
        sb.append("title");
        sb.append('=');
        sb.append(((getTitle() == null)?"<null>":getTitle()));
        sb.append(',');
        sb.append("poster");
        sb.append('=');
        sb.append(((getPoster() == null)?"<null>":getPoster()));
        sb.append(',');
        sb.append("year");
        sb.append('=');
        sb.append(((getYear() == null)?"<null>":getYear()));
        sb.append(',');
        sb.append("rated");
        sb.append('=');
        sb.append(((this.rated == null)?"<null>":this.rated));
        sb.append(',');
        sb.append("released");
        sb.append('=');
        sb.append(((this.released == null)?"<null>":this.released));
        sb.append(',');
        sb.append("runtime");
        sb.append('=');
        sb.append(((this.runtime == null)?"<null>":this.runtime));
        sb.append(',');
        sb.append("director");
        sb.append('=');
        sb.append(((this.director == null)?"<null>":this.director));
        sb.append(',');
        sb.append("writer");
        sb.append('=');
        sb.append(((this.writer == null)?"<null>":this.writer));
        sb.append(',');
        sb.append("actors");
        sb.append('=');
        sb.append(((this.actors == null)?"<null>":this.actors));
        sb.append(',');
        sb.append("plot");
        sb.append('=');
        sb.append(((this.plot == null)?"<null>":this.plot));
        sb.append(',');
        sb.append("country");
        sb.append('=');
        sb.append(((getCountry() == null)?"<null>":getCountry()));
        sb.append(',');
        sb.append("awards");
        sb.append('=');
        sb.append(((this.awards == null)?"<null>":this.awards));
        sb.append(',');
        sb.append("metascore");
        sb.append('=');
        sb.append(((this.metascore == null)?"<null>":this.metascore));
        sb.append(',');
        sb.append("imdbRating");
        sb.append('=');
        sb.append(((getImdbRating() == null)?"<null>":getImdbRating()));
        sb.append(',');
        sb.append("imdbVotes");
        sb.append('=');
        sb.append(((this.imdbVotes == null)?"<null>":this.imdbVotes));
        sb.append(',');
        sb.append("imdbId");
        sb.append('=');
        sb.append(((this.imdbId == null)?"<null>":this.imdbId));
        sb.append(',');
        sb.append("type");
        sb.append('=');
        sb.append(((this.type == null)?"<null>":this.type));
        sb.append(',');
        sb.append("genres");
        sb.append('=');
        sb.append(((getGenres() == null)?"<null>":getGenres()));
        sb.append(',');
        sb.append("images");
        sb.append('=');
        sb.append(((getImages() == null)?"<null>":getImages()));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

}
