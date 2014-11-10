/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package splittingcriteria;

/**
 *
 * @author djordje
 */
public class EuclideanDistance {

    public static double calculateEuclideanDistance(double[][] e1, double[][] e2) {
        return Math.sqrt(Math.pow(e1[0][0] - e2[0][0], 2) + Math.pow(e1[0][1] - e2[0][1], 2));
    }

}
