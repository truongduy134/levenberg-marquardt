package com.nus;

/**
 * Created by duy on 20/1/15.
 */
public interface IErrorFunc {

  /**
   * Evaluates the error function with input parameters
   *
   * @param args Parameter variables
   * @return Double value of the error function
   */
  public double eval(Object... args);

  /**
   * Computes the Jacobian vector of the error function with input parameters
   *
   * @param args Parameter variables
   * @return Jacobian vector of the error function
   */
  public double[] jacobian(Object... args);

  /**
   * Computes the Hessian matrix of the error function with input parameters
   *
   * @param args Parameter variables
   * @return Hessian matrix of the error function
   */
  public double[][] hessian(Object... args);
}
