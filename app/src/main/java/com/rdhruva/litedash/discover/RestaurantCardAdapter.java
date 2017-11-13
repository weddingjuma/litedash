package com.rdhruva.litedash.discover;

import static android.support.v7.widget.RecyclerView.NO_POSITION;
import static com.bumptech.glide.request.RequestOptions.fitCenterTransform;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.rdhruva.litedash.R;
import com.rdhruva.litedash.data.Restaurant;
import java.util.List;

public class RestaurantCardAdapter
    extends RecyclerView.Adapter<RestaurantCardAdapter.RestaurantViewHolder> {

  private DiscoverContract.Presenter mPresenter;

  public static class RestaurantViewHolder extends RecyclerView.ViewHolder {
    ImageView coverImageView;
    TextView titleView;
    TextView descriptionView;
    TextView statusView;
    RatingBar ratingBar;
    ImageButton favIcon;

    public RestaurantViewHolder(View itemView) {
      super(itemView);
      coverImageView = itemView.findViewById(R.id.cover_image_view);
      titleView = itemView.findViewById(R.id.title_text_view);
      descriptionView = itemView.findViewById(R.id.description_text_view);
      statusView = itemView.findViewById(R.id.status_text_view);
      ratingBar = itemView.findViewById(R.id.rating_bar);
      favIcon = itemView.findViewById(R.id.favorite_button);
    }
  }

  private List<Restaurant> mRestaurants;

  public RestaurantCardAdapter(List<Restaurant> restaurants, DiscoverContract.Presenter presenter) {
    mRestaurants = restaurants;
    mPresenter = presenter;
  }

  @Override
  public RestaurantViewHolder onCreateViewHolder(
      ViewGroup parent,
      int viewType) {
    View v =
        LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_card, parent, false);
    return new RestaurantViewHolder(v);
  }

  @Override
  public void onBindViewHolder(final RestaurantViewHolder holder, final int position) {
    if (position == NO_POSITION) {
      return;
    }

    final Restaurant restaurant = mRestaurants.get(holder.getAdapterPosition());
    holder.titleView.setText(restaurant.name);
    holder.descriptionView.setText(restaurant.description);
    holder.statusView.setText(restaurant.status);
    Glide.with(holder.itemView.getContext())
        .load(restaurant.url)
        .apply(fitCenterTransform())
        .into(holder.coverImageView);

    if (restaurant.rating <= 0.0) {
      holder.ratingBar.setVisibility(View.GONE);
    } else {
      holder.ratingBar.setVisibility(View.VISIBLE);
      holder.ratingBar.setRating(restaurant.rating);
    }

    holder.favIcon.setImageResource(
        mPresenter.isFavorite(restaurant)
            ? R.drawable.favorite_enabled
            : R.drawable.favorite_disabled
    );

    holder.favIcon.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        mPresenter.toggleFavorite(restaurant);
        notifyItemChanged(holder.getAdapterPosition());
      }
    });
  }

  @Override
  public int getItemCount() {
    return mRestaurants.size();
  }
}
