package ru.pyrovsergey.gallery.model.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Response {

    @SerializedName("next_page")
    private String nextPage;

    @SerializedName("per_page")
    private int perPage;

    @SerializedName("page")
    private int page;

    @SerializedName("photos")
    private List<PhotosItem> photos;

    @SerializedName("total_results")
    private int totalResults;

    public void setNextPage(String nextPage) {
        this.nextPage = nextPage;
    }

    public String getNextPage() {
        return nextPage;
    }

    public void setPerPage(int perPage) {
        this.perPage = perPage;
    }

    public int getPerPage() {
        return perPage;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPage() {
        return page;
    }

    public void setPhotos(List<PhotosItem> photos) {
        this.photos = photos;
    }

    public List<PhotosItem> getPhotos() {
        return photos;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public int getTotalResults() {
        return totalResults;
    }

    @Override
    public String toString() {
        return
                "Response{" +
                        "next_page = '" + nextPage + '\'' +
                        ",per_page = '" + perPage + '\'' +
                        ",page = '" + page + '\'' +
                        ",photos = '" + photos + '\'' +
                        ",total_results = '" + totalResults + '\'' +
                        "}";
    }
}