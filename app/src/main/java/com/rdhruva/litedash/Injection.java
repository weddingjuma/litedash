package com.rdhruva.litedash;

import android.content.Context;
import com.rdhruva.litedash.data.RestaurantsDataSource;
import com.rdhruva.litedash.data.sources.RestaurantsRemoteDataSource;
import com.rdhruva.litedash.location.DummyLocationHelper;
import com.rdhruva.litedash.location.LocationHelper;
import com.rdhruva.litedash.network.LiteDashApi;
import com.rdhruva.litedash.network.LiteDashService;

/**
 * Replace this with a real DI framework.
 */
public class Injection {

  public static final String ENDPOINT_BASE_URL = "https://api.doordash.com/v2/";

  public static RestaurantsDataSource provideDataSource(Context context) {
    return new RestaurantsRemoteDataSource(context);
  }

  public static LiteDashApi provideApi() {
    return LiteDashService.getApi();
  }

  public static LocationHelper provideLocationHelper() {
    return new DummyLocationHelper();
  }
}
