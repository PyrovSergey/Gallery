package ru.pyrovsergey.gallery.model.dto;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PhotosItem {

    @SerializedName("src")
    private Src src;

    @SerializedName("width")
    private int width;

    @SerializedName("photographer")
    private String photographer;

    @SerializedName("id")
    private int id;

    @SerializedName("url")
    private String url;

    @SerializedName("height")
    private int height;
}