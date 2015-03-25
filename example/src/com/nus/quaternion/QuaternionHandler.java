package com.nus.quaternion;

/**
 * Created by duy on 25/3/15.
 */

import com.nus.LmParamHandler;

public class QuaternionHandler implements LmParamHandler {
  /**
   * Normalizes the quaternion at the end of each Levenberg-Marquardt iteration
   * @param quaternion A 4-element array representing the quaternion
   */
  @Override
  public void adjust(double[] quaternion) {
    double norm = Math.sqrt(
      quaternion[0] * quaternion[0] +
      quaternion[1] * quaternion[1] +
      quaternion[2] * quaternion[2] +
      quaternion[3] * quaternion[3]
    );
    for (int i = 0; i < 4; i++) {
      quaternion[i] /= norm;
    }
  }
}
