package com.nus;

/**
 * Created by duy on 14/4/15.
 */

/**
 * LmDatumError is an interface for evaluating error, Jacobian matrix and
 * Hessian matrix of a single piece of observed data
 */
public interface LmDatumError {
  /**
   * Gets the total number of observed data
   *
   * @return An integer which is the number of observed data
   */
  public int getNumData();

  /**
   * Evaluates value of the error function for the k-th observed data that
   * corresponds to the parameter vector
   *
   * @param dataIdx The index of the input data
   * @param optParams A vector of real values of parameters in the model
   * @return
   */
  public double eval(int dataIdx, double[] optParams);

  /**
   * Evaluates the Jacobian vector of the error function for the k-th observed
   * data that corresponds to the parameter vector
   *
   * @param dataIdx The index of the input data
   * @param optParams A vector of real values of parameters in the model
   * @return
   */
  public double[] jacobian(int dataIdx, double[] optParams);

  /**
   * Evaluates the Hessian matrix of the error function for the k-th observed
   * data that corresponds to the parameter vector
   *
   * @param dataIdx The index of the input data
   * @param optParams A vector of real values of parameters in the model
   * @return
   */
  public double[][] hessian(int dataIdx, double[] optParams);
}
