package com.nus;

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
    this.lambda = 10;
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
}
