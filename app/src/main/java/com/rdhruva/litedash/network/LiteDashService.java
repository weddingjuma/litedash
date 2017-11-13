package com.rdhruva.litedash.network;

import com.rdhruva.litedash.Injection;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LiteDashService {
  public static LiteDashApi getApi() {
    Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(Injection.ENDPOINT_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build();
    return retrofit.create(LiteDashApi.class);
  }
}
