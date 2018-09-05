package ru.pyrovsergey.gallery.network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import ru.pyrovsergey.gallery.model.dto.Response;

public interface PexelsApi {
    @GET("/v1/search")
    Call<Response> searchPhoto(@Query("query") String query,
                               @Query("per_page") int per_page,
                               @Query("page") int pageNumber);
}
