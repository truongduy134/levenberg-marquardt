package com.nus;

/**
 * Created by duy on 27/1/15.
 */

/**
 * LmScalarModel is an interface for models (functions) whose ranges are
 * single real-valued numbers
 */
public interface LmScalarModel {
  /**
   * Gets the vector of real numbers containing measured output data,
   */
  public double[] getMeasuredData();

  /**
   * Evaluates the model's estimated output for the k-th input data that
   * corresponds to the parameter vector
   * @param dataIdx The index of the input data
   * @param optParams A vector of real values of parameters in the model
   * @return Estimated output value produced by the model
   */
  public double eval(int dataIdx, double[] optParams);

  /**
   * Computes the model's Jacobian vector for the k-th input data that
   * corresponds to the parameter vector
   *
   * @param dataIdx The index of the input data
   * @param optParams A vector of real values of parameters in the model
   * @return Jacobian vector of the model for the specified input data
   */
  public double[] jacobian(int dataIdx, double[] optParams);

  /**
   * Computes the model's Hessian matrix for the k-th input data that
   * corresponds to the parameter vector
   *
   * @param dataIdx The index of the input data
   * @param optParams A vector of real values of parameters in the model
   * @return Hessian matrix of the model for the specified input data
   */
  public double[][] hessian(int dataIdx, double[] optParams);
}
