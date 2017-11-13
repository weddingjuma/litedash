package com.rdhruva.litedash.discover;

import com.rdhruva.litedash.data.Restaurant;
import com.rdhruva.litedash.data.RestaurantsDataSource;
import java.util.List;

public class DiscoverPresenter implements DiscoverContract.Presenter {

  private final DiscoverContract.View mDiscoverView;
  private final RestaurantsDataSource mDataSource;

  public DiscoverPresenter(
      DiscoverContract.View view,
      RestaurantsDataSource restaurantsDataSource) {
    mDiscoverView = view;
    mDataSource = restaurantsDataSource;
  }

  @Override
  public void start() {
    mDiscoverView.setIsLoading(true);
    loadRestaurants();
  }

  @Override
  public void loadRestaurants() {
    mDataSource.fetchRestaurants(new RestaurantsDataSource.FetchRestaurantsCallback() {
      @Override
      public void onRestaurantsFetched(List<Restaurant> restaurants) {
        mDiscoverView.setIsLoading(false);
        if (restaurants.isEmpty()) {
          mDiscoverView.showNoRestaurants();
        } else {
          mDiscoverView.showRestaurants(restaurants);
        }
      }

      @Override
      public void onFetchFailed(Throwable t) {
        mDiscoverView.setIsLoading(false);
        mDiscoverView.showFetchError(t);
      }
    });
  }

  @Override
  public void toggleFavorite(Restaurant restaurant) {
    mDataSource.toggleFavorite(restaurant);
  }

  @Override
  public boolean isFavorite(Restaurant restaurant) {
    return mDataSource.isFavorite(restaurant);
  }
}
