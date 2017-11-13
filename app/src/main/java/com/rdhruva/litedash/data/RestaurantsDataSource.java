package com.rdhruva.litedash.data;

import java.util.List;

/**
 * Main entry point for accessing restaurant data.
 */
public interface RestaurantsDataSource {

  interface FetchRestaurantsCallback {
    void onRestaurantsFetched(List<Restaurant>restaurants);
    void onFetchFailed(Throwable t);
  }

  void fetchRestaurants(FetchRestaurantsCallback callback);
  boolean isFavorite(Restaurant restaurant);
  void toggleFavorite(Restaurant restaurant);
}
