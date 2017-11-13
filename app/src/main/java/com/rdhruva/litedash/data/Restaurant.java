package com.rdhruva.litedash.data;

import com.google.gson.annotations.SerializedName;
import java.util.Objects;

/**
 * Hand-written model for a Restaurant object returned by REST API call, serialized by GSON.
 * Ideally, if this implemented Parcelable, we could cache the call result list across app restarts.
 */
public class Restaurant {

  @SerializedName("id")
  public int id;

  @SerializedName("name")
  public String name;

  @SerializedName("description")
  public String description;

  @SerializedName("cover_img_url")
  public String url;

  @SerializedName("status")
  public String status;

  @SerializedName("delivery_fee")
  public int deliveryFee;

  @SerializedName("average_rating")
  public float rating;

  @Override
  public boolean equals(Object obj) {
    if (obj == null || !(obj instanceof Restaurant)) {
      return super.equals(obj);
    }

    return id == ((Restaurant) obj).id;
  }

  @Override
  public int hashCode() {
    int result = 17;
    result = 31 * result + id;
    result = 31 * result + name.hashCode();
    result = 31 * result + url.hashCode();
    return result;
  }
}
