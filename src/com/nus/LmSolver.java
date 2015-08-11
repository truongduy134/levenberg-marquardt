package com.nus;

import Jama.Matrix;

/**
 * Created by duy on 20/1/15.
 */
public class LmSolver {
  // Configuration parameters for Levenberg-Marquadt algorithm
  private double dampingFactor;
  private int maxNumIter;
  private double gradientEpsilon;
  private double changeEpsilon;

  private LmModelError errorFunc;

  public LmSolver(LmModelError inErrorFunc) {
    this.dampingFactor = 0.001;
    this.maxNumIter = 10;
    this.gradientEpsilon = 1e-8;
    this.changeEpsilon = 1e-8;
    this.errorFunc = inErrorFunc;
  }

  public LmSolver(
    LmModelError errorFunc,
    double damping,
    int maxNumIter,
    double gradientEpsilon,
    double changeEpsilon) {
    this.dampingFactor = damping;
    this.maxNumIter = maxNumIter;
    this.gradientEpsilon = gradientEpsilon;
    this.changeEpsilon = changeEpsilon;
    this.errorFunc = errorFunc;
  }

  public double getDampingFactor() {
    return dampingFactor;
  }

  public void setDampingFactor(double dampingFactor) {
    this.dampingFactor = dampingFactor;
  }

  public int getMaxNumIter() {
    return maxNumIter;
  }

  public LmModelError getErrorFunc() {
    return errorFunc;
  }

  public void setMaxNumIter(int maxNumIter) {
    this.maxNumIter = maxNumIter;
  }

  public double getGradientEpsilon() {
    return gradientEpsilon;
  }

  public void setGradientEpsilon(double gradientEpsilon) {
    this.gradientEpsilon = gradientEpsilon;
  }

  public double getChangeEpsilon() {
    return changeEpsilon;
  }

  public void setChangeEpsilon(double changeEpsilon) {
    this.changeEpsilon = changeEpsilon;
  }

  /**
   * Applies Levenberg-Marquadt algorithm on the input error function with the
   * input initial guess of optimization parameters
   *
   * @param optParams A vector of initial guess of values of parameters
   *                  for optimization
   * @param paramHandler A handler which is called to adjust values of
   *                     the Levenberg-Marquadt parameters after they are
   *                     updated at the end of each iteration in the algorithm.
   *                     If {@code paramHandler} is null, no further adjustment
   *                     to the updated parameters is performed. This is useful
   *                     when Levenberg-Marquadt algorithm is performed on
   *                     structures such as quaternions. Note that the
   *                     way updated parameters are modified can affect
   *                     correctness of the Levenberg-Marquadt algorithm
   * @param approxHessianFlg A boolean flag to indicate whether the Hessian
   *                         matrix used in the Levenberg-Marquadt algorithm
   *                         should be approximated or computed exactly. If
   *                         {@code true}, the Hessian matrix will be
   *                         approximated by the Jacobian matrix
   */
  public void solve(
      double[] optParams,
      LmParamHandler paramHandler,
      boolean approxHessianFlg) {
    int iter = 0;
    int numOptParams = optParams.length;
    double penaltyFactor = 2.0;
    double lambda = 0.0;

    double errValue = errorFunc.eval(optParams);

    while (iter < maxNumIter) {
      iter++;

      // Compute gradient vector
      double[] gradient = errorFunc.jacobian(optParams);
      Matrix gradientMat = new Matrix(gradient, numOptParams);
      if (gradientMat.normInf() < gradientEpsilon) {
        break;
      }

      // Compute modified Hessian matrix
      double[][] modifiedHessian;
      if (approxHessianFlg) {
        modifiedHessian = approximateHessian(gradientMat).getArray();
      } else {
        modifiedHessian = errorFunc.hessian(optParams);
      }
      if (iter == 1) {
        // Initialize damping value on the first iteration
        double diagonalMax = modifiedHessian[0][0];
        for (int i = 1; i < modifiedHessian.length; ++i) {
          diagonalMax = Math.max(diagonalMax, modifiedHessian[i][i]);
        }
        lambda = dampingFactor * diagonalMax;
      }
      for (int i = 0; i < numOptParams; ++i) {
        modifiedHessian[i][i] += lambda;
      }
      Matrix modifiedHessianMat = new Matrix(modifiedHessian);

      // Solve augmented normal equation
      Matrix direction = JamaHelper.solvePSDMatrixEq(
        modifiedHessianMat,
        gradientMat.uminus()
      );
      if (direction == null) {
        // Modified Hessian matrix is not positive definite
        lambda *= penaltyFactor;
        penaltyFactor *= 2.0;
        continue;
      }

      // Stop if the change in optimized parameter vectors is negligible
      Matrix paramVector = new Matrix(optParams, numOptParams);
      if (direction.normF() <
          changeEpsilon * (paramVector.normF() + changeEpsilon)) {
        break;
      }

      double[] newOptParams = paramVector.plus(direction).getRowPackedCopy();

      // Compute gain ratio between actual and predicted gain
      double newErrValue = errorFunc.eval(newOptParams);
      double predictedGain = 0.5 * (
        lambda * JamaHelper.dotProduct(direction, direction) -
        JamaHelper.dotProduct(gradientMat, direction)
      );
      double gainRatio = (errValue - newErrValue) / predictedGain;

      // Update optimized parameter vector and
      // damping value in augmented normal equation
      if (gainRatio > 0) {
        for (int i = 0; i < numOptParams; ++i) {
          optParams[i] = newOptParams[i];
        }
        errValue = newErrValue;
        penaltyFactor = 2.0;
        lambda *= Math.max(1.0 / 3.0, 1 - Math.pow(2.0 * gainRatio - 1, 3));
      } else {
        lambda *= penaltyFactor;
        penaltyFactor *= 2.0;
      }

      // Adjust updated values of the parameters
      if (paramHandler != null) {
        paramHandler.adjust(optParams);
      }
    }
  }

