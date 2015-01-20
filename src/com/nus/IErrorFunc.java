package com.nus;

/**
 * Created by duy on 20/1/15.
 */
public interface IErrorFunc {

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
   * @return Hessian matrix of the error function
   */
  public double[][] hessian(double[] optParams);
}
