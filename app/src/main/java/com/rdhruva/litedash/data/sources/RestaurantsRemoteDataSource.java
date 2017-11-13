package com.rdhruva.litedash.data.sources;

import android.content.Context;
import android.support.annotation.NonNull;
import com.rdhruva.litedash.Injection;
import com.rdhruva.litedash.data.Restaurant;
import com.rdhruva.litedash.data.RestaurantsDataSource;
import com.rdhruva.litedash.location.LocationHelper;
import com.rdhruva.litedash.network.LiteDashApi;
import com.rdhruva.litedash.util.FavoritesHelper;
import com.rdhruva.litedash.util.SortByFavoritesHelper;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * This data source fetches the list of restaurants at the current location.
 */
public class RestaurantsRemoteDataSource implements RestaurantsDataSource {

  private static final int MAX_ITEMS = 100;

  private FavoritesHelper mFavoritesHelper;
  private LiteDashApi mLiteDashApi;
  private LocationHelper mLocationHelper;

  private List<Restaurant> mCachedResponse;
  private double mCachedLatitude;
  private double mCachedLongitude;

  public RestaurantsRemoteDataSource(Context context) {
    mFavoritesHelper = new FavoritesHelper(context);
    mLiteDashApi = Injection.provideApi();
    mLocationHelper = Injection.provideLocationHelper();
  }

  @Override
  public void fetchRestaurants(final FetchRestaurantsCallback callback) {
    if (mLocationHelper.getLatitude() == mCachedLatitude &&
        mLocationHelper.getLongitude() == mCachedLongitude &&
        mCachedResponse != null &&
        !mCachedResponse.isEmpty()) {
      callback.onRestaurantsFetched(mCachedResponse);
      return;
    }

    Call<List<Restaurant>> call =
        mLiteDashApi.restaurantList(mLocationHelper.getLatitude(), mLocationHelper.getLongitude());
    call.enqueue(new Callback<List<Restaurant>>() {
      @Override
      public void onResponse(
          @NonNull Call<List<Restaurant>> call, @NonNull Response<List<Restaurant>> response) {
        List<Restaurant> fetchedList = response.body();
        if (fetchedList == null) {
          onFailure(call, new IllegalStateException());
          return;
        }
        if (fetchedList.isEmpty()) {
          // If the response is empty, let's skip caching it.
          mCachedResponse = null;
          callback.onRestaurantsFetched(fetchedList);
          return;
        }

        int responseSize = fetchedList.size();
        // Because we don't have pagination in the API endpoint, we artificially limit the response
        // size to 100 items.
        fetchedList = fetchedList.subList(0, responseSize > MAX_ITEMS ? MAX_ITEMS : responseSize);
        SortByFavoritesHelper.byFavoritesFirst(fetchedList, mFavoritesHelper);

        mCachedResponse = fetchedList;
        mCachedLatitude = mLocationHelper.getLatitude();
        mCachedLongitude = mLocationHelper.getLongitude();
        callback.onRestaurantsFetched(mCachedResponse);
      }

      @Override
      public void onFailure(@NonNull Call<List<Restaurant>> call, @NonNull Throwable t) {
        callback.onFetchFailed(t);
      }
    });
  }

  @Override
  public boolean isFavorite(Restaurant restaurant) {
    return mFavoritesHelper.isRestaurantFavorite(restaurant);
  }

  @Override
  public void toggleFavorite(Restaurant restaurant) {
    mFavoritesHelper.toggleFavorite(restaurant);
  }
}