  /**
   * Applies Levenberg-Marquadt algorithm on the input error function with the
   * input initial guess of optimization parameters. Note that the Hessian
   * matrix used in the Levenberg-Marquadt will be computed exactly
   *
   * @param optParams A vector of initial guess of values of parameters
   *                  for optimization
   */
  public void solve(double[] optParams) {
    this.solve(optParams, null, false);
  }

  /**
   * Applies Levenberg-Marquadt algorithm on the input error function with the
   * input initial guess of optimization parameters.
   *
   * @param optParams A vector of initial guess of values of parameters
   *                  for optimization
   * @param approxHessianFlg A boolean flag to indicate whether the Hessian
   *                         matrix used in the Levenberg-Marquadt algorithm
   *                         should be approximated or computed exactly. If
   *                         {@code true}, the Hessian matrix will be
   *                         approximated by the Jacobian matrix
   */
  public void solve(double[] optParams, boolean approxHessianFlg) {
    this.solve(optParams, null, approxHessianFlg);
  }

  /**
   * Applies Levenberg-Marquadt algorithm on the input error function with the
   * input initial guess of optimization parameters. Note that the Hessian
   * matrix used in the Levenberg-Marquadt will be computed exactly
   *
   * @param optParams A vector of initial guess of values of parameters
   *                  for optimization
   * @param paramHandler A handler which is called to adjust values of
   *                     the Levenberg-Marquadt parameters after they are
   *                     updated at the end of each iteration in the algorithm.
   *                     If {@code paramHandler} is null, no further adjustment
   *                     to the updated parameters is performed. This is useful
   *                     when Levenberg-Marquadt algorithm is performed on
   *                     structures such as quaternions. Note that the
   *                     way updated parameters are modified can affect
   *                     correctness of the Levenberg-Marquadt algorithm
   */
  public void solve(double[] optParams, LmParamHandler paramHandler) {
    this.solve(optParams, paramHandler, false);
  }

  private static Matrix approximateHessian(Matrix jacobian) {
    return jacobian.transpose().times(jacobian);
  }
}
