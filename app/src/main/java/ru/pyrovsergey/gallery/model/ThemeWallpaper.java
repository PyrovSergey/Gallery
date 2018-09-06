package ru.pyrovsergey.gallery.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ThemeWallpaper {
    private String title;
    private String localTitle;
    private int image;

}
