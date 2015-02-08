package com.nus;

import Jama.Matrix;
import Jama.CholeskyDecomposition;

/**
 * Created by duy on 31/1/15.
 */
public class JamaHelper {
  /**
   * Solves the matrix equation A * x = b where A is assumed to be positive
   * definite by using Cholesky decomposition
   * @param A Matrix on the left-hand side of the equation
   * @param b Matrix on the right-hand side of the equation
   * @return The solution of the equation A * x = b, OR null if A is not
   *         positive definite
   */
  public static Matrix solvePSDMatrixEq(Matrix A, Matrix b) {
    CholeskyDecomposition cholesky = A.chol();
    if (!cholesky.isSPD()) {
      return null;
    }
    return cholesky.solve(b);
  }
}
