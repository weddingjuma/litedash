package com.rdhruva.litedash.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.rdhruva.litedash.data.Restaurant;

/**
 * Fairly naive implementation of favorites using local SharedPreferences. There's no syncing
 * implemented. Ideally, the concept of a local favorite should be integrated into the data model.
 */
public class FavoritesHelper {

  private SharedPreferences mSharedPreferences;

  public FavoritesHelper(Context context) {
    mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
  }

  public boolean isRestaurantFavorite(Restaurant restaurant) {
    return mSharedPreferences.getBoolean(Integer.toString(restaurant.id), false);
  }

  public void toggleFavorite(Restaurant restaurant) {
    boolean isFavorite = isRestaurantFavorite(restaurant);
    mSharedPreferences.edit().putBoolean(Integer.toString(restaurant.id), !isFavorite).apply();
  }
}
