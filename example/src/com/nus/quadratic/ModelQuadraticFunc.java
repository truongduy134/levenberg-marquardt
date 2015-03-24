package com.nus.quadratic;

/**
 * Created by duy on 1/2/15.
 */

import com.nus.LmScalarModel;

/**
 * Model: f(x) = a * x^2 + b * x
 */
public class ModelQuadraticFunc implements LmScalarModel {
  private double[] x;
  private double[] y;

  public ModelQuadraticFunc(double x[], double y[]) {
    this.x = x;
    this.y = y;
  }

  public double[] getMeasuredData() {
    return y;
  }

  public double eval(int dataIdx, double[] ab) {
    return ab[0] * x[dataIdx] * x[dataIdx] + ab[1] * x[dataIdx];
  }

  public double[] jacobian(int dataIdx, double[] ab) {
    double[] jVector = new double[2];
    jVector[0] = x[dataIdx] * x[dataIdx];
    jVector[1] = x[dataIdx];
    return jVector;
  }

  public double[][] hessian(int dataIdx, double[] ab) {
    double[][] mat = {{0.0, 0.0}, {0.0, 0.0}};
    return mat;
  }
}
