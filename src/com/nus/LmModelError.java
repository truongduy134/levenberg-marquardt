package com.nus;

/**
 * Created by duy on 20/1/15.
 */
public interface LmModelError {

  /**
   * Evaluates the error function with input optimization parameter values
   *
   * @param optParams A vector of real values of parameters used in optimizing
   *                  the error function
   * @return Double value of the error function
   */
  public double eval(double[] optParams);

  /**
   * Computes the Jacobian vector of the error function with input
   * optimization parameter values
   *
   * @param optParams A vector of real values of parameters used in optimizing
   *                  the error function
   * @return Jacobian vector of the error function
   */
  public double[] jacobian(double[] optParams);

  /**
   * Computes the Hessian matrix of the error function with input
   * optimization parameter values
   *
   * @param optParams A vector of real values of parameters used in optimizing
   *                  the error function
   * @param approxHessianFlg A boolean flag to indicate whether the Hessian
   *                         matrix can be approximated instead of having to be
   *                         computed exactly
   * @return Hessian matrix of the error function
   */
  public double[][] hessian(double[] optParams, boolean approxHessianFlg);

  /**
   * Computes the Hessian matrix of the error function with input
   * optimization parameter values
   *
   * @param optParams A vector of real values of parameters used in optimizing
   *                  the error function
   * @return The exact Hessian matrix of the error function
   */
  public default double[][] hessian(double[] optParams) {
    return this.hessian(optParams, false);
  }
}
