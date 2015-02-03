package com.nus.sincos;

/**
 * Created by duy on 3/2/15.
 */

import com.nus.LmModel;

/**
 * Model: f(x) = a * cos(b * x) + b * sin(a * x)
 * fa = cos(b * x) + b * x * cos(a * x)
 * fb = a * x * (-sin(b * x)) + sin(a * x)
 *
 * fab = -x * sin(b * x) + x * cos(a * x)
 * fba = -x * sin(b * x) + x * cos(a * x)
 *
 * faa = -b * x * x * sin(a * x)
 * fbb = -a * x * x * cos(b * x)
 */
public class ModelSinCosFunc implements LmModel {
  private double[] x;
  private double[] y;

  public ModelSinCosFunc(double x[], double y[]) {
    this.x = x;
    this.y = y;
  }

  public double[] getMeasuredData() {
    return y;
  }

  public double eval(int dataIdx, double[] ab) {
    return ab[0] * Math.cos(ab[1] * x[dataIdx]) +
      ab[1] * Math.sin(ab[0] * x[dataIdx]);
  }

  public double[] jacobian(int dataIdx, double[] ab) {
    double[] jVector = new double[2];
    jVector[0] = Math.cos(ab[1] * x[dataIdx]) +
      ab[1] * x[dataIdx] * Math.cos(ab[0] * x[dataIdx]);
    jVector[1] = -ab[0] * x[dataIdx] * Math.sin(ab[1] * x[dataIdx]) +
      Math.sin(ab[0] * x[dataIdx]);
    return jVector;
  }

  public double[][] hessian(int dataIdx, double[] ab) {
    double[][] mat = new double[2][2];

    mat[0][0] = -ab[1] * x[dataIdx] * x[dataIdx] * Math.sin(ab[0] * x[dataIdx]);
    mat[0][1] = x[dataIdx] * (-Math.sin(ab[1] * x[dataIdx]) +
      Math.cos(ab[0] * x[dataIdx]));
    mat[1][0] = mat[0][1];
    mat[1][1] = -ab[0] * x[dataIdx] * x[dataIdx] * Math.cos(ab[1] * x[dataIdx]);

    return mat;
  }
}
