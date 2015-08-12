package com.nus.quaternion;

/**
 * Created by duy on 15/4/15.
 */
import com.nus.LmDatumError;

/**
 * The class implements LmDatumError to evaluate error, Jacobian vector and
 * Hessian matrix of the error function between two points A' and B where A'
 * is the rotated point of A (by the parameter quaternion) for each input point
 * A and corresponding supposedly transformed point B
 *
 * Essentially, the error function computes the squared Euclidean distance
 * between A' and B for each pair of points (A, B)
 */
public class QuaternionPointError implements LmDatumError {
  private double[][] originalPoints;
  private double[][] transformedPoints;

  public QuaternionPointError(
      double[][] originalPoints,
      double[][] transformedPoints) {
    this.originalPoints = originalPoints;
    this.transformedPoints = transformedPoints;
  }

  @Override
  public int getNumData() {
    return this.originalPoints.length;
  }

  @Override
  public double eval(int pointIdx, double[] quaternion) {
    return QuaternionExpr.eval(
      quaternion, originalPoints[pointIdx], transformedPoints[pointIdx]);
  }

  @Override
  public double[] jacobian(int pointIdx, double[] quaternion) {
    return QuaternionExpr.jacobian(
      quaternion, originalPoints[pointIdx], transformedPoints[pointIdx]);
  }

  @Override
  public double[][] hessian(
      int pointIdx, double[] quaternion, boolean approxHessianFlg) {
    return QuaternionExpr.hessian(
      quaternion, originalPoints[pointIdx], transformedPoints[pointIdx]);
  }
}
