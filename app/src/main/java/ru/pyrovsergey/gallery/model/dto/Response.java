package ru.pyrovsergey.gallery.model.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
}