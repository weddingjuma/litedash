package com.rdhruva.litedash.discover;

import com.rdhruva.litedash.BasePresenter;
import com.rdhruva.litedash.BaseView;
import com.rdhruva.litedash.data.Restaurant;
import java.util.List;

/**
 * Contract between the view and the presenter for the "Discover" section of the app.
 */
public class DiscoverContract {

  interface View extends BaseView {

    void setIsLoading(boolean isLoading);

    void showFetchError(Throwable t);

    void showNoRestaurants();

    void showRestaurants(List<Restaurant> restaurantList);
  }

  interface Presenter extends BasePresenter {
    void loadRestaurants();

    void toggleFavorite(Restaurant restaurant);

    boolean isFavorite(Restaurant restaurant);
  }
}
