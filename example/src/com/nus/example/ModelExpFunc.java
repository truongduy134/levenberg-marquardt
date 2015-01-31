package com.nus.example;

/**
 * Created by duy on 29/1/15.
 */

import com.nus.LmModel;

/**
 * Model: f(x) = e^(a * x) + b * x
 *
 * True model: f(x) = e^(2 * x) + 3 * x
 */
public class ModelExpFunc implements LmModel {
  private double[] x;
  private double[] y;

  public ModelExpFunc(double x[], double y[]) {
    this.x = x;
    this.y = y;
  }

  private static double func(double x, double a, double b) {
    return Math.exp(a * x) + b * x;
  }

  public double[] getMeasuredData() {
    return y;
  }

  public double eval(int dataIdx, double[] ab) {
    return Math.exp(ab[0] * x[dataIdx]) + ab[1] * x[dataIdx];
  }

  public double[] jacobian(int dataIdx, double[] ab) {
    double[] jVector = new double[2];
    jVector[0] = x[dataIdx] * Math.exp(ab[0] * x[dataIdx]);
    jVector[1] = x[dataIdx];
    return jVector;
  }

  public double[][] hessian(int dataIdx, double[] ab) {
    double[][] mat = new double[2][2];

    mat[0][0] = x[dataIdx] * x[dataIdx] * Math.exp(ab[0] * x[dataIdx]);
    mat[1][1] = 0.0;
    mat[0][1] = mat[1][0] = 0.0;

    return mat;
  }
}
