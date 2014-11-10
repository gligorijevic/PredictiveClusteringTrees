/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package splittingcriteria;

import util.matrix.Gets;

/**
 *
 * @author djordje
 */
public class CalculateVariance {

    /**
     *
     * Works only for two dimensional target Variables
     *
     * @param targetVariables
     * @return
     */
    public static double calculateVariance(double[][] targetVariables) {
        double[][] mean = FindPrototype.findOptimalPrototype(targetVariables);

        int N = targetVariables.length;

        double numerator = 0;

        for (int i = 0; i < N; i++) {
            numerator += EuclideanDistance.calculateEuclideanDistance(Gets.getRowFromMatix(i, targetVariables), mean);
        }
        
        if (N - 1 == 0) {
            return 0;
        } else {
            return numerator / (N - 1);
        }
    }

}
