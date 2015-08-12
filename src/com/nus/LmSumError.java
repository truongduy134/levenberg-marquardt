package com.nus;

/**
 * Created by duy on 1/4/15.
 */
public class LmSumError implements LmModelError {
  // Instance to compute the error of a model on a single instance of data
  private LmDatumError datumError;

  public LmSumError(LmDatumError datumError) {
    this.datumError = datumError;
  }

  public LmDatumError getDatumError() {
    return datumError;
  }

  /**
   * Evaluates the error function with input optimization parameter values
   *
   * @param optParams A vector of real values of parameters used in optimizing
   *                  the error function
   * @return Double value of the error function
   */
  @Override
  public double eval(double[] optParams) {
    int numData = datumError.getNumData();
    double sumError = 0.0;

    for (int i = 0; i < numData; ++i) {
      sumError += datumError.eval(i, optParams);
    }

    return sumError;
  }

  /**
   * Computes the Jacobian vector of the error function with input
   * optimization parameter values
   *
   * @param optParams A vector of real values of parameters used in optimizing
   *                  the error function
   * @return Jacobian vector of the error function
   */
  @Override
  public double[] jacobian(double[] optParams) {
    int numData = datumError.getNumData();
    int numParams = optParams.length;
    double[] jacobianVector = new double[numParams];

    for (int i = 0; i < numParams; ++i) {
      jacobianVector[i] = 0.0;
    }

    for (int i = 0; i < numData; ++i) {
      double[] datumJacobian = datumError.jacobian(i, optParams);
      for (int j = 0; j < numParams; ++j) {
        jacobianVector[j] += datumJacobian[j];
      }
    }

    return jacobianVector;
  }

  /**
   * Computes the Hessian matrix of the error function with input
   * optimization parameter values
   *
   * @param optParams A vector of real values of parameters used in optimizing
   *                  the error function
   * @param approxHessianFlg A boolean flag to indicate whether the Hessian
   *                         matrix can be approximated instead of having to be
   *                         computed exactly.
   * @return Hessian matrix of the error function
   */
  @Override
  public double[][] hessian(double[] optParams, boolean approxHessianFlg) {
    int numData = datumError.getNumData();
    int numParams = optParams.length;
    double[][] hessianMat = new double[numParams][numParams];

    for (int i = 0; i < numParams; ++i) {
      for (int j = 0; j < numParams; ++j) {
        hessianMat[i][j] = 0.0;
      }
    }

    for (int k = 0; k < numData; ++k) {
      double[][] datumHessian = datumError.hessian(
        k, optParams, approxHessianFlg);

      for (int i = 0; i < numParams; ++i) {
        for (int j = 0; j < numParams; ++j) {
          hessianMat[i][j] += datumHessian[i][j];
        }
      }
    }

    return hessianMat;
  }
}
