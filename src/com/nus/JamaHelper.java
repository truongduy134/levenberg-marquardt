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

  /**
   * Computes the dot product between vectors u and v
   * @param u A row or column vector
   * @param v A row or column vector
   * @return Real number which is the dot product between u and v
   */
  public static double dotProduct(Matrix u, Matrix v) {
    if (u.getRowDimension() != 1) {
      u = u.transpose();
    }
    if (v.getColumnDimension() != 1) {
      v = v.transpose();
    }
    return (u.times(v).getArray())[0][0];
  }
}
