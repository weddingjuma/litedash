package com.rdhruva.litedash.location;

/**
 * Hard-codes the lat/long values provided in the assignment.
 */
public class DummyLocationHelper implements LocationHelper {

  @Override
  public double getLatitude() {
    return 37.422740;
  }

  @Override
  public double getLongitude() {
    return -122.139956;
  }
}
