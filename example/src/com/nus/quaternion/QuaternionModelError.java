package com.nus.quaternion;

/**
 * Created by duy on 25/3/15.
 */

import com.nus.LmModelError;

public class QuaternionModelError implements LmModelError {
  private double[][] originalPoints;
  private double[][] transformedPoints;
  private QuaternionExpr quaternionExpr;

  public QuaternionModelError(
      double[][] originalPoints,
      double[][] transformedPoints) {
    this.originalPoints = originalPoints;
    this.transformedPoints = transformedPoints;
    this.quaternionExpr = new QuaternionExpr();
  }

  @Override
  public double eval(double[] optParams) {
    double errorValue = 0.0;
    int numPoints = originalPoints.length;
    for (int i = 0; i < numPoints; ++i) {
      errorValue += this.quaternionExpr.eval(
        optParams, originalPoints[i], transformedPoints[i]);
    }
    return errorValue;
  }

  @Override
  public double[] jacobian(double[] optParams) {
    double[] result = new double[optParams.length];
    for (int i = 0; i < result.length; ++i) {
      result[i] = 0.0;
    }

    int numPoints = originalPoints.length;
    for (int dataIdx = 0; dataIdx < numPoints; ++dataIdx) {
      double[] dataResult = this.quaternionExpr.jacobian(
        optParams, originalPoints[dataIdx], transformedPoints[dataIdx]);
      for (int i = 0; i < result.length; ++i) {
        result[i] += dataResult[i];
      }
    }

    return result;
  }

  @Override
  public double[][] hessian(double[] optParams) {
    double[][] result = new double[optParams.length][optParams.length];
    for (int i = 0; i < result.length; ++i) {
      for (int j = 0; j < result.length; ++j) {
        result[i][j] = 0.0;
      }
    }

    int numPoints = originalPoints.length;
    for (int dataIdx = 0; dataIdx < numPoints; ++dataIdx) {
      double[][] dataResult = this.quaternionExpr.hessian(
        optParams, originalPoints[dataIdx], transformedPoints[dataIdx]);
      for (int i = 0; i < result.length; ++i) {
        for (int j = 0; j < result.length; ++j) {
          result[i][j] += dataResult[i][j];
        }
      }
    }

    return result;
  }
}
