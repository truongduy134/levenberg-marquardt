package com.nus.cubic;

/**
 * Created by duy on 2/2/15.
 */

import com.nus.LmModel;

/**
 * Model: f(x) = a * x^3 + b * x^2 + c * x
 */
public class ModelCubicFunction implements LmModel {
  private double[] x;
  private double[] y;

  public ModelCubicFunction(double x[], double y[]) {
    this.x = x;
    this.y = y;
  }

  public double[] getMeasuredData() {
    return y;
  }

  public double eval(int dataIdx, double[] abc) {
    double mx = x[dataIdx];
    return abc[0] * mx * mx * mx + abc[1] * mx * mx + abc[2] * mx ;
  }

  public double[] jacobian(int dataIdx, double[] abc) {
    double[] jVector = new double[3];
    double mx = x[dataIdx];
    jVector[2] = mx;
    jVector[1] = mx * mx;
    jVector[0] = mx * mx * mx;
    return jVector;
  }

  public double[][] hessian(int dataIdx, double[] abc) {
    double[][] mat = new double[3][3];
    for (int i = 0; i < 3; ++i) {
      for (int j = 0; j < 3; ++j) {
        mat[i][j] = 0.0;
      }
    }
    return mat;
  }
}
