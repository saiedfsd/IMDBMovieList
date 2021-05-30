package com.android.movielist.webservice.responsemodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MetaDataModel implements Serializable {

    @SerializedName("current_page")
    @Expose
    private String currentPage;

    @SerializedName("per_page")
    @Expose
    private int perPage;

    @SerializedName("page_count")
    @Expose
    private int pageCount;

    @SerializedName("total_count")
    @Expose
    private int totalCount;

    public MetaDataModel() {

    }

    public MetaDataModel(String currentPage, int perPage, int pageCount, int totalCount) {
        super();
        this.currentPage = currentPage;
        this.perPage = perPage;
        this.pageCount = pageCount;
        this.totalCount = totalCount;
    }

    public String getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }

    public int getPerPage() {
        return perPage;
    }

    public void setPerPage(int perPage) {
        this.perPage = perPage;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(MetaDataModel.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("currentPage");
        sb.append('=');
        sb.append(((this.currentPage == null)?"<null>":this.currentPage));
        sb.append(',');
        sb.append("perPage");
        sb.append('=');
        sb.append(this.perPage);
        sb.append(',');
        sb.append("pageCount");
        sb.append('=');
        sb.append(this.pageCount);
        sb.append(',');
        sb.append("totalCount");
        sb.append('=');
        sb.append(this.totalCount);
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }


}
