package com.nus;

import Jama.Matrix;

/**
 * Created by duy on 20/1/15.
 */
public class LMSolver {
  // Configuration parameters for Levenberg-Marquadt algorithm
  private double lambda;
  private int maxNumIter;
  private double termEpsilon;

  private IErrorFunc errorFunc;

  public LMSolver(IErrorFunc inErrorFunc) {
    this.lambda = 0.001;
    this.maxNumIter = 10;
    this.termEpsilon = 0.00001;
    this.errorFunc = inErrorFunc;
  }

  public LMSolver(
      double lambda,
      int maxNumIter,
      double termEpsilon,
      IErrorFunc errorFunc) {
    this.lambda = lambda;
    this.maxNumIter = maxNumIter;
    this.termEpsilon = termEpsilon;
    this.errorFunc = errorFunc;
  }

  public double getLambda() {
    return lambda;
  }

  public void setLambda(double lambda) {
    this.lambda = lambda;
  }

  public int getMaxNumIter() {
    return maxNumIter;
  }

  public IErrorFunc getErrorFunc() {
    return errorFunc;
  }

  public void setMaxNumIter(int maxNumIter) {
    this.maxNumIter = maxNumIter;
  }

  public double getTermEpsilon() {
    return termEpsilon;
  }

  public void setTermEpsilon(double termEpsilon) {
    this.termEpsilon = termEpsilon;
  }

  /**
   * Applies Levenberg-Marquadt on the input error function with the input
   * initial guess of optimization parameters
   *
   * @param optParams A vector of initial guess of values of parameters
   *                  for optimization
   */
  public void solve(double[] optParams) {
    int iter = 0;
    int count = 0;
    boolean stopFlag;
    int numOptParams = optParams.length;

    double errValue = errorFunc.eval(optParams);

    double curLambda = lambda;

    do {
      stopFlag = false;
      iter++;

      // Compute matrices and vectors needed in augmented normal equation
      double[] gradient = errorFunc.jacobian(optParams);
      double[][] modifiedHessian = errorFunc.hessian(optParams);
      for (int i = 0; i < numOptParams; ++i) {
        modifiedHessian[i][i] *= (1 + curLambda);
      }

      // Solve augmented normal equation
      Matrix direction = solveMatrixEq(
        new Matrix(modifiedHessian),
        (new Matrix(gradient, numOptParams)).uminus()
      );
      double[] newOptParams =
        (new Matrix(optParams, numOptParams)).plus(direction).getRowPackedCopy();   // New estimate = estimate + delta

      double newErrValue = errorFunc.eval(newOptParams);

      if (Math.abs(newErrValue - errValue) < termEpsilon) {
        count++;
        if (count == 4) {
          stopFlag = true;
        }
      } else {
        count = 0;
      }

      if (newErrValue > errValue) {
        curLambda *= 10.0;
      } else {
        curLambda /= 10.0;
        errValue = newErrValue;
        for (int i = 0; i < numOptParams; ++i) {
          optParams[i] = newOptParams[i];
        }
      }

      if (iter > maxNumIter) {
        stopFlag = true;
      }

    } while (!stopFlag);
  }

  private static Matrix solveMatrixEq(Matrix A, Matrix b) {
    return A.lu().solve(b);
  }
}
