package com.nus;

/**
 * Created by duy on 18/3/15.
 */
public interface LmParamHandler {
  /**
   * Adjusts (or modifies) values of the Levenberg-Marquardt parameters
   *
   * @param lmParams Numbers which are values of Levenberg-Marquardt parameters
   */
  public void adjust(double[] lmParams);
}
