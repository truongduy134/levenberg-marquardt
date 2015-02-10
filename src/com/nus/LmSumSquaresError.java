package com.nus;

import Jama.Matrix;

/**
 * Created by duy on 27/1/15.
 */
public class LmSumSquaresError implements LmModelError {
  private LmModel model;

  public LmSumSquaresError(LmModel model) {
    this.model = model;
  }

  public LmModel getModel() {
    return model;
  }

  public void setModel(LmModel model) {
    this.model = model;
  }

  /**
   * Evaluates the error function with input optimization parameter values
   *
   * @param optParams A vector of real values of parameters used in optimizing
   *                  the error function
   * @return Double value of the error function
   */
  public double eval(double[] optParams) {
    double[] measuredOutputs = model.getMeasuredData();
    int numData = measuredOutputs.length;

    double errorSum = 0.0;
    for (int i = 0; i < numData; ++i) {
      double error = measuredOutputs[i] - model.eval(i, optParams);
      errorSum += error * error;
    }

    return 0.5 * errorSum;
  }

  /**
   * Computes the Jacobian vector of the error function with input
   * optimization parameter values
   *
   * @param optParams A vector of real values of parameters used in optimizing
   *                  the error function
   * @return Jacobian vector of the error function
   */
  public double[] jacobian(double[] optParams) {
    double[] measuredOutputs = model.getMeasuredData();
    int numData = measuredOutputs.length;
    int numParams = optParams.length;

    double[] jVector = new double[numParams];
    for (int i = 0; i < numParams; ++i) {
      jVector[i] = 0.0;
    }

    for (int i = 0; i < numData; ++i) {
      double[] modelJacobian = model.jacobian(i, optParams);
      double error = measuredOutputs[i] - model.eval(i, optParams);

      for (int k = 0; k < numParams; k++) {
        jVector[k] -= modelJacobian[k] * error;
      }
    }

    return jVector;
  }

  /**
   * Computes the Hessian matrix of the error function with input
   * optimization parameter values
   *
   * @param optParams A vector of real values of parameters used in optimizing
   *                  the error function
   * @return Hessian matrix of the error function
   */
  public double[][] hessian(double[] optParams) {
    double[] measuredOutputs = model.getMeasuredData();
    int numData = measuredOutputs.length;
    int numParams = optParams.length;

    double[][] jVectors = new double[numData][numParams];
    for (int i = 0; i < numData; ++i) {
      jVectors[i] = model.jacobian(i, optParams);
    }
    Matrix jacobianMat = new Matrix(jVectors);
    Matrix hessianMat = jacobianMat.transpose().times(jacobianMat);
    for (int i = 0; i < numData; ++i) {
      double error = measuredOutputs[i] - model.eval(i, optParams);
      Matrix modelHessian = new Matrix(model.hessian(i, optParams));
      hessianMat.minusEquals(modelHessian.times(error));
    }

    return hessianMat.getArrayCopy();
  }
}
