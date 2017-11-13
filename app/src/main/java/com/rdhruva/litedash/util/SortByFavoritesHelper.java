package com.rdhruva.litedash.util;

import com.rdhruva.litedash.data.Restaurant;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * This comparator is "quick and dirty" in sorting by a boolean property of the data model. This
 * approach can lead to unintended re-ordering within a section.
 */
public class SortByFavoritesHelper {

  public static void byFavoritesFirst(
      List<Restaurant> restaurantList,
      final FavoritesHelper favoritesHelper) {
    Collections.sort(restaurantList, new Comparator<Restaurant>() {
      @Override
      public int compare(Restaurant r1, Restaurant r2) {
        boolean b1 = favoritesHelper.isRestaurantFavorite(r1);
        boolean b2 = favoritesHelper.isRestaurantFavorite(r2);
        return (b1 != b2) ? b1 ? -1 : 1 : 0;
      }
    });
  }
}
