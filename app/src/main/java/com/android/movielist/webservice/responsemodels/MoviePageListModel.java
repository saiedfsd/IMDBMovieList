package com.android.movielist.webservice.responsemodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MoviePageListModel implements Serializable {

    @SerializedName("data")
    @Expose
    private List<MovieBaseModel> data = new ArrayList<>();

    @SerializedName("metadata")
    @Expose
    private MetaDataModel metadata;

    public MoviePageListModel() {
    }

    public MoviePageListModel(List<MovieBaseModel> data, MetaDataModel metadata) {
        super();
        this.data = data;
        this.metadata = metadata;
    }

    public List<MovieBaseModel> getData() {
        return data;
    }

    public void setData(List<MovieBaseModel> data) {
        this.data = data;
    }

    public MetaDataModel getMetadata() {
        return metadata;
    }

    public void setMetadata(MetaDataModel metadata) {
        this.metadata = metadata;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(MetaDataModel.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("data");
        sb.append('=');
        sb.append(((this.data == null)?"<null>":this.data));
        sb.append(',');
        sb.append("metadata");
        sb.append('=');
        sb.append(((this.metadata == null)?"<null>":this.metadata));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

}
