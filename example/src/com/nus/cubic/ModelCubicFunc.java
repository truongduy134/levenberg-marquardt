package com.nus.cubic;

/**
 * Created by duy on 2/2/15.
 */

import com.nus.LmScalarModel;

/**
 * Model: f(x) = a * x^3 + b * x^2 + c * x + d
 */
public class ModelCubicFunc implements LmScalarModel {
  private double[] x;
  private double[] y;

  public ModelCubicFunc(double x[], double y[]) {
    this.x = x;
    this.y = y;
  }

  public double[] getMeasuredData() {
    return y;
  }

  public double eval(int dataIdx, double[] abcd) {
    double mx = x[dataIdx];
    return abcd[0] * mx * mx * mx + abcd[1] * mx * mx + abcd[2] * mx + abcd[3];
  }

  public double[] jacobian(int dataIdx, double[] abcd) {
    double[] jVector = new double[4];
    double mx = x[dataIdx];
    jVector[3] = 1.0;
    jVector[2] = mx;
    jVector[1] = mx * mx;
    jVector[0] = mx * mx * mx;
    return jVector;
  }

  public double[][] hessian(int dataIdx, double[] abcd) {
    double[][] mat = new double[4][4];
    for (int i = 0; i < 4; ++i) {
      for (int j = 0; j < 4; ++j) {
        mat[i][j] = 0.0;
      }
    }
    return mat;
  }
}
