package com.nus.approxhessianexpfunc;

/**
 * Created by duy on 12/8/15.
 */

import com.nus.LmScalarModel;

/**
 * Model: f(x) = e^(a * x) + b * x
 */
public class ModelExpFunc implements LmScalarModel {
  private double[] x;
  private double[] y;

  public ModelExpFunc(double x[], double y[]) {
    this.x = x;
    this.y = y;
  }

  @Override
  public double[] getMeasuredData() {
    return y;
  }

  @Override
  public double eval(int dataIdx, double[] ab) {
    return Math.exp(ab[0] * x[dataIdx]) + ab[1] * x[dataIdx];
  }

  @Override
  public double[] jacobian(int dataIdx, double[] ab) {
    double[] jVector = new double[2];
    jVector[0] = x[dataIdx] * Math.exp(ab[0] * x[dataIdx]);
    jVector[1] = x[dataIdx];
    return jVector;
  }

  @Override
  public double[][] hessian(int dataIdx, double[] ab) {
    return null;
  }
}
