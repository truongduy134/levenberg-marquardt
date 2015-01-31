package com.nus;

import Jama.Matrix;

/**
 * Created by duy on 31/1/15.
 */
public class JamaHelper {
  /**
   * Solves the matrix equation A * x = b
   * @param A Matrix on the left-hand side of the equation
   * @param b Matrix on the right-hand side of the equation
   * @return The solution of the equation A * x = b
   */
  public static Matrix solveMatrixEq(Matrix A, Matrix b) {
    return A.lu().solve(b);
  }
}
