package com.rdhruva.litedash.network;

import com.rdhruva.litedash.data.Restaurant;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface LiteDashApi {

  @GET("restaurant")
  Call<List<Restaurant>> restaurantList(
      @Query("lat") double latitude,
      @Query("lng") double longitude);
}
