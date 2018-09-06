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
public class Src {

    @SerializedName("small")
    private String small;

    @SerializedName("square")
    private String square;

    @SerializedName("original")
    private String original;

    @SerializedName("large")
    private String large;

    @SerializedName("tiny")
    private String tiny;

    @SerializedName("medium")
    private String medium;

    @SerializedName("large2x")
    private String large2x;

    @SerializedName("portrait")
    private String portrait;

    @SerializedName("landscape")
    private String landscape;

}