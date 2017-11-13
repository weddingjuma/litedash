package com.rdhruva.litedash.discover;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.rdhruva.litedash.Injection;
import com.rdhruva.litedash.R;
import com.rdhruva.litedash.data.Restaurant;
import java.util.List;

public class DiscoverActivity extends AppCompatActivity implements DiscoverContract.View {

  @BindView(R.id.progress_bar) ProgressBar mProgressBar;
  @BindView(R.id.recycler_view) RecyclerView mRecyclerView;

  private DiscoverContract.Presenter mPresenter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_discover);
    ButterKnife.bind(this);

    setupRecyclerView();
    setupPresenter();
  }

  @Override
  protected void onResume() {
    super.onResume();
    mPresenter.start();
  }

  @Override
  public void setIsLoading(boolean isLoading) {
    if (isLoading) {
      mProgressBar.setVisibility(View.VISIBLE);
    } else {
      mProgressBar.setVisibility(View.GONE);
    }
  }

  @Override
  public void showFetchError(Throwable t) {
    showSnackbar(getString(R.string.fetch_error, t != null ? t.getMessage() : ""));
  }

  @Override
  public void showNoRestaurants() {
    showSnackbar(getString(R.string.no_restaurants));
  }

  @Override
  public void showRestaurants(List<Restaurant> restaurantList) {
    RestaurantCardAdapter adapter =
        new RestaurantCardAdapter(restaurantList, mPresenter);
    mRecyclerView.setAdapter(adapter);
  }

  private void setupPresenter() {
    mPresenter =
        new DiscoverPresenter(this, Injection.provideDataSource(getApplicationContext()));
  }

  private void setupRecyclerView() {
    LinearLayoutManager lm = new LinearLayoutManager(this);
    lm.setOrientation(LinearLayoutManager.VERTICAL);
    mRecyclerView.setLayoutManager(lm);
  }

  private void showSnackbar(String message) {
    Snackbar snackbar =
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_INDEFINITE);
    snackbar.show();
  }
}
