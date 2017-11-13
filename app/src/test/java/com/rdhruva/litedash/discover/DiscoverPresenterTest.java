package com.rdhruva.litedash.discover;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import com.rdhruva.litedash.data.Restaurant;
import com.rdhruva.litedash.data.RestaurantsDataSource;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * Tests {@link DiscoverPresenter}.
 */
public class DiscoverPresenterTest {

  @Captor private ArgumentCaptor<RestaurantsDataSource.FetchRestaurantsCallback>
      mFetchCallbackCaptor;
  @Captor private ArgumentCaptor<List<Restaurant>> mRestaurantListCaptor;
  @Mock private DiscoverContract.View mDiscoverView;
  @Mock private RestaurantsDataSource mDataSource;

  private DiscoverPresenter mPresenter;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    mPresenter = new DiscoverPresenter(mDiscoverView, mDataSource);
  }

  @Test
  public void testSetViewToLoadOnStart() {
    mPresenter.start();
    verify(mDiscoverView).setIsLoading(true);
  }

  @Test
  public void testFetchDataOnStart() {
    mPresenter.start();
    verify(mDataSource).fetchRestaurants(any(RestaurantsDataSource.FetchRestaurantsCallback.class));
  }

  @Test
  public void testDataFetchAndDisplay() {
    mPresenter.start();
    verify(mDataSource).fetchRestaurants(mFetchCallbackCaptor.capture());
    mFetchCallbackCaptor.getValue().onRestaurantsFetched(getValidSampleData());
    verify(mDiscoverView).setIsLoading(false);
    verify(mDiscoverView).showRestaurants(mRestaurantListCaptor.capture());
    assertThat(mRestaurantListCaptor.getValue()).hasSameElementsAs(getValidSampleData());
  }

  @Test
  public void testEmptyResponse() {
    mPresenter.start();
    verify(mDataSource).fetchRestaurants(mFetchCallbackCaptor.capture());
    mFetchCallbackCaptor.getValue().onRestaurantsFetched(Collections.<Restaurant>emptyList());
    verify(mDiscoverView).setIsLoading(false);
    verify(mDiscoverView).showNoRestaurants();
  }

  @Test
  public void testErrorResponse() {
    mPresenter.start();
    verify(mDataSource).fetchRestaurants(mFetchCallbackCaptor.capture());
    mFetchCallbackCaptor.getValue().onFetchFailed(new Throwable());
    verify(mDiscoverView).setIsLoading(false);
    verify(mDiscoverView).showFetchError(any(Throwable.class));
  }

  private static List<Restaurant> getValidSampleData() {
    Restaurant r1 = new Restaurant();
    r1.id = 1;
    r1.name = "r1";

    Restaurant r2 = new Restaurant();
    r2.id = 2;
    r2.name = "r2";

    Restaurant r3 = new Restaurant();
    r3.id = 3;
    r3.name = "r3";

    return Arrays.asList(r1, r2, r3);
  }
}
