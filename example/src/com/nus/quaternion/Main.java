package com.nus.quaternion;

/**
 * Created by duy on 22/3/15.
 */

import com.nus.LmSolver;
import com.nus.LmSumError;

public class Main {
  /**
   * Task: Suppose the true quaternion is the unit quaternion
   * q = 0.365148 * i + 0.182574 * j + 0.547723 * k -0.730297
   *
   * Given the set of original points S(1) and the set of points S(2) generated
   * by rotating points in S(1) with the above unit quaternion plus some noise,
   * use Levenberg-Marquardt algorithm to find the quaternion model to fit map
   * points in S(1) to points in S(2)
   */
  public static void main(String[] args) {
    double[][] originalPoints = new double[][] {
      {1.0, 0.0, 0.0},
      {0.0, 1.0, 0.0},
      {0.0, 0.0, 1.0},
      {1.0, 1.0, 1.0},
      {1.0, 1.0, 1.0},
      {-1.0, -2.0, 5.0},
      {0.0, 2.0, -3.0},
      {2.2, 3.3, 4.5},
      {0.45, 1.65, -5.5},
      {1.5, -0.5, -1.5},
      {0.6, 0.7, 0.8},
      {0.75, 0.15, 0.25},
      {-0.5, -0.8, -0.9},
      {-0.8, -0.1, -0.22},
      {3.0, 3.0, -0.5},
    };
    double[][] transformedPoints = new double[][] {
      {0.33334, -0.666667, 0.666667},
      {0.933336, 0.133333, -0.333333},
      {0.13332, 0.733333, 0.666667},
      {1.39997, 0.200050, 1.000080},
      {1.400050, 0.200500, 1.000800},
      {-1.533333, 4.066667, 3.333333},
      {1.466667, -1.933333, -2.666667},
      {4.413333, 2.273333, 3.366667},
      {0.956667, -4.113329, -3.91666},
      {-0.166667, -2.166667, 0.166667},
      {0.960001, 0.280002, 0.700004},
      {0.423333, -0.296667, 0.616667},
      {-1.033336, -0.43334, -0.666668},
      {-0.38933, 0.35866, -0.64666},
      {3.733338, -1.966671, 0.666665},
    };

    QuaternionPointError datumError = new QuaternionPointError(
      originalPoints, transformedPoints);
    LmSumError errorFunc = new LmSumError(datumError);
    LmSolver lmSolver = new LmSolver(errorFunc, 1e-3, 100, 1e-8, 1e-8);
    QuaternionHandler unitHandler = new QuaternionHandler();

    double[] q = new double[] {0.37, 0.17, 0.52, -0.73};
    unitHandler.adjust(q);      // normalize

    lmSolver.solve(q, unitHandler);
    // Expected result: Quaternion(0.365148, 0.182574, 0.547723, -0.730297)
    System.out.println("Result:");
    for (int i = 0; i < q.length; ++i) {
      System.out.printf("%f ", q[i]);
    }
    System.out.println("");
  }
}
